package kz.fastjava.fraud;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record FraudCheckHistoryService(FraudCheckHistoryRepository repository) {

    public boolean isFraudulentCustomer(Integer customerID) {
        repository.save(FraudCheckHistory.builder()
                .customerID(customerID)
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .build());
        return false;
    }
}
