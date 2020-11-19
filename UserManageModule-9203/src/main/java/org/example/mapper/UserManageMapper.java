package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.entity.User;

import java.util.List;

/**
 * 该接口将声明与数据库交互时使用的方法
 */
public interface UserManageMapper {

    @Insert("insert into user (name,gender,age) values (#{name},#{gender},#{age})")
    void userInsert(User user);

    @Delete("delete from user where id = #{id}")
    void userDelete(int id);

    @Update("update user set name = #{name}, gender = #{gender}, age = #{age} where id = #{id}")
    void userUpdate(User user);

    @Select("select * from user where id = #{id}")
    User userSelectById(int id);

    @Select("select * from user where name = #{name}")
    List<User> userSelectByName(String name);

    @Select("select * from user where id >= (select id from user limit #{startLine},1) limit #{pageSize}")
    List<User> userSelectByPage(int startLine, int pageSize);

    @Select("select * from user")
    List<User> userGetAll();
}
