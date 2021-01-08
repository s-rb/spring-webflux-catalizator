package ru.list.surkovr.springwebfluxcatalizator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.list.surkovr.springwebfluxcatalizator.config.JwtUtil;
import ru.list.surkovr.springwebfluxcatalizator.domain.User;
import ru.list.surkovr.springwebfluxcatalizator.service.UserService;

import java.util.Objects;

@RestController
public class UserController {

    private final static ResponseEntity<Object> UNAUTHORIZED = ResponseEntity
            .status(HttpStatus.UNAUTHORIZED).build();

    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange swe) {
        return swe.getFormData().flatMap(credentials ->
            userService.findByUsername(credentials.getFirst("username"))
                    .cast(User.class)
                    .map(userDetails ->
                        // Todo шифрование добавить
                        Objects.equals(
                                credentials.getFirst("password"),
                                userDetails.getPassword()
                        )
                                ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                : UNAUTHORIZED
                    )
        .defaultIfEmpty(UNAUTHORIZED));
    }
}
