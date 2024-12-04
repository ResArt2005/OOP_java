package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.TBFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathFuncRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.TBFuncRepos;

import java.util.List;

@Service
public class TBFuncService {

    private final TBFuncRepos TBFuncRepository;

    @Autowired
    public TBFuncService(TBFuncRepos TBFuncRepository) {
        this.TBFuncRepository = TBFuncRepository;
    }

    // Метод для создания новой записи в базе данных
    @Transactional
    public TBFunc create(TBFunc mathFunc) {
        return TBFuncRepository.save(mathFunc);
    }

    // Метод для получения всех записей из базы данных
    public List<TBFunc> readAll() {
        return (List<TBFunc>) TBFuncRepository.findAll();
    }


    // Метод для обновления записи в базе данных
    @Transactional
    public TBFunc update(TBFunc mathFunc) {
        return TBFuncRepository.save(mathFunc);
    }

    // Метод для удаления записи из базы данных по идентификатору
    @Transactional
    public void deleteById(Integer id) {
        TBFuncRepository.deleteById(id);
    }


    // Метод для удаления всех записей из базы данных
    @Transactional
    public void deleteAll() {
        TBFuncRepository.deleteAll();
    }
}
