package com.roshan.financemanager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalLong;

@Getter
public class Subscription {
    private String name;
    @JsonProperty("start_date")
    private Date startDate;
    private Optional<Long> years;
    private Long months;

    /*
    If years is null, then assume we are using only months
    If both years is not null, months should be >0, <12
     */
    public Subscription(final String name, final Date startDate, final Long years, final Long months){
        this.name = name;
        this.startDate = startDate;
        this.years = Optional.ofNullable(years);
        this.months = months;
        if(months < 0 || months > 12) { throw new RuntimeException("Invalid number of months"); }
        if(this.years.isPresent() && (years < 0)) { throw new RuntimeException("Invalid number of years"); }
    }
}
