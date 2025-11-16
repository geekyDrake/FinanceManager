package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.domain.dto.MonthlySubscription;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Entity
public class MonthlySubscriptionEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long subscriptionEntityId;
    private final String name;
    private final Long amount;

    public MonthlySubscriptionEntity(final String name, final Long amount, final Date endDate, final Date startDate){
        super(endDate, startDate);
        this.subscriptionEntityId = null;
        this.name = name;
        this.amount = amount;
    }

    public MonthlySubscriptionEntity(final MonthlySubscription s) {
        this(s.getName(),s.getAmount(),s.getEndDate(), s.getStartDate());
    }
}
