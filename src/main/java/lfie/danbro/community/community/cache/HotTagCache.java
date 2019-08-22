package lfie.danbro.community.community.cache;


import lfie.danbro.community.community.dto.HotTagDto;
import lfie.danbro.community.community.mapper.QuestionExtMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {

    @Autowired
    QuestionExtMapper questionExtMapper;

    private PriorityQueue<HotTagDto> priorityQueue;

    public void updateTags(Map<String,Long> tags){
        priorityQueue = new PriorityQueue<>();
        int max = 5;
        tags.forEach((name,priority)->{
            HotTagDto hotTagDto = new HotTagDto();
            Integer questionCount = questionExtMapper.getRelatedQuestionNumByTag(name);
            hotTagDto.setQuestionCount(questionCount);
            hotTagDto.setName(name);
            hotTagDto.setPriority(priority);
            if (priorityQueue.size() < max){
                priorityQueue.add(hotTagDto);
            }else {
                HotTagDto min = priorityQueue.peek();
                if (min != null){
                    if (hotTagDto.compareTo(min) > 0){
                        priorityQueue.poll();
                        priorityQueue.add(hotTagDto);
                    }
                }
            }
        });
    }
}
