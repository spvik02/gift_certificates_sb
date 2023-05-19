package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.model.mapper.UserMapper;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.service.UserService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserServiceImpl.class})
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Test
    void saveUser() {
    }
    @Nested
    class SaveTag {

        @Test
        void checkSaveTagInvokeRepositoryMethod() {
            User user = TestData.buildUser();
            UserDto userDto = TestData.buildUserDto();

            when(userMapper.dtoToEntity(any(UserDto.class)))
                    .thenReturn(user);
            when(userRepository.save(any(User.class)))
                    .thenReturn(user);
            when(userMapper.entityToDto(any(User.class)))
                    .thenReturn(userDto);

            userService.saveUser(userDto);

            verify(userRepository, times(1)).save(any(User.class));
        }
    }

    @Nested
    class FindAllUsersPageable {

        @Test
        void checkFindAllUsersPageableInvokeRepositoryMethod() {
            List<User> userList = List.of(TestData.buildUser());
            Page<User> userPage = new PageImpl<>(userList);

            when(userRepository.findAll(any(Pageable.class)))
                    .thenReturn(userPage);
            when(userMapper.entityToDto(any(User.class)))
                    .thenReturn(TestData.buildUserDto());

            userService.findAllUsersPageable(Pageable.ofSize(3));

            verify(userRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class FindUserById {

        @Test
        void checkFindUserByIdShouldThrowNoSuchElementException() {
            doReturn(Optional.empty()).when(userRepository).findById(1L);

            assertThatThrownBy(() -> {
                userService.findUserById(1L);
            }).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void checkFindUserByIdInvokeRepositoryMethod() {
            User user = TestData.buildUser();
            when(userRepository.findById(1L))
                    .thenReturn(Optional.of(user));
            when(userMapper.entityToDto(any(User.class)))
                    .thenReturn(TestData.buildUserDto());

            userService.findUserById(1L);

            verify(userRepository, times(1)).findById(1L);
        }
    }
}
