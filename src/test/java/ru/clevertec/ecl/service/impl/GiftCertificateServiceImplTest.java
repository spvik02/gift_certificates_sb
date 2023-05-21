package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.utils.TestData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GiftCertificateServiceImpl.class})
class GiftCertificateServiceImplTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @MockBean
    private GiftCertificateMapper giftCertificateMapper;

    @MockBean
    private GiftCertificateRepository giftCertificateRepository;

    @MockBean
    private TagRepository tagRepository;

    @Captor
    private ArgumentCaptor<GiftCertificate> certificateCaptor;

    @Nested
    class SaveGiftCertificate {

        @Test
        void checkSaveGiftCertificateShouldSetCreateAndUpdateDateFields() {
            GiftCertificate giftCertificate = TestData.buildGiftCertificateWithoutTags();
            giftCertificate.setId(null);
            GiftCertificateDto giftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();

            when(giftCertificateMapper.dtoToEntity(any(GiftCertificateDto.class)))
                    .thenReturn(giftCertificate);
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(giftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(giftCertificateDto);

            giftCertificateService.saveGiftCertificate(giftCertificateDto);
            verify(giftCertificateRepository).save(certificateCaptor.capture());
            GiftCertificate certificateCaptorValue = certificateCaptor.getValue();

            assertAll(
                    () -> assertNotNull(certificateCaptorValue.getCreateDate()),
                    () -> assertNotNull(certificateCaptorValue.getLastUpdateDate()),
                    () -> assertEquals(certificateCaptorValue.getCreateDate(), certificateCaptorValue.getLastUpdateDate())
            );
        }

        @Test
        void checkSaveGiftCertificateInvokeRepositoryMethod() {
            GiftCertificate giftCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificateDto giftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();

            when(giftCertificateMapper.dtoToEntity(any(GiftCertificateDto.class)))
                    .thenReturn(giftCertificate);
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(giftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(giftCertificateDto);

            giftCertificateService.saveGiftCertificate(TestData.buildGiftCertificateDtoWithoutTags());

            verify(giftCertificateRepository, times(1)).save(any(GiftCertificate.class));
        }
    }

    @Nested
    class FindAllGiftCertificatesPageable {

        @Test
        void checkFindAllGiftCertificatesPageableInvokeRepositoryMethod() {
            List<GiftCertificate> giftCertificateList = List.of(TestData.buildGiftCertificateWithoutTags());
            Page<GiftCertificate> giftCertificatePage = new PageImpl<>(giftCertificateList);
            when(giftCertificateRepository.findAll(any(Pageable.class)))
                    .thenReturn(giftCertificatePage);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(TestData.buildGiftCertificateDtoWithoutTags());

            giftCertificateService.findAllGiftCertificatesPageable(Pageable.ofSize(3));

            verify(giftCertificateRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class FindGiftCertificateById {

        @Test
        void checkFindByIdShouldThrowNoSuchElementException() {
            doReturn(Optional.empty()).when(giftCertificateRepository).findById(1L);

            assertThatThrownBy(() -> {
                giftCertificateService.findGiftCertificateById(1L);
            }).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void checkFindGiftCertificateByIdInvokeRepositoryMethod() {
            GiftCertificate giftCertificate = TestData.buildGiftCertificateWithoutTags();
            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.of(giftCertificate));
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(TestData.buildGiftCertificateDtoWithoutTags());

            giftCertificateService.findGiftCertificateById(1L);

            verify(giftCertificateRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class FindByParametersPageable {

        @Test
        void checkFindByParametersPageableInvokeRepositoryMethod() {
            List<GiftCertificate> giftCertificates = List.of(TestData.buildGiftCertificateWithoutTags());
            when(giftCertificateRepository.findByParameters(any(), any(), any(Pageable.class)))
                    .thenReturn(giftCertificates);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(TestData.buildGiftCertificateDtoWithoutTags());

            giftCertificateService.findByParametersPageable(null, null, Pageable.ofSize(3));

            verify(giftCertificateRepository, times(1)).findByParameters(any(), any(), any(Pageable.class));
        }
    }

    @Nested
    class UpdateGiftCertificateById {

        @Test
        void checkUpdateGiftCertificateByIdShouldThrowNoSuchElementException() {
            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> {
                giftCertificateService.updateGiftCertificateById(1L, TestData.buildGiftCertificateDtoWithoutTags());
            }).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void checkUpdateGiftCertificateByIdShouldUpdateOnlyNameField() {
            String updatedName = "updated name";
            GiftCertificateDto certificateDtoWithUpdateData = GiftCertificateDto.builder()
                    .name(updatedName)
                    .build();
            GiftCertificate foundedCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificate updatedGiftCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificateDto expectedUdatedGiftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            updatedGiftCertificate.setName(updatedName);
            expectedUdatedGiftCertificateDto.setName(updatedName);

            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.of(foundedCertificate));
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(expectedUdatedGiftCertificateDto);

            giftCertificateService.updateGiftCertificateById(1L, certificateDtoWithUpdateData);
            verify(giftCertificateRepository).save(certificateCaptor.capture());
            GiftCertificate updatedCertificateCaptorValue = certificateCaptor.getValue();

            assertAll(
                    () -> assertThat(updatedCertificateCaptorValue.getName()).isEqualTo(updatedName),
                    () -> assertThat(updatedCertificateCaptorValue.getId()).isEqualTo(foundedCertificate.getId()),
                    () -> assertThat(updatedCertificateCaptorValue.getPrice()).isEqualTo(foundedCertificate.getPrice()),
                    () -> assertThat(updatedCertificateCaptorValue.getDescription()).isEqualTo(foundedCertificate.getDescription()),
                    () -> assertThat(updatedCertificateCaptorValue.getDuration()).isEqualTo(foundedCertificate.getDuration())
            );
        }

        @Test
        void checkUpdateGiftCertificateByIdShouldUpdateOnlyDescriptionField() {
            String updatedField = "updated description";
            GiftCertificateDto certificateDtoWithUpdateData = GiftCertificateDto.builder()
                    .description(updatedField)
                    .build();
            GiftCertificate foundedCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificate updatedGiftCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificateDto expectedUdatedGiftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            updatedGiftCertificate.setDescription(updatedField);
            expectedUdatedGiftCertificateDto.setDescription(updatedField);

            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.of(foundedCertificate));
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(expectedUdatedGiftCertificateDto);

            giftCertificateService.updateGiftCertificateById(1L, certificateDtoWithUpdateData);
            verify(giftCertificateRepository).save(certificateCaptor.capture());
            GiftCertificate updatedCertificateCaptorValue = certificateCaptor.getValue();

            assertAll(
                    () -> assertThat(updatedCertificateCaptorValue.getDescription()).isEqualTo(updatedField),
                    () -> assertThat(updatedCertificateCaptorValue.getId()).isEqualTo(foundedCertificate.getId()),
                    () -> assertThat(updatedCertificateCaptorValue.getPrice()).isEqualTo(foundedCertificate.getPrice()),
                    () -> assertThat(updatedCertificateCaptorValue.getName()).isEqualTo(foundedCertificate.getName()),
                    () -> assertThat(updatedCertificateCaptorValue.getDuration()).isEqualTo(foundedCertificate.getDuration())
            );
        }

        @Test
        void checkUpdateGiftCertificateByIdShouldUpdateOnlyPriceField() {
            BigDecimal updatedField = BigDecimal.valueOf(20);
            GiftCertificateDto certificateDtoWithUpdateData = GiftCertificateDto.builder()
                    .price(updatedField)
                    .build();
            GiftCertificate foundedCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificate updatedGiftCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificateDto expectedUdatedGiftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            updatedGiftCertificate.setPrice(updatedField);
            expectedUdatedGiftCertificateDto.setPrice(updatedField);

            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.of(foundedCertificate));
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(expectedUdatedGiftCertificateDto);

            giftCertificateService.updateGiftCertificateById(1L, certificateDtoWithUpdateData);
            verify(giftCertificateRepository).save(certificateCaptor.capture());
            GiftCertificate updatedCertificateCaptorValue = certificateCaptor.getValue();

            assertAll(
                    () -> assertThat(updatedCertificateCaptorValue.getPrice()).isEqualTo(updatedField),
                    () -> assertThat(updatedCertificateCaptorValue.getId()).isEqualTo(foundedCertificate.getId()),
                    () -> assertThat(updatedCertificateCaptorValue.getName()).isEqualTo(foundedCertificate.getName()),
                    () -> assertThat(updatedCertificateCaptorValue.getDescription()).isEqualTo(foundedCertificate.getDescription()),
                    () -> assertThat(updatedCertificateCaptorValue.getDuration()).isEqualTo(foundedCertificate.getDuration())
            );
        }

        @Test
        void checkUpdateGiftCertificateByIdShouldUpdateOnlyDurationField() {
            int updatedDuration = 1;
            GiftCertificateDto certificateDtoWithUpdateData = GiftCertificateDto.builder()
                    .duration(updatedDuration)
                    .build();
            GiftCertificate foundedCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificate updatedGiftCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificateDto expectedUdatedGiftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            updatedGiftCertificate.setDuration(updatedDuration);
            expectedUdatedGiftCertificateDto.setDuration(updatedDuration);

            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.of(foundedCertificate));
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(expectedUdatedGiftCertificateDto);

            giftCertificateService.updateGiftCertificateById(1L, certificateDtoWithUpdateData);
            verify(giftCertificateRepository).save(certificateCaptor.capture());
            GiftCertificate updatedCertificateCaptorValue = certificateCaptor.getValue();

            assertAll(
                    () -> assertThat(updatedCertificateCaptorValue.getDuration()).isEqualTo(updatedDuration),
                    () -> assertThat(updatedCertificateCaptorValue.getId()).isEqualTo(foundedCertificate.getId()),
                    () -> assertThat(updatedCertificateCaptorValue.getPrice()).isEqualTo(foundedCertificate.getPrice()),
                    () -> assertThat(updatedCertificateCaptorValue.getDescription()).isEqualTo(foundedCertificate.getDescription()),
                    () -> assertThat(updatedCertificateCaptorValue.getName()).isEqualTo(foundedCertificate.getName())
            );
        }

        @Test
        void checkUpdateGiftCertificateByIdInvokeRepositoryMethods() {
            String updatedName = "updated name";
            GiftCertificateDto certificateDtoWithUpdateData = GiftCertificateDto.builder()
                    .name(updatedName)
                    .build();
            GiftCertificate foundedCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificate updatedGiftCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificateDto updatedGiftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();

            updatedGiftCertificate.setName(updatedName);
            updatedGiftCertificateDto.setName(updatedName);

            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.of(foundedCertificate));
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificateDto);

            giftCertificateService.updateGiftCertificateById(1L, certificateDtoWithUpdateData);

            verify(giftCertificateRepository, times(1)).findById(1L);
            verify(giftCertificateRepository, times(1)).save(any(GiftCertificate.class));
        }

        @Test
        void checkUpdateGiftCertificateByIdShouldNotInvokeRepositorySaveMethodIfNotFound() {
            String updatedName = "updated name";
            GiftCertificateDto certificateDtoWithUpdateData = GiftCertificateDto.builder()
                    .name(updatedName)
                    .build();

            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.empty());

            try {
                giftCertificateService.updateGiftCertificateById(1L, certificateDtoWithUpdateData);
            } catch (Exception ignored) {

            }

            verify(giftCertificateRepository, times(1)).findById(1L);
            verify(giftCertificateRepository, times(0)).save(any(GiftCertificate.class));
        }

        @Test
        void checkUpdateGiftCertificateByIdShouldUpdateLastUpdateDateField() {
            String updatedName = "updated name";
            GiftCertificateDto certificateDtoWithUpdateData = GiftCertificateDto.builder()
                    .name(updatedName)
                    .build();
            GiftCertificate foundedCertificate = TestData.buildGiftCertificateWithoutTags();
            LocalDateTime previousLastUpdateDate = foundedCertificate.getLastUpdateDate();
            GiftCertificate updatedGiftCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificateDto updatedGiftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            updatedGiftCertificate.setName(updatedName);
            updatedGiftCertificateDto.setName(updatedName);

            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.of(foundedCertificate));
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificateDto);

            giftCertificateService.updateGiftCertificateById(1L, certificateDtoWithUpdateData);
            verify(giftCertificateRepository).save(certificateCaptor.capture());
            GiftCertificate updatedCertificateCaptorValue = certificateCaptor.getValue();

            assertThat(updatedCertificateCaptorValue.getLastUpdateDate())
                    .isNotEqualTo(previousLastUpdateDate);
        }

        @Test
        void checkUpdateGiftCertificateByIdShouldNotUpdateCreateDateField() {
            String updatedName = "updated name";
            GiftCertificateDto certificateDtoWithUpdateData = GiftCertificateDto.builder()
                    .name(updatedName)
                    .build();
            GiftCertificate foundedCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificate updatedGiftCertificate = TestData.buildGiftCertificateWithoutTags();
            GiftCertificateDto expectedUdatedGiftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
            updatedGiftCertificate.setName(updatedName);
            expectedUdatedGiftCertificateDto.setName(updatedName);

            when(giftCertificateRepository.findById(1L))
                    .thenReturn(Optional.of(foundedCertificate));
            when(giftCertificateRepository.save(any(GiftCertificate.class)))
                    .thenReturn(updatedGiftCertificate);
            when(giftCertificateMapper.entityToDto(any(GiftCertificate.class)))
                    .thenReturn(expectedUdatedGiftCertificateDto);

            giftCertificateService.updateGiftCertificateById(1L, certificateDtoWithUpdateData);
            verify(giftCertificateRepository).save(certificateCaptor.capture());
            GiftCertificate updatedCertificateCaptorValue = certificateCaptor.getValue();

            assertThat(updatedCertificateCaptorValue.getCreateDate()).isEqualTo(foundedCertificate.getCreateDate());
        }
    }

    @Nested
    class DeleteGiftCertificateById {
        @Test
        void checkDeleteGiftCertificateByIdInvokeRepositoryMethod() {
            doNothing().when(giftCertificateRepository).deleteById(anyLong());

            giftCertificateService.deleteGiftCertificateById(1L);

            verify(giftCertificateRepository, times(1)).deleteById(1L);
        }
    }
}
