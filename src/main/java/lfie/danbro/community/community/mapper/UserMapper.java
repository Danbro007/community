package lfie.danbro.community.community.mapper;


import lfie.danbro.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

//    @Insert("INSERT INTO user(name,account_id,gmt_create,gmt_modified,token)values(#{name},#{accountId},#{gmtCreate},#{gmtModified}),#{token})")
    @Insert("INSERT INTO user(name,account_id,gmt_create,gmt_modified,token) values(#{name},#{accountId},#{gmtCreate},#{gmtModified},#{token})")
    public void addUser(User user);

}
