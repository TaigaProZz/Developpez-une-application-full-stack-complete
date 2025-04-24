package com.openclassrooms.mddapi.user.mapper;

import com.openclassrooms.mddapi.user.dto.GetUserDto;
import com.openclassrooms.mddapi.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  GetUserDto userToUserDTO(User user);
  User userDTOToUser(GetUserDto userDTO);
}
