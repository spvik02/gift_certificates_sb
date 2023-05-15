package ru.clevertec.ecl.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.model.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.service.GiftCertificateService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto) {

        GiftCertificate giftCertificate = giftCertificateMapper.dtoToEntity(giftCertificateDto);
        List<Tag> tags = new ArrayList<>();

        for (Tag tag : giftCertificate.getTags()) {
            tags.add(tag.getId() == null
                    ? tagRepository.save(tag)
                    : tagRepository.findById(tag.getId()).get());
        }

        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificate.setTags(tags);

        GiftCertificate savedGiftCertificate = giftCertificateRepository.save(giftCertificate);
        return giftCertificateMapper.entityToDto(savedGiftCertificate);
    }

    @Override
    public List<GiftCertificateDto> findAllGiftCertificatesPageable(Pageable pageable) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll(pageable).getContent();
        return giftCertificates.stream()
                .map(certificate -> giftCertificateMapper.entityToDto(certificate))
                .toList();
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateRepository.findById(id)
                .map(certificate -> giftCertificateMapper.entityToDto(certificate))
                .orElseThrow(() -> new NoSuchElementException("gift certificate with id " + id + " wasn't found"));
        return giftCertificateDto;
    }

    @Override
    public List<GiftCertificateDto> findByParametersPageable(String tagName, String substring, Pageable pageable) {

        List<GiftCertificate> giftCertificates = giftCertificateRepository
                .findByParameters(tagName, substring, pageable);

        return giftCertificates.stream()
                .map(certificate -> giftCertificateMapper.entityToDto(certificate))
                .toList();
    }

    @Transactional
    @Override
    public GiftCertificateDto updateGiftCertificateById(long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificateForUpdate = giftCertificateRepository.findById(id)
                .map(certificate -> {
                    if (Objects.nonNull(giftCertificateDto.getName())) {
                        certificate.setName(giftCertificateDto.getName());
                    }
                    if (Objects.nonNull(giftCertificateDto.getDescription())) {
                        certificate.setDescription(giftCertificateDto.getDescription());
                    }
                    if (Objects.nonNull(giftCertificateDto.getPrice())) {
                        certificate.setPrice(giftCertificateDto.getPrice());
                    }
                    if (0 != giftCertificateDto.getDuration()) {
                        certificate.setDuration(giftCertificateDto.getDuration());
                    }
                    return certificate;
                }).orElseThrow(() -> new NoSuchElementException("gift certificate with id " + id + " wasn't found"));

        certificateForUpdate.setLastUpdateDate(LocalDateTime.now());

        GiftCertificate updatedUser = giftCertificateRepository.save(certificateForUpdate);

        return giftCertificateMapper.entityToDto(updatedUser);
    }

    @Transactional
    @Override
    public void deleteGiftCertificateById(long id) {
        giftCertificateRepository.deleteById(id);
    }
}
