package com.gustavo.ecommerce_api.controller;

import com.gustavo.ecommerce_api.model.Usuario;
import com.gustavo.ecommerce_api.repository.UsuarioRepository;
import com.gustavo.ecommerce_api.security.JwtService;
import lombok.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder =
                new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

        if (!encoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.gerarToken(usuario.getUsername());

        return new LoginResponse(token);
    }

    @Getter @Setter
    static class LoginRequest {
        private String username;
        private String password;
    }

    @AllArgsConstructor
    @Getter
    static class LoginResponse {
        private String token;
    }
}