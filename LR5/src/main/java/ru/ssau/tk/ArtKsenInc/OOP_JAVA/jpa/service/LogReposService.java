package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.LogRepos;

@Component
@AllArgsConstructor
public class LogReposService {
    @Delegate(types = LogRepos.class)

    private LogRepos logRepos;

}