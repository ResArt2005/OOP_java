package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.LogDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.Log;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.LogService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.MathFuncService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.TBFuncService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class dbTools {

    // Автоматически внедряем сервисы с помощью Spring
    private static UserService userService;
    private static LogService logService;
    private static MathFuncService mathFuncService;
    private static TBFuncService tbFuncService;

    // Внедрение через Autowired
    @Autowired
    public dbTools(UserService userService, LogService logService, MathFuncService mathFuncService, TBFuncService tbFuncService) {
        dbTools.userService = userService;
        dbTools.logService = logService;
        dbTools.mathFuncService = mathFuncService;
        dbTools.tbFuncService = tbFuncService;
    }

    //Табулированные функции
    public static void deleteAllTBFunctions() {
        tbFuncService.deleteAll();
    }

    //Математические функции
    public static void deleteAllMathFunctions() {
        mathFuncService.deleteAll();
    }

    // Логи
    public static List<Log> getAllLogs() {
        return logService.findByOrderByTimestampDesc();
    }

    public static void createLog(String message) {
        Log log = new Log();
        log.setMessage(message);
        log.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        logService.create(log);
    }

    public static List<LogDTO> showLogs() {
        List<LogDTO> logs = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Log log : getAllLogs()) {
            logs.add(new LogDTO(log.getId(), log.getMessage(), log.getTimestamp().toLocalDateTime().format(formatter)));
        }
        return logs;
    }

    public static void deleteAllLogs() {
        logService.deleteAll();
    }

    // Пользователи
    public static Map<String, User> getAllUsers() {
        return userService.readAll();
    }

    public static UserDTO getUserDTOByToken(String token) {
        return new UserDTO(userService.readAll().get(token));
    }

    public static User getUserByToken(String token) {
        return userService.readAll().get(token);
    }

    public static void createUser(User user) {
        userService.create(user);
    }

    public static void deleteAllUsers() {
        userService.deleteAll();
    }

    public static void addAdmin() {
        if (getUserByToken("admin") != null) {
            return;
        }
        User admin = new User();
        admin.setToken("admin");
        admin.setLogin("admin");
        admin.setPassword("admin");
        createUser(admin);
    }
}