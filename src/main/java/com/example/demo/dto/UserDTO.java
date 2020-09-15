package com.example.demo.dto;

import com.example.demo.vo.UserVO;
import lombok.Builder;
import lombok.Data;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;


@Data
public class UserDTO {
    private Long id;
    @NotEmpty(message = "이 입력란을 작성하세요.")
    private String username;
    @NotEmpty(message = "이 입력란을 작성하세요.")
    private String password;
    @NotEmpty(message = "이 입력란을 작성하세요.")
    private String confirmPassword;

    private String country;

    @Builder
    public UserDTO(Long id, String username, String password, String country){
        this.id = id;
        this.username = username;
        this.password = password;
        this.country = country;
    }

    public UserDTO(String username, String country){
        this.username = username;
        this.country = country;
    }

    public UserDTO(){}

    public UserVO toEntity(){
        return UserVO.builder()
                .id(id)
                .username(username)
                .password(password)
                .country(country)
                .build();
    }
}
