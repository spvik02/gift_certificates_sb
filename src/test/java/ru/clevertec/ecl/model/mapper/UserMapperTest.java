package ru.clevertec.ecl.model.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void checkDtoToEntity() {
        UserDto dto = TestData.buildUserDto();
        User expectedUser = TestData.buildUser();

        User actualUser = mapper.dtoToEntity(dto);

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void checkEntityToDto() {
        UserDto expectedDto = TestData.buildUserDto();
        User user = TestData.buildUser();

        UserDto actualDto = mapper.entityToDto(user);

        assertThat(actualDto).isEqualTo(expectedDto);
    }
}
