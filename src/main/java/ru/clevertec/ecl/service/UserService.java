package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto);

    List<UserDto> findAllUsers(int page, int pageSize);

    UserDto findUserById(long id);
}
