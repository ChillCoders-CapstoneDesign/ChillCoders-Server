package com.project.submate.subscribe.dto;

import com.project.submate.subscribe.entity.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SubscribeRequestDto {
    private String name;
    private String image;
    private Integer price;
    private String priceUnit;
    private Integer period;
    private String periodUnit;
    private LocalDate startDate;

    public void updateSubscribeInfo(Subscribe subscribe){
        subscribe.setSubscribeName(this.name);
        subscribe.setImage(this.image);
        subscribe.setPrice(this.price);
        subscribe.setPriceUnit(this.priceUnit);
        subscribe.setPeriod(this.period);
        subscribe.setPeriodUnit(this.periodUnit);
        subscribe.setStartDate(this.startDate);
    }
}
