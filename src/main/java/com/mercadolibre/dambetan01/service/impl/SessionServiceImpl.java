package com.mercadolibre.dambetan01.service.impl;

import com.mercadolibre.dambetan01.dtos.AuthDTO;
import com.mercadolibre.dambetan01.dtos.response.AccountResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Account;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.model.enums.RoleType;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.service.ISessionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javassist.NotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements ISessionService {
    private final UserRepository userRepository;

    public SessionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @return
     * @throws NotFoundException
     */
    @Override
    public AccountResponseDTO login(AuthDTO credentials) throws ApiException {
        User account = userRepository.findByLogin(credentials.getUsername())
                .orElseThrow(() -> new ApiException("404", "Usuario e/ou senha incorreto(s)!", 404));

        boolean invalidCredentials = !validCredentials(account, credentials.getPassword());

        if (invalidCredentials){
            throw new ApiException("404", "Usuario e/ou senha incorreto(s)!", 404);
        }

        String token = getJWTToken(account.getId(), account.getRole());
        AccountResponseDTO user = new AccountResponseDTO();
        user.setUsername(account.getLogin());
        user.setToken(token);
        return user;
    }

    /**
     * Genera un token para un usuario específico, válido por 10'
     * @return
     */
    private String getJWTToken(UUID userId, RoleType role) {
        String secretKey = "mySecretKey";

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + role.getDescription());
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(userId.toString())
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
    public static String getUserId(String token) {
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }

    protected boolean validCredentials(User user, String givenPassword){
        return BCrypt.checkpw(givenPassword, user.getPassword());
    }

}
