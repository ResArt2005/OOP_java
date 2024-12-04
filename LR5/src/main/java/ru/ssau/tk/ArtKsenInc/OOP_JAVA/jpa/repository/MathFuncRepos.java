package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MathFuncRepos extends CrudRepository<MathFunc, Integer> {

}