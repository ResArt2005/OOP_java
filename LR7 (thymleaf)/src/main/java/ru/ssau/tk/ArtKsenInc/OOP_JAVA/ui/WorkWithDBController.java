package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

@Controller
@SessionAttributes("fabricType")
public class WorkWithDBController {
    @GetMapping("/workWithDb")
    public String main() {
        return "workWithDb";
    }

    @PostMapping("/workWithDbLogs")
    public void workWithDbLogs() {

    }

    @PostMapping("/workWithDbMathFunc")
    public void workWithDbMathFunc() {

    }

    @PostMapping("/workWithDbTBFunc")
    public void workWithDbTBFunc() {

    }

    @PostMapping("/workWithDbUsers")
    public void workWithDbUsers() {

    }
}
