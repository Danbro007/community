package lfie.danbro.community.community.repository;


import lfie.danbro.community.community.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionRepository extends ElasticsearchRepository<Question, Long> {
    Page<Question> findByTitleLike(String title, Pageable pageable);
    Page<Question> findByTagLike(String tag, Pageable pageable);
}
