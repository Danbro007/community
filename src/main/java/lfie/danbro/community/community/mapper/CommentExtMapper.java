package lfie.danbro.community.community.mapper;

import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentExtMapper {
   List<CommentDto> getCommentsByTargetId(@Param("id") Long id,@Param("type") Integer type);
   void increaseCommentCount(Comment comment);
}