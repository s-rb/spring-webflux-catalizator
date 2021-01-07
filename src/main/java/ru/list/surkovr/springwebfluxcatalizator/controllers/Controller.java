package ru.list.surkovr.springwebfluxcatalizator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.list.surkovr.springwebfluxcatalizator.domain.Message;
import ru.list.surkovr.springwebfluxcatalizator.service.MessageService;

// Для наглядности - описание по-старому (в отличие от нового функционального стиля в GreetingRouter
@RestController
@RequestMapping("/controller")
public class Controller {

    @Autowired
    private final MessageService messageService;

    public Controller(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public Flux<Message> list(@RequestParam(required = false, defaultValue = "0") Long start,
                              @RequestParam(required = false, defaultValue = "3") Long count) {
        return messageService.list();
    }

    @PostMapping
    public Mono<Message> addOne(@RequestBody Message message) {
        return messageService.addOne(message);
    }
}
