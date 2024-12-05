package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathPointRepos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MathPointService {

    private final MathPointRepos mathFuncRepository;

    @Autowired
    public MathPointService(MathPointRepos mathFuncRepository) {
        this.mathFuncRepository = mathFuncRepository;
    }

    // Метод для создания новой записи в базе данных
    @Transactional
    public ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint create(ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint) {
        return mathFuncRepository.save(mathPoint);
    }

    // Метод для получения всех записей из базы данных
    public Map<Integer, ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint> readAll() {
        List<ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint> funcList = (List<ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint>)mathFuncRepository.findAll();
        Map<Integer, ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint> funcMap = new HashMap<>();

        for (ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint func : funcList) {
            funcMap.put(func.getId(), func);
        }

        return funcMap;
    }


    // Метод для обновления записи в базе данных
    @Transactional
    public ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint update(ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint) {
        return mathFuncRepository.save(mathPoint);
    }

    // Метод для удаления записи из базы данных по идентификатору
    @Transactional
    public void deleteById(Integer id) {
        mathFuncRepository.deleteById(id);
    }


    // Метод для удаления всех записей из базы данных
    @Transactional
    public void deleteAll() {
        mathFuncRepository.deleteAll();
    }
}
