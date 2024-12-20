package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyController {
    @GetMapping("/path")
    public String show() {
        return "Spring_AJAX_Example";
    }
    @PostMapping("/path")
    public ResponseEntity<String> handleRequest(@RequestParam String data) {
        // Логика обработки данных
        String responseMessage = "Данные получены: " + data;

        // Возвращаем ответ
        return ResponseEntity.ok(responseMessage);
    }
}