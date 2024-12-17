package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathFuncRepos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MathFuncService {

    private final MathFuncRepos MathFuncRepository;

    @Autowired
    public MathFuncService(MathFuncRepos MathFuncRepository) {
        this.MathFuncRepository = MathFuncRepository;
    }

    // Метод для создания новой записи в базе данных
    @Transactional
    public MathFunc create(MathFunc mathFunc) {
        return MathFuncRepository.save(mathFunc);
    }

    // Метод для получения всех записей из базы данных
    public Map<Integer, MathFunc> readAll() {
        List<MathFunc> funcList = MathFuncRepository.findAll();
        Map<Integer, MathFunc> funcMap = new HashMap<>();

        for (MathFunc func : funcList) {
            funcMap.put(func.getId(), func);
        }

        return funcMap;
    }


    // Метод для обновления записи в базе данных
    @Transactional
    public MathFunc update(Integer id, MathFunc mathFunc) {
        // Получаем существующий объект из базы данных
        MathFunc existingFunc = MathFuncRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Function with id " + id + " not found"));

        // Обновляем поля существующего объекта значениями из mathFunc
        existingFunc.setName(mathFunc.getName());
        existingFunc.setFunction(mathFunc.getFunction());
        // Сохраняем изменения в базе данных
        return MathFuncRepository.save(existingFunc);
    }

    @Transactional
    public MathFunc getById(Integer id) {
        return MathFuncRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Function with id " + id + " not found"));
    }

    // Метод для удаления записи из базы данных по идентификатору
    @Transactional
    public void deleteById(Integer id) {
        MathFuncRepository.deleteById(id);
    }


    // Метод для удаления всех записей из базы данных
    @Transactional
    public void deleteAll() {
        MathFuncRepository.deleteAll();
    }
}
