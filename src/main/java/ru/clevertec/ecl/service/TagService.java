package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.TagDto;

import java.util.List;

public interface TagService {

    TagDto saveTag(TagDto tagDto);

    List<TagDto> findAllTagsPageable(Pageable pageable);

    TagDto findTagById(long id);

    TagDto findMostPopularTagOfMostProfitableUser();

    TagDto updateTagById(long id, TagDto tagDto);

    void deleteTagById(long id);
}
