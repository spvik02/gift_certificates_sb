package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.model.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TagServiceImpl.class})
class TagServiceImplTest {

    @Autowired
    private TagService tagService;

    @MockBean
    private TagMapper tagMapper;

    @MockBean
    private TagRepository tagRepository;

    @Nested
    class SaveTag {

        @Test
        void checkSaveTagInvokeRepositoryMethod() {
            Tag tag = TestData.buildTag();
            TagDto tagDto = TestData.buildTagDto();

            when(tagMapper.dtoToEntity(any(TagDto.class)))
                    .thenReturn(tag);
            when(tagRepository.save(any(Tag.class)))
                    .thenReturn(tag);
            when(tagMapper.entityToDto(any(Tag.class)))
                    .thenReturn(tagDto);

            tagService.saveTag(tagDto);

            verify(tagRepository, times(1)).save(any(Tag.class));
        }
    }

    @Nested
    class FindAllTagsPageable {

        @Test
        void checkFindAllTagsPageableInvokeRepositoryMethod() {
            List<Tag> tagList = List.of(TestData.buildTag());
            Page<Tag> tagPage = new PageImpl<>(tagList);

            when(tagRepository.findAll(any(Pageable.class)))
                    .thenReturn(tagPage);
            when(tagMapper.entityToDto(any(Tag.class)))
                    .thenReturn(TestData.buildTagDto());

            tagService.findAllTagsPageable(Pageable.ofSize(3));

            verify(tagRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class FindTagById {

        @Test
        void checkFindTagByIdShouldThrowNoSuchElementException() {
            doReturn(Optional.empty()).when(tagRepository).findById(1L);

            assertThatThrownBy(() -> {
                tagService.findTagById(1L);
            }).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void checkFindTagByIdInvokeRepositoryMethod() {
            Tag tag = TestData.buildTag();
            when(tagRepository.findById(1L))
                    .thenReturn(Optional.of(tag));
            when(tagMapper.entityToDto(any(Tag.class)))
                    .thenReturn(TestData.buildTagDto());

            tagService.findTagById(1L);

            verify(tagRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class FindMostPopularTagOfMostProfitableUser {

        @Test
        void checkFindMostPopularTagOfMostProfitableUserInvokeRepositoryMethod() {
            Tag tag = TestData.buildTag();
            when(tagRepository.findMostPopularTagOfMostProfitableUser())
                    .thenReturn(tag);
            when(tagMapper.entityToDto(any(Tag.class)))
                    .thenReturn(TestData.buildTagDto());

            tagService.findMostPopularTagOfMostProfitableUser();

            verify(tagRepository, times(1)).findMostPopularTagOfMostProfitableUser();
        }
    }

    @Nested
    class UpdateTagById {

        @Test
        void checkUpdateTagByIdShouldThrowNoSuchElementException() {
            when(tagRepository.findById(1L))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> {
                tagService.updateTagById(1L, TestData.buildTagDto());
            }).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void checkUpdateTagByIdInvokeRepositoryMethods() {
            String updatedName = "updated name";
            TagDto tagDtoWithUpdateData = TagDto.builder()
                    .name(updatedName)
                    .build();
            Tag foundedTag = TestData.buildTag();
            Tag updatedTag = TestData.buildTag();
            TagDto updatedTagDto = TestData.buildTagDto();

            updatedTag.setName(updatedName);
            updatedTagDto.setName(updatedName);

            when(tagRepository.findById(1L))
                    .thenReturn(Optional.of(foundedTag));
            when(tagRepository.save(any(Tag.class)))
                    .thenReturn(updatedTag);
            when(tagMapper.entityToDto(any(Tag.class)))
                    .thenReturn(updatedTagDto);

            tagService.updateTagById(1L, tagDtoWithUpdateData);

            verify(tagRepository, times(1)).findById(1L);
            verify(tagRepository, times(1)).save(any(Tag.class));
        }

        @Test
        void checkUpdateGiftCertificateByIdShouldNotInvokeRepositorySaveMethodIfNotFound() {
            String updatedName = "updated name";
            TagDto tagDtoWithUpdateData = TagDto.builder()
                    .name(updatedName)
                    .build();

            when(tagRepository.findById(1L))
                    .thenReturn(Optional.empty());

            try {
                tagService.updateTagById(1L, tagDtoWithUpdateData);
            } catch (Exception ignored) {

            }

            verify(tagRepository, times(1)).findById(1L);
            verify(tagRepository, times(0)).save(any(Tag.class));
        }
    }

    @Nested
    class DeleteTagById {
        @Test
        void checkDeleteTagByIdInvokeRepositoryMethod() {
            doNothing().when(tagRepository).deleteById(anyLong());

            tagService.deleteTagById(1L);

            verify(tagRepository, times(1)).deleteById(1L);
        }
    }
}
