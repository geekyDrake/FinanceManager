package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.domain.dto.MonthlySubscription;
import com.roshan.financemanager.domain.dto.YearlySubscription;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class YearlySubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriptionEntityId;
    private String name;
    // Assume amount is for an arbitary year if no endDate is given (i.e. divide by 12)
    private Long amount;
    // If no endDate, assume subscription is perpetual
    private Date endDate;

    public YearlySubscriptionEntity(final YearlySubscription s) {
        this(null,s.getName(),s.getAmount(),s.getEndDate());
    }
}
