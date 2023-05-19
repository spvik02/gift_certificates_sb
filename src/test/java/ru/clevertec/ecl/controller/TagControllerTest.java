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
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TagService tagService;

    @Nested
    class SaveTag {

        @Test
        void checkSaveTagValidTest() throws Exception {
            TagDto tagDto = TestData.buildTagDto();
            when(tagService.saveTag(tagDto))
                    .thenReturn(tagDto);

            mockMvc.perform(post("/api/tags")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(tagDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(objectMapper.writeValueAsString(tagDto)));
        }
    }

    @Nested
    class FindAllTags {

        @Test
        void checkFindAllTagsValidTest() throws Exception {
            List<TagDto> tagDtoList = List.of(TestData.buildTagDto());
            when(tagService.findAllTagsPageable(any(Pageable.class)))
                    .thenReturn(tagDtoList);

            mockMvc.perform(get("/api/tags")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(tagDtoList)));
        }

        @Test
        void checkFindAllTagsWithInvalidParamsWillNotFail() throws Exception {
            List<TagDto> tagDtoList = List.of(TestData.buildTagDto());
            when(tagService.findAllTagsPageable(any(Pageable.class)))
                    .thenReturn(tagDtoList);

            mockMvc.perform(get("/api/tags")
                            .param("size", "five")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(tagDtoList)));
        }
    }

    @Nested
    class FindMostPopularTagOfMostProfitableUser {

        @Test
        void checkFindMostPopularTagOfMostProfitableUserValidTest() throws Exception {
            TagDto tagDto = TestData.buildTagDto();
            when(tagService.findMostPopularTagOfMostProfitableUser())
                    .thenReturn(tagDto);

            mockMvc.perform(get("/api/tags/popular")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(tagDto)));
        }
    }

    @Nested
    class FindTagById {

        @Test
        void checkFindTagByIdValidTest() throws Exception {
            long id = 1L;
            TagDto tagDto = TestData.buildTagDto();
            when(tagService.findTagById(id))
                    .thenReturn(tagDto);

            mockMvc.perform(get("/api/tags/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(tagDto)));
        }

        @Test
        void checkFindTagByIdWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(get("/api/tags/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class UpdateTag {

        @Test
        void checkUpdateGiftCertificatePartiallyValidTest() throws Exception {
            long id = 1L;
            TagDto tagDto = TestData.buildTagDto();
            when(tagService.updateTagById(id, tagDto))
                    .thenReturn(tagDto);

            mockMvc.perform(patch("/api/tags/1")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(tagDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(tagDto)));
        }

        @Test
        void checkUpdateGiftCertificatePartiallyWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(patch("/api/tags/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class DeleteTag {

        @Test
        void checkDeleteTagValidTest() throws Exception {
            mockMvc.perform(delete("/api/tags/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void checkDeleteTagWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {
            mockMvc.perform(delete("/api/tags/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
