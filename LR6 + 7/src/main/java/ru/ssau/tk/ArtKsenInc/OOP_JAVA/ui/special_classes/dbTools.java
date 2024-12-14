package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.UserService;

import java.util.Map;

@Component
public class dbTools {

    // Автоматически внедряем сервисы с помощью Spring
    private static UserService userService;
    //private static OtherService1 otherService1;  // Пример другого сервиса
    //private static OtherService2 otherService2;  // Пример другого сервиса

    // Внедрение через Autowired
    @Autowired
    public dbTools(UserService userService/*, OtherService1 otherService1, OtherService2 otherService2*/) {
        dbTools.userService = userService;
        //dbTools.otherService1 = otherService1;
        //dbTools.otherService2 = otherService2;
    }

    // Пример статического метода для работы с базой данных
    public static Map<String, User> getAllUsers() {
        return userService.readAll();  // Используем метод сервиса
    }
    public static void createUser(User user){
        userService.create(user);
    }
    public static void deleteAllUsers() {
        userService.deleteAll();  // Используем метод сервиса для поиска пользователя
    }
}