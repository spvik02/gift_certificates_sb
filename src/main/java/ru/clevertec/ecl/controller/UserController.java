package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

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
     * @param pageable
     * @return ResponseEntity with list of users
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers(@PageableDefault(size = 20) Pageable pageable) {
        List<UserDto> users = userService.findAllUsersPageable(pageable);
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
