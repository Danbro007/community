package lfie.danbro.community.community.utils;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {
    public static List<Integer> getPageList(Integer totalPage, Integer currentPage, Integer showPage) {
        List<Integer> pageList = new ArrayList<>();
        if (currentPage <= showPage && currentPage > 0) {
            for (int i = 1; i <= totalPage; i++) {
                pageList.add(i);
            }
        } else if (currentPage > showPage && currentPage <= totalPage) {
            if (currentPage == totalPage) {
                for (int i = currentPage - showPage + 1; i <= currentPage; i++) {
                    pageList.add(i);
                }
            } else if (totalPage - currentPage >= showPage) {
                for (int i = currentPage - 2; i <= currentPage + 2; i++) {
                    pageList.add(i);
                }
            } else if (totalPage - currentPage < showPage) {
                int leftPage = showPage - (totalPage - currentPage);
                for (int i = currentPage - leftPage + 1; i <= totalPage; i++) {
                    pageList.add(i);
                }
            }

        }
        return pageList;
    }
}
