package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findAllGiftCertificatesPageable(Pageable pageable);

    GiftCertificateDto findGiftCertificateById(long id);

    List<GiftCertificateDto> findByParametersPageable(String tagName, String substring, Pageable pageable);

    GiftCertificateDto updateGiftCertificateById(long id, GiftCertificateDto giftCertificateDto);

    void deleteGiftCertificateById(long id);
}
