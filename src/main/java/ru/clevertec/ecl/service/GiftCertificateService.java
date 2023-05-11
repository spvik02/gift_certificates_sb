package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findAllGiftCertificates(int page, int pageSize);

    GiftCertificateDto findGiftCertificateById(long id);

    List<GiftCertificateDto> findByParameters(String tagName, String substring, int page, int pageSize,
                                              String dateSortType, String nameSortType);

    GiftCertificateDto updateGiftCertificateById(long id, GiftCertificateDto giftCertificateDto);

    void deleteGiftCertificateById(long id);
}
