package lfie.danbro.community.community.cache;


import lfie.danbro.community.community.dto.HotTagDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private Map<String,Long> tagsPriority = new HashMap<>();
    public void updateTags(Map<String,Long> tags){
        List<String> priorityList = new ArrayList<>();
        PriorityQueue<HotTagDto> priorityQueue = new PriorityQueue<>();
        int max = 5;
        tags.forEach((name,priority)->{
            HotTagDto hotTagDto = new HotTagDto();
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
        HotTagDto poll = priorityQueue.poll();
        while (poll!= null){
            priorityList.add(poll.getName());
            poll = priorityQueue.poll();
        }
        System.out.println(priorityList);
    }
}
