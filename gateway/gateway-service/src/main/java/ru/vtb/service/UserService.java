package ru.vtb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.vtb.dto.UserDto;
import ru.vtb.exception.UserIsAlreadyExistedException;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.User;
import ru.vtb.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;
    private final IModelMapper<User, UserDto> userMapper;

    @Override
    @Transactional(readOnly = true)
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .cast(UserDetails.class)
                .log();
    }

    @Transactional
    public Mono<String> createUser(UserDto userDto) {
        return userRepository.existsByUsername(userDto.getUsername())
                .flatMap(isPresent -> isPresent
                        ? Mono.error(UserIsAlreadyExistedException::new)
                        : userRepository.save(userMapper.convertToEntity(userDto))
                        .map(User::getUsername)
                )
                .log();
    }
}
