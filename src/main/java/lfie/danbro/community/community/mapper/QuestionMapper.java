package lfie.danbro.community.community.mapper;


import lfie.danbro.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QuestionMapper {
    //添加问题
    @Insert("INSERT INTO question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void addQuestion(Question question);


    @Select("SELECT * FROM question")
    List<Question> getAllQuestions();

    @Select("SELECT * FROM question WHERE creator = #{id}")
    List<Question> getQuestionsByUserId(Integer userId);


    @Select("SELECT * FROM question WHERE id = #{id}")
    Question getQuestionById(Integer id);


    @Update("UPDATE question SET title = #{title},description = #{description},tag = #{tag}, gmt_modified = #{gmtModified} WHERE id = #{id}")
    void updateQuestion(Question question);
}
