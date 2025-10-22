package com.roshan.financemanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class MonthlySubscription {
    private String name;
    private Long amount;
    @Nullable
    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    public void validate() {
        if(endDate != null && endDate.before(new Date())){ throw new RuntimeException("Monthly subscription no longer active"); }
    }
}
