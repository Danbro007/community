package lfie.danbro.community.community.dto;


import lombok.Data;

@Data
public class HotTagDto implements Comparable {
    private String name;
    private Long priority;

    @Override
    public int compareTo(Object o) {
        return new Long(this.priority).intValue() - new Long(((HotTagDto) o).getPriority()).intValue();
    }
}
