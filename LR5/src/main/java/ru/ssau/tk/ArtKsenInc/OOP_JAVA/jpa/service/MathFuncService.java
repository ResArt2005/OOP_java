package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.TBFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathFuncRepos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<Integer, MathFunc> readAll() {
        List<MathFunc> funcList = (List<MathFunc>)mathFuncRepository.findAll();
        Map<Integer, MathFunc> funcMap = new HashMap<>();

        for (MathFunc func : funcList) {
            funcMap.put(func.getId(), func);
        }

        return funcMap;
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


    // Метод для удаления всех записей из базы данных
    @Transactional
    public void deleteAll() {
        mathFuncRepository.deleteAll();
    }
}
