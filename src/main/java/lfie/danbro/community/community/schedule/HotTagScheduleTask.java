package lfie.danbro.community.community.schedule;

import lfie.danbro.community.community.cache.HotTagCache;
import lfie.danbro.community.community.mapper.QuestionMapper;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HotTagScheduleTask {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 5000)
//    @Scheduled(cron = "0 0 6,19 * * *")
    public void reportCurrentTime() {
        QuestionExample questionExample = new QuestionExample();
        List<Question> questions = questionMapper.selectByExample(questionExample);
        if (questions.size() > 0) {
            Map<String, Double> newTagsPriority = new HashMap<>();
            for (Question question : questions) {
                String[] tags = question.getTag().split(",");
                for (String tag : tags) {
                    if (newTagsPriority.containsKey(tag)) {
                        Double priority = newTagsPriority.get(tag);
                        newTagsPriority.put(tag, priority + 0.2 * question.getViewCount() + 0.6 * question.getCommentCount());
                    } else {
                        newTagsPriority.put(tag, 0.2 * question.getViewCount() + 0.6 * question.getCommentCount());
                    }
                }
            }
            hotTagCache.setTagsPriority(newTagsPriority);
            hotTagCache.getTagsPriority().forEach(
                    (k, v) -> {
//                        System.out.print(k);
//                        System.out.print(":");
//                        System.out.print(v);
//                        System.out.println();
                    }
            );
        }
    }
}
