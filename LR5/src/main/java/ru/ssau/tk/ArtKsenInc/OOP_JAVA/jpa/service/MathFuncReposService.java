package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathFuncRepos;

import java.util.OptionalDouble;

@Service
public class MathFuncReposService {
    private final MathFuncRepos mathFuncRepos;
    @Autowired
    public MathFuncReposService(MathFuncRepos mathFuncRepos) {
        this.mathFuncRepos = mathFuncRepos;
    }

    public double applyCached(MathFunction func, double x) {
        OptionalDouble cache = getCached(func, x);
        if (cache.isPresent()) {
            return cache.getAsDouble();
        } else {
            double result = func.apply(x);
            MathFunc mathRes = new MathFunc(x, result, (long) func.hash());
            mathFuncRepos.save(mathRes);
            return result;
        }
    }

    public OptionalDouble getCached(MathFunction func, double x) {
        MathFunc var = mathFuncRepos.findByXAndHash(x, func.hash());
        if (var != null) {
            return OptionalDouble.of(var.getY());
        }
        return OptionalDouble.empty();
    }

    @Transactional
    public void deleteAll() {
        mathFuncRepos.deleteAll();
    }
}