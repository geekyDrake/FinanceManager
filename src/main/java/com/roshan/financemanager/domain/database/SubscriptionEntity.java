package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.domain.Subscription;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriptionEntityId;
    private String name;
    private Date startDate;
    private Long years;
    private Long months;

    public SubscriptionEntity(Subscription s) {
        this(null,s.getName(),s.getStartDate(),s.getYears().orElse(0L),s.getMonths());
    }
}
