package com.fhcs.clothing_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fhcs.clothing_store.dto.response.UserResponse;
import com.fhcs.clothing_store.entity.User;
import com.fhcs.clothing_store.service.UserService;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUserInformation(@RequestHeader("Authorization") String token) {

        try {
            String accessToken = token.substring(7);
            User user = userService.getUserInformation(accessToken);

            UserResponse response = UserResponse.success(user,
                    "Informações do usuário recuperadas com sucesso");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(UserResponse.error("Erro ao recuperar informações do usuário: " + e.getMessage()));
        }
    }

    @PatchMapping("/info")
    public ResponseEntity<UserResponse> updateUserInformation(@RequestHeader("Authorization") String token,
            @RequestBody JsonPatch patch) {

        try {
            String accessToken = token.substring(7);
            User newUser = userService.updateUserInformation(accessToken, patch);

            UserResponse response = UserResponse.success(newUser,
                    "Informações do usuário atualizadas com sucesso");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UserResponse.error("Erro ao atualizar informações do usuário: " + e.getMessage()));
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<UserResponse> deleteUser(@RequestHeader("Authorization") String token) {

        try {
            String accessToken = token.substring(7);
            userService.deleteUser(accessToken);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(UserResponse.messageOnly("Usuário deletado com sucesso!", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UserResponse.error("Erro ao deletar usuário: " + e.getMessage()));
        }
    }
}
