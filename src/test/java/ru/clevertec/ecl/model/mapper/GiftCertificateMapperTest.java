package ru.clevertec.ecl.model.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GiftCertificateMapperTest {

    @Autowired
    private GiftCertificateMapper mapper;

    @Test
    void checkDtoToEntity() {
        GiftCertificateDto dto = TestData.buildGiftCertificateDtoWithTags();
        GiftCertificate expectedCertificate = TestData.buildGiftCertificateWithTags();

        GiftCertificate actualCertificate = mapper.dtoToEntity(dto);

        assertThat(actualCertificate).isEqualTo(expectedCertificate);
    }

    @Test
    void checkEntityToDto() {
        GiftCertificateDto expectedDto = TestData.buildGiftCertificateDtoWithTags();
        GiftCertificate certificate = TestData.buildGiftCertificateWithTags();

        GiftCertificateDto actualDto = mapper.entityToDto(certificate);

        assertThat(actualDto).isEqualTo(expectedDto);
    }
}
