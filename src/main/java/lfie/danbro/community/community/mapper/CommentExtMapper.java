package lfie.danbro.community.community.mapper;

import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.model.Comment;
import lfie.danbro.community.community.model.CommentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentExtMapper {
   List<CommentDto> getCommentsByQuestionId(Long id);
}