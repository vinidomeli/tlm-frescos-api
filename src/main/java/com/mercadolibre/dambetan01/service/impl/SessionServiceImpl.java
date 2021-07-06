package com.mercadolibre.dambetan01.service.impl;

import com.mercadolibre.dambetan01.dtos.response.AccountResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Account;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.repository.AccountRepository;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.service.ISessionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javassist.NotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements ISessionService {
    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    public SessionServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountResponseDTO login(String login, String password) throws ApiException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ApiException("404", "Usuario e/ou senha invalido(os).", 404));

        boolean validPassword = BCrypt.checkpw(password, user.getPassword());

        if (!validPassword) {
            throw new ApiException("404", "Usuario e/ou senha invalido(os).", 404);
        }

        String token = getJWTToken(login, user.getRole());

        return AccountResponseDTO.builder()
                .username(login)
                .token(token)
                .build();
    }

    /**
     * Genera un token para un usuario específico, válido por 10'
     * @param username
     * @return
     */
    private String getJWTToken(String username, String role) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("USER_"+role.toUpperCase());
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    /**
     * Decodifica un token para poder obtener los componentes que contiene el mismo
     * @param token
     * @return
     */
    private static Claims decodeJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey("mySecretKey".getBytes())
                .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * Permite obtener el username según el token indicado
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }

}
