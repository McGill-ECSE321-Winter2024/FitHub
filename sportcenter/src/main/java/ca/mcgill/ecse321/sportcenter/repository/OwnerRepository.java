package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    Owner findOwnerById(int id);
}
