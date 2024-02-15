package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Image;

public interface ImageRepository extends CrudRepository<Image, Integer> {
    Image findImageById(int id);
}
