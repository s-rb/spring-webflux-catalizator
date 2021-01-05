package ru.list.surkovr.springwebfluxcatalizator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import ru.list.surkovr.springwebfluxcatalizator.handlers.GreetingHandler;

@Configuration
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        final RequestPredicate route = RequestPredicates
                .GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));
        return RouterFunctions
                .route(route, greetingHandler::hello)
                .andRoute(RequestPredicates.GET("/"), greetingHandler::index);
    }
}
