package com.example.cardapio_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardapio_api.domain.user.User;
import com.example.cardapio_api.dtos.LoginRequestDTO;
import com.example.cardapio_api.dtos.LoginResponseDTO;
import com.example.cardapio_api.dtos.RecoverResponseDTO;
import com.example.cardapio_api.dtos.RegisterRequestDTO;
import com.example.cardapio_api.dtos.RegisterResponseDTO;
import com.example.cardapio_api.infra.security.TokenService;
import com.example.cardapio_api.services.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequestDTO data, HttpServletResponse response)
            throws Exception {
        LoginResponseDTO loginResponse = service.login(data);
        this.setCookie(loginResponse.token(), response);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        User user = service.register(dto);
        return ResponseEntity.ok(new RegisterResponseDTO(user));
    }

    @GetMapping("/recover")
    public ResponseEntity<?> recoverGet(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String authHeader = request.getHeader("Authorization");
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            User user = service.recover(token);
            return ResponseEntity.ok(new RecoverResponseDTO(user));
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                User user = service.recover(cookie.getValue());
                return ResponseEntity.ok(new RecoverResponseDTO(user));
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/recover")
    public ResponseEntity<?> recoverPost(@RequestBody String data) {
        System.out.println(data);
        User user = service.recover(data);
        System.out.println(user.getEmail());
        return ResponseEntity.ok(new RecoverResponseDTO(user));

    }

    private void setCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", token);
        cookie.isHttpOnly();
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(tokenService.maxAge);
        response.addCookie(cookie);
    }
}
