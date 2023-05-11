package ru.clevertec.ecl.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.model.mapper.UserMapper;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User savedUser = userRepository.save(userMapper.dtoToEntity(userDto));
        return userMapper.entityToDto(savedUser);
    }

    @Override
    public List<UserDto> findAllUsers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<User> users = userRepository.findAll(pageable).getContent();
        return users.stream()
                .map(user -> userMapper.entityToDto(user))
                .toList();
    }

    @Override
    public UserDto findUserById(long id) {
        UserDto userDto = userRepository.findById(id)
                .map(user -> userMapper.entityToDto(user))
                .orElseThrow(() -> new NoSuchElementException("user with id " + id + " wasn't found"));
        return userDto;
    }
}
