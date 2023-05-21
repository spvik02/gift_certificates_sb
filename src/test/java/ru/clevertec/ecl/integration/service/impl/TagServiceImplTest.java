package ru.clevertec.ecl.integration.service.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TagServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void checkSaveTagShouldReturnTagWithNotNullId() {
        TagDto tagDto = TestData.buildTagDto();
        tagDto.setId(null);

        TagDto actualTag = tagService.saveTag(tagDto);

        assertThat(actualTag.getId()).isNotNull();
    }

    @Test
    void checkFindAllTagsPageableShouldReturn3() {
        int expectedTagCount = 3;

        List<TagDto> actualTags = tagService.findAllTagsPageable(Pageable.ofSize(5));

        assertThat(actualTags).hasSize(expectedTagCount);
    }

    @Test
    void checkFindAllGiftCertificatesPageableShouldReturn2() {
        int expectedCount = 2;

        List<TagDto> actualTags = tagService.findAllTagsPageable(Pageable.ofSize(2));

        assertThat(actualTags).hasSize(expectedCount);
    }

    @Test
    void checkFindTagByIdShouldReturnTagWithId1() {
        long expectedId = 1;

        TagDto actualTag = tagService.findTagById(1L);

        assertThat(actualTag.getId()).isEqualTo(expectedId);
    }

    @Test
    void checkFindMostPopularTagOfMostProfitableUserShouldReturnTagWithId1() {
        long expectedId = 3;

        TagDto actualTag = tagService.findMostPopularTagOfMostProfitableUser();

        assertThat(actualTag.getId()).isEqualTo(expectedId);
    }

    @Test
    void checkDeleteTagByIdShouldNotFindTagByDeletedId() {
        long expectedId = 1;

        tagService.deleteTagById(expectedId);

        entityManager.flush();

        assertThatThrownBy(() -> {
            tagService.findTagById(expectedId);
        }).isInstanceOf(NoSuchElementException.class);
    }
}
