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
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.service.GiftCertificateService;
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
class GiftCertificateControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    GiftCertificateService giftCertificateService;


    @Nested
    class CreateGiftCertificate {

        @Test
        void checkCreateGiftCertificateValidTest() throws Exception {
            GiftCertificateDto giftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            when(giftCertificateService.saveGiftCertificate(giftCertificateDto))
                    .thenReturn(giftCertificateDto);

            mockMvc.perform(post("/api/certificates")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(giftCertificateDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(objectMapper.writeValueAsString(giftCertificateDto)));
        }
    }

    @Nested
    class FindAllGiftCertificates {

        @Test
        void checkFindAllGiftCertificatesValidTest() throws Exception {
            List<GiftCertificateDto> giftCertificateDtoList = List.of(TestData.buildGiftCertificateDtoWithoutTags());
            when(giftCertificateService.findAllGiftCertificatesPageable(any(Pageable.class)))
                    .thenReturn(giftCertificateDtoList);

            mockMvc.perform(get("/api/certificates")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(giftCertificateDtoList)));
        }

        @Test
        void checkFindAllGiftCertificatesWithInvalidParamsWillNotFail() throws Exception {
            List<GiftCertificateDto> giftCertificateDtoList = List.of(TestData.buildGiftCertificateDtoWithoutTags());
            when(giftCertificateService.findAllGiftCertificatesPageable(any(Pageable.class)))
                    .thenReturn(giftCertificateDtoList);

            mockMvc.perform(get("/api/certificates")
                            .param("size", "five")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(giftCertificateDtoList)));
        }
    }

    @Nested
    class FindGiftCertificateById {

        @Test
        void checkFindGiftCertificateByIdValidTest() throws Exception {
            long id = 1L;
            GiftCertificateDto giftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            when(giftCertificateService.findGiftCertificateById(id))
                    .thenReturn(giftCertificateDto);

            mockMvc.perform(get("/api/certificates/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(giftCertificateDto)));
        }

        @Test
        void checkFindGiftCertificateByIdWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(get("/api/certificates/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class FindByParameters {

        @Test
        void checkFindByParametersValidTest() throws Exception {
            List<GiftCertificateDto> giftCertificateDtoList = List.of(TestData.buildGiftCertificateDtoWithoutTags());
            when(giftCertificateService.findByParametersPageable(any(), any(), any(Pageable.class)))
                    .thenReturn(giftCertificateDtoList);

            mockMvc.perform(get("/api/certificates/filter")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(giftCertificateDtoList)));
        }
    }

    @Nested
    class UpdateGiftCertificatePartially {

        @Test
        void checkUpdateGiftCertificatePartiallyValidTest() throws Exception {
            long id = 1L;
            GiftCertificateDto giftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            when(giftCertificateService.updateGiftCertificateById(id, giftCertificateDto))
                    .thenReturn(giftCertificateDto);

            mockMvc.perform(patch("/api/certificates/1")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(giftCertificateDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(giftCertificateDto)));
        }

        @Test
        void checkUpdateGiftCertificatePartiallyWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(patch("/api/certificates/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class RemoveGiftCertificate {

        @Test
        void checkRemoveGiftCertificateValidTest() throws Exception {

            mockMvc.perform(delete("/api/certificates/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void checkRemoveGiftCertificateWithMethodArgumentTypeMismatchEndWithBadRequest() throws Exception {

            mockMvc.perform(delete("/api/certificates/one")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
