package com.rohitgangwar.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "/auth/userbyid",
            "/auth/userbyemail",
            "/auth/fetchByUserIds",
            "/content/board/getboards",
            "/content/board/fetchByBoardIds",
            "/content/board/get",
            "/content/pin/get",
            "/content/pin/getpins",
            "/collab/follow/followers",
            "/collab/follow/followings"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}

