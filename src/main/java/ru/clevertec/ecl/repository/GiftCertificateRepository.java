package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {

    GiftCertificate save(GiftCertificate giftCertificate);

    Page<GiftCertificate> findAll(Pageable pageable);

    Optional<GiftCertificate> findById(long id);

    @Query(value = """
            from GiftCertificate gc left join fetch gc.tags t
            where (:tagName is null or t.name = :tagName)
                and (:substring is null or gc.name LIKE %:substring% OR gc.description LIKE %:substring%)
            """)
    List<GiftCertificate> findByParameters(String tagName, String substring, Pageable pageable);

    void deleteById(long id);
}
