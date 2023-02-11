package ru.vtb.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vtb.dto.UserDto;
import ru.vtb.model.User;


@Component
@RequiredArgsConstructor
public class UserMapper implements IModelMapper<User, UserDto> {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User convertToEntity(UserDto dto) {
        User user = mapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return user;
    }

    @Override
    public UserDto convertToDto(User entity) {
        return mapper.map(entity, UserDto.class);
    }
}
