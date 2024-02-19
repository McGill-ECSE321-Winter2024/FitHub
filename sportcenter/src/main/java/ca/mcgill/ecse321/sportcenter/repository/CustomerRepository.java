package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findCustomerById(int id);
}