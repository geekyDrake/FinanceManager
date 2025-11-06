package com.roshan.financemanager.endpoints;

import com.roshan.financemanager.domain.dto.MonthlySubscription;
import com.roshan.financemanager.domain.dto.OneOffExpense;
import com.roshan.financemanager.service.SubscriptionDatabaseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private SubscriptionDatabaseManagerService dbManagerService;

    @GetMapping("/greeting")
    public String testEndpoint(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @PostMapping(path = "/addMonthlySubscription", consumes = "application/json")
    public void addMonthlySubscription(
            @RequestBody MonthlySubscription newMonthlySubscription
    ) {
        newMonthlySubscription.validate();
        dbManagerService.saveSubscription(newMonthlySubscription);
    }

    @PostMapping(path = "/addOneOffExpense", consumes = "application/json")
    public void addOneOffExpense(
            @RequestBody OneOffExpense newOneOffExpense
    ) {
        newOneOffExpense.validate();
        dbManagerService.saveExpense(newOneOffExpense);
    }
}
