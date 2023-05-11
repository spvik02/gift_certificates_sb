package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Page<Tag> findAll(Pageable pageable);

    Optional<Tag> findById(long id);

    void deleteById(long id);

    @Query(value = """
            select tag.id, tag.name, count(tag.id)
            from (
            	select users.id, sum(price) 
            	from users 
            	join orders ON users.id = orders.user_id
            	group by users.id
            	order by sum(price) desc 
            	limit 1
            ) as profitableUser 
            join orders on orders.user_id = profitableUser.id 
            join tag_in_certificate on orders.gift_certificate_id = tag_in_certificate.certificate_id
            join tag on tag.id = tag_in_certificate.tag_id
            group by tag.id, tag.name
            order by 3 DESC
            limit 1;
            """,
            nativeQuery = true
    )
    Tag findMostPopularTagOfMostProfitableUser();
}
