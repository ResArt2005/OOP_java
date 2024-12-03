package ru.ssau.tk.ArtKsenInc.OOP_JAVA.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathFuncRepos;

import java.util.List;

@Service
public class MathFuncService {

    private final MathFuncRepos mathFuncRepository;

    @Autowired
    public MathFuncService(MathFuncRepos mathFuncRepository) {
        this.mathFuncRepository = mathFuncRepository;
    }

    // Метод для создания новой записи в базе данных
    @Transactional
    public MathFunc create(MathFunc mathFunc) {
        return mathFuncRepository.save(mathFunc);
    }

    // Метод для получения всех записей из базы данных
    public List<MathFunc> readAll() {
        return (List<MathFunc>) mathFuncRepository.findAll();
    }

    // Метод для получения записей по хешу
    public List<MathFunc> findByHash(long hash) {
        return mathFuncRepository.findByHash(hash);
    }

    // Метод для получения записи по значению x и хешу
    public MathFunc findByXAndHash(double x, long hash) {
        return mathFuncRepository.findByXAndHash(x, hash);
    }

    // Метод для обновления записи в базе данных
    @Transactional
    public MathFunc update(MathFunc mathFunc) {
        return mathFuncRepository.save(mathFunc);
    }

    // Метод для удаления записи из базы данных по идентификатору
    @Transactional
    public void deleteById(Integer id) {
        mathFuncRepository.deleteById(id);
    }

    // Метод для удаления записей по хешу
    @Transactional
    public void deleteByHash(long hash) {
        mathFuncRepository.deleteByHash(hash);
    }

    // Метод для удаления записи по значению x и хешу
    @Transactional
    public void deleteByXAndHash(double x, long hash) {
        mathFuncRepository.deleteByXAndHash(x, hash);
    }

    // Метод для удаления всех записей из базы данных
    @Transactional
    public void deleteAll() {
        mathFuncRepository.deleteAll();
    }
}
