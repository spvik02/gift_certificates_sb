package ru.clevertec.ecl.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.repository.GiftCertificateRepository;

@Repository
public interface JpaGiftCertificateRepository
        extends JpaRepository<GiftCertificate, Long>, GiftCertificateRepository {

}
