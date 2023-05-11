package ru.clevertec.ecl.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.repository.UserRepository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

}
