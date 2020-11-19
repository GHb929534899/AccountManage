package org.example.controller;

import org.example.entity.ResponseData;
import org.example.entity.User;
import org.example.service.UserManageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类将向外提供接口方法，将UserManageServiceImplement类中实现的对外提供服务的方法，包装成接口，通过Mapping向外暴露接口，并进行数据的初步过滤
 */
@RequestMapping("/user")
@RestController
public class UserManageController {

    @Resource
    private UserManageService userManageService;

    @PostMapping("/insert")
    public ResponseData<User> insertUser(@RequestBody User user) {
        if(user == null) {
            return new ResponseData<>(400,"参数不可为null",null);
        }
        else {
            if(user.getName()==null) {
                return new ResponseData<>(400,"添加失败，用户姓名为必填项",user);
            }
            else if(user.getName().equals("")) {
                return new ResponseData<>(400,"添加失败，用户姓名不能为空",user);
            }
            else if (user.getGender()==null) {
                return new ResponseData<>(400,"添加失败，用户性别为必填项",user);
            }
            else if(user.getAge() < 0) {
                return new ResponseData<>(400,"添加失败，用户年龄数值非法",user);
            }
            else {
                return new ResponseData<>(200,"添加成功",userManageService.insertUser(user));
            }
        }
    }

    @DeleteMapping("/delete")
    public ResponseData<User> deleteUser(@RequestBody User user) {
        if(user == null) {
            return new ResponseData<>(400,"参数不可为null",null);
        }
        else {
            if(user.getId()==0) {
                return new ResponseData<>(400,"删除失败，请传入合法id值",user);
            }
            else {
                User findUserToDelete = userManageService.findUserById(user.getId());
                if(findUserToDelete == null)
                {
                    return new ResponseData<>(400,"未查询到该用户",user);
                }
                else {
                    if (user.getName() == null) {
                        return new ResponseData<>(400, "删除失败，用户姓名为必填项", user);
                    }
                    else if (!user.getName().equals(findUserToDelete.getName())) {
                        return new ResponseData<>(400, "删除失败，用户姓名信息有误", user);
                    }
                    else if (user.getGender() == null) {
                        return new ResponseData<>(400, "删除失败，用户性别为必填项", user);
                    }
                    else if (user.getGender() != findUserToDelete.getGender()) {
                        return new ResponseData<>(400, "删除失败，用户性别信息有误", user);
                    }
                    else if (user.getAge() != findUserToDelete.getAge()) {
                        return new ResponseData<>(400, "删除失败，用户年龄信息有误", user);
                    }
                    else {
                        userManageService.deleteUser(user.getId());
                        return new ResponseData<>(200, "删除成功", findUserToDelete);
                    }
                }
            }
        }
    }

    @PutMapping("/update")
    public ResponseData<User> updateUser(@RequestBody User user) {
        if(user == null) {
            return new ResponseData<>(400,"参数不可为null",null);
        }
        else {
            if(user.getId() == 0) {
                return new ResponseData<>(400,"修改失败，用户id数值非法",user);
            }
            else if(user.getId() == 1) {
                return new ResponseData<>(403, "无权访问！", null);
            }
            else {
                User findUserToDelete = userManageService.findUserById(user.getId());
                if(findUserToDelete == null)
                {
                    return new ResponseData<>(400,"未查询到该用户",user);
                }
                else {
                    if (user.getName() == null) {
                        return new ResponseData<>(400, "修改失败，用户姓名为必填项", user);
                    }
                    else if(user.getName().equals("")) {
                        return new ResponseData<>(400,"修改失败，用户姓名不能为空",user);
                    }
                    else if (user.getGender() == null) {
                        return new ResponseData<>(400, "修改失败，用户性别为必填项", user);
                    }
                    else if(user.getAge() < 0) {
                        return new ResponseData<>(400,"修改失败，用户年龄数值非法",user);
                    }
                    else {
                        return new ResponseData<>(200,"修改成功",userManageService.updateUser(user));
                    }
                }
            }
        }
    }

    @PostMapping("/getUserInfo")
    public ResponseData<User> getUserInfoById(int id) {
        if(id > 0) {
            if(id == 1) {
                return new ResponseData<>(403,"无权访问！",null);
            }
            else {
                User user = userManageService.findUserById(id);
                if(user == null) {
                    return new ResponseData<>(400,"查询失败，未找到该id对应的用户",new User(id,null,null,0));
                }
                else {
                    return new ResponseData<>(200,"查询成功",user);
                }
            }
        }
        else {
            return new ResponseData<>(400,"查询失败，用户id必须为正数",new User());
        }
    }

    @GetMapping("/getFirst")
    public ResponseData<User> getFirstUser() {
        return new ResponseData<>(200,"查询成功",userManageService.getFirstUser());
    }

    @GetMapping("/getAll")
    public ResponseData<List<User>> getAllUser() {
        return new ResponseData<>(200,"查询成功",userManageService.getAllUser());
    }

    @PostMapping("/getPage")
    public ResponseData<List<User>> getPage(@RequestParam int currentPage,int pageSize) {
        if(currentPage < 1) {
            return new ResponseData<>(400,"查询失败,当前页数最小值为1",new ArrayList<>());
        }
        else if(pageSize < 1) {
            return new ResponseData<>(400,"查询失败,每页信息数最小值为1",new ArrayList<>());
        }
        else {
            List<User> userList = userManageService.getUserPage(currentPage, pageSize);
            if(userList.size() > 0) {
                return new ResponseData<>(200,"查询成功",userList);
            }
            else {
                return new ResponseData<>(400,"未查询到指定范围内用户",userList);
            }
        }
    }
}