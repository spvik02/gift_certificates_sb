package ru.clevertec.ecl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    /**
     * Creates new User with provided data
     *
     * @param userDto user data
     * @return ResponseEntity with saved user
     */
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.saveUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * Returns list of users within the specified range
     *
     * @param page     value in range [0..n]
     * @param pageSize the number of users per page
     * @return ResponseEntity with list of users
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        List<UserDto> users = userService.findAllUsers(page, pageSize);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Finds user with provided id
     *
     * @param id user id
     * @return ResponseEntity with user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findsUserById(@PathVariable("id") long id) {
        UserDto user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
