package ru.clevertec.ecl.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.model.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@AllArgsConstructor
@Service
public class GiftCertificateServiceImpl implements ru.clevertec.ecl.service.GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepository;
    private GiftCertificateMapper giftCertificateMapper;
    private TagRepository tagRepository;

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
    public List<GiftCertificateDto> findAllGiftCertificates(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
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
    public List<GiftCertificateDto> findByParameters(String tagName, String substring, int page, int pageSize,
                                                     String dateSortType, String nameSortType) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (dateSortType != null) {
            sorts.add(new Sort.Order(Sort.Direction.valueOf(dateSortType), "createDate"));
        }
        if (nameSortType != null) {
            sorts.add(new Sort.Order(Sort.Direction.valueOf(nameSortType), "name"));
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        List<GiftCertificate> giftCertificates = giftCertificateRepository
                .findByParameters(tagName, substring, pageable);

        return giftCertificates.stream()
                .map(certificate -> giftCertificateMapper.entityToDto(certificate))
                .toList();
    }

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

    @Override
    public void deleteGiftCertificateById(long id) {
        giftCertificateRepository.deleteById(id);
    }
}
