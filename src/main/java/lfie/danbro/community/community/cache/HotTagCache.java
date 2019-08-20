package lfie.danbro.community.community.cache;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class HotTagCache {
    private Map<String,Double> tagsPriority = new HashMap<>();
}
