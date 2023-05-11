package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.dto.TagDto;

import java.util.List;

public interface TagService {

    TagDto saveTag(TagDto tagDto);

    List<TagDto> findAllTags(int page, int pageSize);

    TagDto findTagById(long id);

    TagDto findMostPopularTagOfMostProfitableUser();

    TagDto updateTagById(long id, TagDto tagDto);

    void deleteTagById(long id);
}
