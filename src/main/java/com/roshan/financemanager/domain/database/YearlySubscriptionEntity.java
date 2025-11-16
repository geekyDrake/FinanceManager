package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.domain.dto.MonthlySubscription;
import com.roshan.financemanager.domain.dto.YearlySubscription;
import com.roshan.financemanager.util.DateHelpers;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@Entity
public class YearlySubscriptionEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long subscriptionEntityId;
    private final String name;
    // Assume amount is for an arbitary year if no endDate is given (i.e. divide by 12)
    private final Long amount;

    public YearlySubscriptionEntity(final String name, final Long amount, final Date endDate, final Date startDate){
        super(endDate, startDate);
        this.subscriptionEntityId = null;
        this.name = name;
        this.amount = amount;
    }

    public YearlySubscriptionEntity(final YearlySubscription s) {
        this(s.getName(),s.getAmount(),s.getEndDate(), s.getStartDate());
    }
}
