package ru.list.surkovr.springwebfluxcatalizator.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.list.surkovr.springwebfluxcatalizator.domain.Message;

import java.util.Map;

@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        Long start = request.queryParam("start")
                .map(Long::valueOf).orElse(0L);
        Long count = request.queryParam("count")
                .map(Long::valueOf).orElse(3L);

        final Flux<Message> data = Flux
                .just("Hello reactive!",
                        "Second",
                        "Third",
                        "Fourth post",
                        "Fifth")
                // Пагинация - с какого начинать и сколько элементов взять
                .skip(start)
                .take(count)
                .map(Message::new);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data, Message.class);
    }

    public Mono<ServerResponse> index(ServerRequest serverRequest) {
        final String user = serverRequest.queryParam("user")
                .orElse("nobody");
        return ServerResponse
                .ok()
                .render("index", Map.of("user", user));
    }
}
