package kz.fastjava.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudController(FraudCheckHistoryService service) {

    @GetMapping(path = "{customerID}")
    public FraudCheckResponse isFraudster(@PathVariable Integer customerID) {
        boolean isFraudulentCustomer = service.isFraudulentCustomer(customerID);
        log.info("New customer check to fraud {} id", customerID);
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
