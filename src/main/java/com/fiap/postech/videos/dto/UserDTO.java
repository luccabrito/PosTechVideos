package com.fiap.postech.videos.dto;

import com.fiap.postech.videos.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String senha;

    public User dtoToEntity(UserDTO userDTO) {
        User novoUser = new User();
        novoUser.setUsername(username);
        novoUser.setSenha(senha);
        return novoUser;
    }
}
