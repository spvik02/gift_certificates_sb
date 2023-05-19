package ru.clevertec.ecl.model.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserDto userDto);

    UserDto entityToDto(User user);
}
