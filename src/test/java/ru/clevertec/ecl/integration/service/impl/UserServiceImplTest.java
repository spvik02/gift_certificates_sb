package ru.clevertec.ecl.integration.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.service.UserService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void checkSaveUserShouldReturnUserWithNotNullId() {
        UserDto userDto = TestData.buildUserDto();
        userDto.setId(null);

        UserDto actualUser = userService.saveUser(userDto);

        assertThat(actualUser.getId()).isNotNull();
    }

    @Test
    void checkFindAllUserPageableShouldReturn2() {
        int expectedCount = 2;

        List<UserDto> actualUsers = userService.findAllUsersPageable(Pageable.ofSize(5));

        assertThat(actualUsers).hasSize(expectedCount);
    }

    @Test
    void checkFindAllUserPageableShouldReturn1() {
        int expectedCount = 1;

        List<UserDto> actualUsers = userService.findAllUsersPageable(Pageable.ofSize(1));

        assertThat(actualUsers).hasSize(expectedCount);
    }

    @Test
    void checkFindUserByIdShouldReturnUserWithId1() {
        long expectedId = 1;

        UserDto actualTag = userService.findUserById(1L);

        assertThat(actualTag.getId()).isEqualTo(expectedId);
    }
}
