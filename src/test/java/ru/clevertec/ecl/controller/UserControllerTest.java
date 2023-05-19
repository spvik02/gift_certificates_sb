package ru.clevertec.ecl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.service.UserService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Nested
    class SaveUser {

        @Test
        void checkSaveUserValidTest() throws Exception {
            UserDto userDto = TestData.buildUserDto();
            when(userService.saveUser(userDto))
                    .thenReturn(userDto);

            mockMvc.perform(post("/api/users")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(objectMapper.writeValueAsString(userDto)));
        }
    }

    @Nested
    class FindAllUsers {

        @Test
        void checkFindAllUsersValidTest() throws Exception {
            List<UserDto> userDtoList = List.of(TestData.buildUserDto());
            when(userService.findAllUsersPageable(any(Pageable.class)))
                    .thenReturn(userDtoList);

            mockMvc.perform(get("/api/users")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(userDtoList)));
        }

        @Test
        void checkFindAllUsersWithInvalidParamsWillNotFail() throws Exception {
            List<UserDto> userDtoList = List.of(TestData.buildUserDto());
            when(userService.findAllUsersPageable(any(Pageable.class)))
                    .thenReturn(userDtoList);

            mockMvc.perform(get("/api/users")
                            .param("size", "five")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(userDtoList)));
        }
    }

    @Nested
    class FindsUserById {

        @Test
        void checkFindsUserById() throws Exception {
            UserDto userDto = TestData.buildUserDto();
            long id = 1L;
            when(userService.findUserById(id))
                    .thenReturn(userDto);

            mockMvc.perform(get("/api/users/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(userDto)));
        }

        @Test
        void checkFindOrderByIdWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(get("/api/users/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
