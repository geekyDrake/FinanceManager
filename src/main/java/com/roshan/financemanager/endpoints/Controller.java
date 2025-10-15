package com.roshan.financemanager.endpoints;

import com.roshan.financemanager.domain.Subscription;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping("/greeting")
    public String testEndpoint(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @PostMapping(path="/addMonthly", consumes="application/json")
    public void addMonthlySubscription(
            @RequestBody Subscription newSubscription
            ){

    }
}
