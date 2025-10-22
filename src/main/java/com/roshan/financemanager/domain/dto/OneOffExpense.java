package com.roshan.financemanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OneOffExpense {
    private String name;
    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @Nullable
    private Long years;
    @Nullable
    private Long months;
    @Nullable
    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    private Long amount;

    /*
     Prioritise endDate over years/months if both are given
     */
    public void validate(){
        if(years == null && months == null && endDate == null){
            throw new RuntimeException("No amortisation period given");
        }
        if((endDate != null) && (years != null || months != null)){
            setYears(null);
            setMonths(null);
        }
    }

    public Date getRealEndDate(){
        if(endDate!=null) { return this.endDate; }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        if(years != null ){
            c.add(Calendar.YEAR, Math.toIntExact(years));
        }
        if(months != null){
            c.add(Calendar.MONTH, Math.toIntExact(months));
        }
        return c.getTime();
    }
}
