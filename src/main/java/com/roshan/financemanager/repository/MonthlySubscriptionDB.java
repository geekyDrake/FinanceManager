package com.roshan.financemanager.repository;

import com.roshan.financemanager.domain.database.MonthlySubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlySubscriptionDB extends JpaRepository<MonthlySubscriptionEntity, Long> {

}
