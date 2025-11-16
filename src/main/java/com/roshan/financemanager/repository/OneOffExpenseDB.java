package com.roshan.financemanager.repository;

import com.roshan.financemanager.domain.database.OneOffExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneOffExpenseDB extends JpaRepository<OneOffExpenseEntity, Long> {}
