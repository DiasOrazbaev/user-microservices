package kz.fastjava.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public boolean emailHasTaken(CustomerRegistrationRequest request) {
        return !customerRepository.findByEmail(request.email());
    }

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // TODO: check if email valid
        customerRepository.save(customer);
    }
}
