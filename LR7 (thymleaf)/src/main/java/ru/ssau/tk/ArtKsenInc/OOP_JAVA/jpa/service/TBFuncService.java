package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.TBFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.TBFuncRepos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<Integer, TBFunc> readAll() {
        List<TBFunc> funcList = TBFuncRepository.findAll();
        Map<Integer, TBFunc> funcMap = new HashMap<>();

        for (TBFunc func : funcList) {
            funcMap.put(func.getId(), func);
        }

        return funcMap;
    }


    // Метод для обновления записи в базе данных
    @Transactional
    public TBFunc update(Integer id, TBFunc mathFunc) {
        // Получаем существующий объект из базы данных
        TBFunc existingFunc = TBFuncRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Function with id " + id + " not found"));

        // Обновляем поля существующего объекта значениями из mathFunc
        existingFunc.setYValues(mathFunc.getYValues());
        existingFunc.setXValues(mathFunc.getXValues());
        // Сохраняем изменения в базе данных
        return TBFuncRepository.save(existingFunc);
    }
    @Transactional
    public TBFunc getById(Integer id) {
        return TBFuncRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Function with id " + id + " not found"));
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
