package com.roshan.financemanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class YearlySubscription {
    private String name;
    private Long amount;
    @Nullable
    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    // If no start date given, subscription started long ago
    @Nullable
    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    public void validate() {
        if(endDate != null && endDate.before(new Date())){ throw new RuntimeException("Yearly subscription no longer active"); }
    }
}
