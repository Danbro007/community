package lfie.danbro.community.community.mapper;


import lfie.danbro.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //添加用户到数据库
    @Insert("INSERT INTO user(name,account_id,gmt_create,gmt_modified,token,avatar_url) values(#{name},#{accountId},#{gmtCreate},#{gmtModified},#{token},#{avatarUrl})")
    void addUser(User user);

    //通过token查找用户
    @Select("SELECT * FROM user WHERE token = #{token}")
    User findtUserByToken(String token);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(Integer id);

    @Select("SELECT * FROM user WHERE account_id = #{accountId}")
    User findUserByAccountId(String accountId);

    @Update("UPDATE user SET token = #{token},avatar_url = #{avatarUrl},name = #{name},gmt_modified = #{gmtModified} WHERE account_id = #{accountId}")
    void updateUserToken(User user);
}
