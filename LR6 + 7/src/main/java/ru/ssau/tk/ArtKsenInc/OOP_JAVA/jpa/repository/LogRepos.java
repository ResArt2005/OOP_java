package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository;
import  ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.Log;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogRepos extends CrudRepository<Log, Integer> {
    List<Log> findByOrderByTimestampDesc();
}