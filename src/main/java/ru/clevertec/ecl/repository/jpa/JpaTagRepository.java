package ru.clevertec.ecl.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.repository.TagRepository;

@Repository
public interface JpaTagRepository extends JpaRepository<Tag, Long>, TagRepository {

}
