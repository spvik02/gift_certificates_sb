package ru.clevertec.ecl.model.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TagMapperTest {

    @Autowired
    private TagMapper mapper;

    @Test
    void checkDtoToEntity() {
        TagDto dto = TestData.buildTagDto();
        Tag expectedTag = TestData.buildTag();

        Tag actualTag = mapper.dtoToEntity(dto);

        assertThat(actualTag).isEqualTo(expectedTag);
    }

    @Test
    void checkEntityToDto() {
        TagDto expectedDto = TestData.buildTagDto();
        Tag tag = TestData.buildTag();

        TagDto actualDto = mapper.entityToDto(tag);

        assertThat(actualDto).isEqualTo(expectedDto);
    }
}
