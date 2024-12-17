package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.UserRepos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepos UserRepository;

    @Autowired
    public UserService(UserRepos UserRepository) {
        this.UserRepository = UserRepository;
    }

    // Метод для создания новой записи в базе данных
    @Transactional
    public User create(User user) {
        return UserRepository.save(user);
    }

    // Метод для получения всех записей из базы данных
    @Transactional
    public Map<String, User> readAll() {
        List<User> UserList = UserRepository.findAll();
        Map<String, User> UserMap = new HashMap<>();

        for (User func : UserList) {
            UserMap.put(func.getToken(), func);
        }

        return UserMap;
    }


    // Метод для обновления записи в базе данных
    @Transactional
    public User update(Integer id, User user) {
        // Получаем существующий объект из базы данных
        User existingUser = UserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        // Обновляем поля существующего объекта значениями из mathFunc
        existingUser.setLogin(user.getLogin());
        existingUser.setPassword(user.getPassword());
        // Сохраняем изменения в базе данных
        return UserRepository.save(existingUser);
    }
    @Transactional
    public User getById(Integer id) {
        return UserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }
    // Метод для удаления записи из базы данных по идентификатору
    @Transactional
    public void deleteById(Integer id) {
        UserRepository.deleteById(id);
    }


    // Метод для удаления всех записей из базы данных
    @Transactional
    public void deleteAll() {
        UserRepository.deleteAll();
    }
}
