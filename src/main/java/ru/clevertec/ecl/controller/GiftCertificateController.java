package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.service.GiftCertificateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    /**
     * Creates new GiftCertificate with provided data. Creates passed tags if they don't exist
     *
     * @param giftCertificateDto GiftCertificate data
     * @return ResponseEntity with saved GiftCertificate
     */
    @PostMapping
    public ResponseEntity<GiftCertificateDto> createGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto savedGiftCertificate = giftCertificateService.saveGiftCertificate(giftCertificateDto);
        return new ResponseEntity<>(savedGiftCertificate, HttpStatus.CREATED);
    }

    /**
     * Returns list of GiftCertificate within the specified range
     *
     * @param pageable
     * @return ResponseEntity with list of GiftCertificate
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAllGiftCertificates(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        List<GiftCertificateDto> giftCertificates = giftCertificateService.findAllGiftCertificatesPageable(pageable);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    /**
     * Returns gift certificate with provided id
     *
     * @param id gift certificate id
     * @return ResponseEntity with gift certificate with provided id
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findGiftCertificateById(@PathVariable Long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findGiftCertificateById(id);
        return new ResponseEntity<>(giftCertificateDto, HttpStatus.OK);
    }

    /**
     * Returns gift certificates filtered and sorted by passed parameters
     *
     * @param tagName   full tag name
     * @param substring part of a string to search by name/description
     * @param pageable
     * @return ResponseEntity with list of certificates matching the passed parameters
     */
    @GetMapping("/filter")
    public ResponseEntity<List<GiftCertificateDto>> findByParameters(
            @RequestParam(value = "tagName", required = false) String tagName,
            @RequestParam(value = "substring", required = false) String substring,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        List<GiftCertificateDto> giftCertificates = giftCertificateService
                .findByParametersPageable(tagName, substring, pageable);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    /**
     * Updates GiftCertificate with data passed in request body. If field is not specified in request body the data is not updated
     *
     * @param id                 GiftCertificate id for updating
     * @param giftCertificateDto request body containing GiftCertificate data
     * @return updated GiftCertificateDto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateGiftCertificatePartially(
            @PathVariable("id") long id,
            @RequestBody GiftCertificateDto giftCertificateDto
    ) {
        GiftCertificateDto giftCertificateUpdated = giftCertificateService
                .updateGiftCertificateById(id, giftCertificateDto);
        return new ResponseEntity<>(giftCertificateUpdated, HttpStatus.OK);
    }

    /**
     * Deletes passed GiftCertificate. Body can contain only id
     *
     * @param id gift certificate id for deletion
     * @return empty ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeGiftCertificate(@PathVariable("id") long id) {
        giftCertificateService.deleteGiftCertificateById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
