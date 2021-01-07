package ru.list.surkovr.springwebfluxcatalizator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.list.surkovr.springwebfluxcatalizator.domain.Message;
import ru.list.surkovr.springwebfluxcatalizator.repo.MessageRepo;

@Service
public class MessageService {

    @Autowired
    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Flux<Message> list() {
        return messageRepo.findAll();
    }

    public Mono<Message> addOne(Message message) {
        return messageRepo.save(message);
    }
}
