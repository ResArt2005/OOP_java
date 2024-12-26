package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.Log;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"fabricType", "userDTO"})
public class WorkWithDBController {
    //Инициализация страницы для работы с бд
    @GetMapping("/main/workWithDb")
    public String get(Model model, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("userDTO");
        if(user == null || !user.getToken().equals("admin")){
            return "redirect:/";
        }
        model.addAttribute("users", "");
        return "workWithDb";
    }
    //Методы для работы с пользователями
    @PostMapping("/main/workWithDbUsersINNT")
    @ResponseBody
    public String workWithDbUsers() {
        Map<String, User> users = dbTools.getAllUsers();
        if(users.size() == 1){
            return "<div class='userBlock'>Пользователей кроме админа не обнаружено</div>";
        }
        return userTable(users);
    }
    @PostMapping("/main/UserCreate")
    @ResponseBody
    public String UserCreate(@RequestParam("login") String login,
                           @RequestParam("password") String password){
        User newUser = new User(login, password);
        dbTools.createUser(newUser);
        Map<String, User> users = dbTools.getAllUsers();
        dbTools.createLog(login + " добавился в систему");
        return userTable(users);
    }
    @PostMapping("/main/UserChange")
    @ResponseBody
    public String UserChange(@RequestParam("token") String token,
                           @RequestParam("login") String login,
                           @RequestParam("password") String password){
        if (token.equals("admin")){
            Map<String, User> users = dbTools.getAllUsers();
            return userTable(users);
        }
        dbTools.updateUserByToken(token, login, password);
        Map<String, User> users = dbTools.getAllUsers();
        return userTable(users);
    }
    @PostMapping("/main/UserDelete")
    @ResponseBody
    public String UserDelete(@RequestParam("token") String token, Model model){
        if (token.equals("admin")){
            Map<String, User> users = dbTools.getAllUsers();
            return userTable(users);
        }
        dbTools.deleteUserByToken(token);
        Map<String, User> users = dbTools.getAllUsers();
        return userTable(users);
    }

    private String userTable(Map<String, User> users){
        StringBuilder sb = new StringBuilder();
        int id = 0;
        sb.append("<div class='userBlock'>");
        for (String token: users.keySet()){
            sb.append("<div><span class='userToken' id='userToken").append(id).append("'>")
                    .append(token).append("</span> <span class='userLogin'>")
                    .append(users.get(token).getLogin())
                    .append("</span><button class='userChangeBtn' data-token-id='userToken")
                    .append(id).append("'>Изменить</button> <button class='userDeleteBtn' data-token-id='userToken")
                    .append(id).append("'>Удалить</button></div>");
            ++id;
        }
        sb.append("</div>");
        return sb.toString();
    }
    //Методы для работы с математическими функциями
    @PostMapping("/main/workWithDbMathFunc")
    @ResponseBody
    public void workWithDbMathFunc(HttpSession session) {

    }
    //Методы для работы с табулированными функциями
    @PostMapping("/main/workWithDbTBFunc")
    @ResponseBody
    public void workWithDbTBFunc(HttpSession session) {

    }

    //Методы для работы с логами
    //Вывод логов
    @PostMapping("/main/workWithDbLogs")
    @ResponseBody
    public String workWithDbLogs() {
        List<Log> logs = dbTools.getAllLogs();
        if(logs.isEmpty()){
            return "<div class='userBlock'>Логи отсутствуют</div>";
        }
        return logTable(logs);
    }
    //Удаление всех логов
    @PostMapping("/main/removeLogs")
    @ResponseBody
    public String removeLogs() {
        dbTools.deleteAllLogs();
        return "<div class='userBlock'>Логи отсутствуют</div>";
    }
    private String logTable(List<Log> logs){
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='userBlock'>");
        for (Log log: logs){
            sb.append("<div><span class='logClass'>").append(log.getTimestamp()).append(" ").append(log.getMessage()).append("</span></div>");
        }
        sb.append("</div>");
        return sb.toString();
    }
    //Стирание всех данных из базы
    @PostMapping("/main/workWithDbEraseAll")
    @ResponseBody
    public void workWithDbEraseAll() {
        dbTools.deleteAllUsers();
        dbTools.deleteAllMathFunctions();
        dbTools.deleteAllTBFunctions();
        dbTools.deleteAllLogs();
    }
}
