package org.zerock.life_fit.mainscreen.hospital;

import lombok.Data;

@Data
public class Body {
    private String totalCount;
    private String pageNo;
    private String numOfRows;
    private ItemWrapper items;
}

