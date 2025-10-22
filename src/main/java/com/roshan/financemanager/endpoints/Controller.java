package com.roshan.financemanager.endpoints;

import com.roshan.financemanager.domain.Subscription;
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

    @PostMapping(path = "/addSubscription", consumes = "application/json")
    public void addMonthlySubscription(
            @RequestBody Subscription newSubscription
    ) {
        dbManagerService.saveSubscription(newSubscription);
    }
}
