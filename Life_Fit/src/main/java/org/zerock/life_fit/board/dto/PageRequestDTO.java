package org.zerock.life_fit.board.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {

    private String keyword;
    private String searchtype;
    @Builder.Default
    @Min(value=1)
    //정수만 지정하도록 설정
    @Positive
    private int page = 1;


    //최솟값은 10 최대값은 100으로 설정
    @Builder.Default
    @Min(value=10)
    @Max(value=100)
    private int size = 10;
    private String link;

    public int getSkip(){
        //1페이지 : 0
        //2페이지 : 10
        //3페이지 : 20
        return (page - 1) * 10;
    }
    public String getLink(){
        if(link ==null){
            StringBuilder builder = new StringBuilder();
            builder.append("page="+this.page);
            builder.append("&size="+this.size);
            link = builder.toString();
        }
        return link;
    }
}
