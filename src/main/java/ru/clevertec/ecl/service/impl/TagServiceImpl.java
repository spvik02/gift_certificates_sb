package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.model.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.service.TagService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Transactional
    @Override
    public TagDto saveTag(TagDto tagDto) {
        Tag savedTag = tagRepository.save(tagMapper.dtoToEntity(tagDto));
        return tagMapper.entityToDto(savedTag);
    }

    @Override
    public List<TagDto> findAllTagsPageable(Pageable pageable) {
        List<Tag> tags = tagRepository.findAll(pageable).getContent();
        return tags.stream()
                .map(tag -> tagMapper.entityToDto(tag))
                .toList();
    }

    @Override
    public TagDto findTagById(long id) {
        TagDto tagDto = tagRepository.findById(id)
                .map(user -> tagMapper.entityToDto(user))
                .orElseThrow(() -> new NoSuchElementException("tag with id " + id + " wasn't found"));
        return tagDto;
    }

    @Override
    public TagDto findMostPopularTagOfMostProfitableUser() {
        Tag tag = tagRepository.findMostPopularTagOfMostProfitableUser();
        return tagMapper.entityToDto(tag);
    }

    @Transactional
    @Override
    public TagDto updateTagById(long id, TagDto tagDto) {
        Tag tagForUpdate = tagRepository.findById(id)
                .map(tag -> {
                    if (Objects.nonNull(tagDto.getName())) {
                        tag.setName(tagDto.getName());
                    }
                    return tag;
                })
                .orElseThrow(() -> new NoSuchElementException("tag with id " + id + " wasn't found"));
        Tag updatedTag = tagRepository.save(tagForUpdate);

        return tagMapper.entityToDto(updatedTag);
    }

    @Transactional
    @Override
    public void deleteTagById(long id) {
        tagRepository.deleteById(id);
    }
}
