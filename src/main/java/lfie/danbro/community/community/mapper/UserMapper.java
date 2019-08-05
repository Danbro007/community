package lfie.danbro.community.community.mapper;


import lfie.danbro.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //添加用户到数据库
    @Insert("INSERT INTO user(name,account_id,gmt_create,gmt_modified,token) values(#{name},#{accountId},#{gmtCreate},#{gmtModified},#{token})")
    void addUser(User user);

    //通过token查找用户
    @Select("SELECT * FROM user WHERE token = #{token}")
    User findtUserByToken(String token);

}
