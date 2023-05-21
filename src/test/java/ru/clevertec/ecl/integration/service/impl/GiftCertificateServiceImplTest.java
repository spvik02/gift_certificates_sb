package ru.clevertec.ecl.integration.service.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class GiftCertificateServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void checkSaveGiftCertificateShouldReturnGiftCertificateWithNotNullId() {
        GiftCertificateDto giftCertificateDto = TestData.buildGiftCertificateDtoWithoutTags();
        giftCertificateDto.setId(null);

        GiftCertificateDto actualCertificate = giftCertificateService.saveGiftCertificate(giftCertificateDto);

        assertThat(actualCertificate.getId()).isNotNull();
    }

    @Test
    void checkFindAllGiftCertificatesPageableShouldReturn3() {
        int expectedCount = 3;

        List<GiftCertificateDto> actualCertificates = giftCertificateService.findAllGiftCertificatesPageable(Pageable.ofSize(5));

        assertThat(actualCertificates).hasSize(expectedCount);
    }

    @Test
    void checkFindAllGiftCertificatesPageableShouldReturn2() {
        int expectedCount = 2;

        List<GiftCertificateDto> actualCertificates = giftCertificateService.findAllGiftCertificatesPageable(Pageable.ofSize(2));

        assertThat(actualCertificates).hasSize(expectedCount);
    }

    @Test
    void checkFindGiftCertificateByIdShouldReturnTagWithId1() {
        long expectedId = 1;

        GiftCertificateDto actualCertificate = giftCertificateService.findGiftCertificateById(1L);

        assertThat(actualCertificate.getId()).isEqualTo(expectedId);
    }


    @Test
    void checkDeleteGiftCertificateByIdShouldNotFindGiftCertificateByDeletedId() {
        long expectedId = 1;

        giftCertificateService.deleteGiftCertificateById(expectedId);

        entityManager.flush();

        assertThatThrownBy(() -> {
            giftCertificateService.findGiftCertificateById(expectedId);
        }).isInstanceOf(NoSuchElementException.class);
    }
}
