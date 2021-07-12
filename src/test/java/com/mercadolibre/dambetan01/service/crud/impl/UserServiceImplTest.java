package com.mercadolibre.dambetan01.service.crud.impl;

//import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

//    private static Stream<Arguments> userDataProvider() {
//        Faker faker = new Faker();
//
//        UserDTO userDTO = UserDTO.builder()
//                .name(faker.name().fullName())
//                .login(faker.name().username())
//                .password(faker.internet().password())
//                .role(Roles.SELLER.getDescription())
//                .build();
//
//        return Stream.of(
//                Arguments.of(userDTO)
//        );
//    }

    @ParameterizedTest
    @MethodSource("userDataProvider")
    void createANewUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .role(userDTO.getRole())
                .login(userDTO.getLogin())
                .build();

        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findByLogin(any())).thenReturn(Optional.empty());

        assertEquals(UserDTO.fromEntity(user), this.userService.create(userDTO));
    }

    @ParameterizedTest
    @MethodSource("userDataProvider")
    void createAnUserThatAlreadyExistsTest(UserDTO userDTO) {
        when(this.userRepository.findByLogin(any())).thenReturn(Optional.of(new User()));

        assertThrows(ApiException.class, () -> {
            this.userService.create(userDTO);
        });
    }

}