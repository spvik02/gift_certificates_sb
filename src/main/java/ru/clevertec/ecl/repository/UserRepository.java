package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(long id);
}
