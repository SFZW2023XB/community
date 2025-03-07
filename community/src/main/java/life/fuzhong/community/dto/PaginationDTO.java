package life.fuzhong.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPre;
    private boolean showFirst;
    private boolean showNext;
    private boolean showEnd;
    private List<Integer> pages = new ArrayList<>();
    private Integer page;
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;

        pages.add(page);
        for (int i = 1; i <= 3 ; i++) {
            if(page - i > 0) pages.add(0, page - i);
            if(page + i <= totalPage) pages.add(page + i);
        }

        showPre = false;
        if(page != 1) showPre =true;

        showNext = true;
        if (page == totalPage) showNext = false;

        if(pages.contains(1)){
            showFirst = false;
        }else{
            showFirst = true;
        }

        if (pages.contains(totalPage)){
            showEnd = false;
        }else{
            showEnd = true;
        }


    }
}
