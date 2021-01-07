package ru.list.surkovr.springwebfluxcatalizator.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.list.surkovr.springwebfluxcatalizator.domain.User;

public interface UserRepo extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String username);
}
