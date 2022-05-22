package kz.fastjava.customer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
    public boolean emailHasTaken(CustomerRegistrationRequest request) {
        return !customerRepository.findByEmail(request.email());
    }

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);
        // TODO: check if email valid
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerID}",
                FraudCheckResponse.class,
                customer.getId()
        );
        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }
        // TODO: send notification
    }
}
