package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @Interface UserService
 * @Impl UserServiceImpl
 */
public interface UserService extends UserDetailsService {
//    Long getGenerateValueId();
    List<UserDTO> findAll();
    UserDTO findOne(Long id);
    UserDTO findByUsername(String name);

    Long create(String username, String password, String country);
    UserDTO update(Long id, String username, String country);
    void delete(Long id);
}
