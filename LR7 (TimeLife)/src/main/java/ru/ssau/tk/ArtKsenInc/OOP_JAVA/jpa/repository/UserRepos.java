package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;

public interface UserRepos extends JpaRepository<User, Integer> {

}
