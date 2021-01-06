package ru.list.surkovr.springwebfluxcatalizator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.list.surkovr.springwebfluxcatalizator.domain.Message;

import java.util.Objects;
import java.util.Optional;

// Для наглядности - описание по-старому (в отличие от нового функционального стиля в GreetingRouter
@RestController
@RequestMapping("/controller")
public class Controller {

    @GetMapping
    public Flux<Message> list(@RequestParam(required = false, defaultValue = "0") Long start,
                              @RequestParam(required = false, defaultValue = "3") Long count) {
        return Flux
                .just("Hello reactive!",
                        "Second",
                        "Third",
                        "Fourth post",
                        "Fifth")
                // Пагинация - с какого начинать и сколько элементов взять
                .skip(start)
                .take(count)
                .map(Message::new);
    }
}
