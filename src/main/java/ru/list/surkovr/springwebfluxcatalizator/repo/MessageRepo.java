package ru.list.surkovr.springwebfluxcatalizator.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.list.surkovr.springwebfluxcatalizator.domain.Message;

public interface MessageRepo extends ReactiveCrudRepository<Message, Long> {
}
