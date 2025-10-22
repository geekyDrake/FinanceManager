package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.domain.dto.MonthlySubscription;
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
public class MonthlySubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriptionEntityId;
    private String name;
    private Long amount;
    private Date endDate;

    public MonthlySubscriptionEntity(final MonthlySubscription s) {
        this(null,s.getName(),s.getAmount(),s.getEndDate());
    }
}
