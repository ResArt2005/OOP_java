package ru.ssau.tk.ArtKsenInc.OOP_JAVA.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.Log;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.LogRepos;

import java.util.List;

@Service
public class LogService {

    private final LogRepos logRepository;

    @Autowired
    public LogService(LogRepos logRepository) {
        this.logRepository = logRepository;
    }

    // Метод для создания новой записи в базе данных
    @Transactional
    public Log create(Log log) {
        return logRepository.save(log);
    }

    // Метод для получения всех записей из базы данных
    public List<Log> readAll() {
        return (List<Log>) logRepository.findAll();
    }

    // Метод для получения записей, отсортированных по временной метке в порядке убывания
    public List<Log> findByOrderByTimestampDesc() {
        return logRepository.findByOrderByTimestampDesc();
    }

    // Метод для обновления записи в базе данных
    @Transactional
    public Log update(Log log) {
        return logRepository.save(log);
    }

    // Метод для удаления записи из базы данных по идентификатору
    @Transactional
    public void deleteById(Integer id) {
        logRepository.deleteById(id);
    }

    // Метод для удаления всех записей из базы данных
    @Transactional
    public void deleteAll() {
        logRepository.deleteAll();
    }
}
