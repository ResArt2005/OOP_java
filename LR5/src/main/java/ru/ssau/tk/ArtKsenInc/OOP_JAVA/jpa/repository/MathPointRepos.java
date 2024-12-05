package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathPointRepos extends CrudRepository<ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint, Integer> {

}