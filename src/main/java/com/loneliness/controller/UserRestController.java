package com.loneliness.controller;

import com.loneliness.dto.UserDTO;
import com.loneliness.entity.domain.User;
import com.loneliness.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("/edit/user")
public class UserRestController extends CommonRestController<User, UserDTO> {

    public UserRestController(UserService service) {
        this.service = service;
        this.page = "user";
    }

    @GetMapping("/get")
    public User getOneById(@RequestParam(name = "id", required = false) Integer id) {
        if (id != null) {
            return find(id);
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (User) auth.getPrincipal();

        }
    }

    @GetMapping(value = "/byeBook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersThatByeBook(@RequestParam(name = "id") Integer id) {
        return ((UserService) service).findUsersThatByeBook(id).orElse(new LinkedList<>());

    }
}