package me.andrusha.vpnpayment.service;

import io.jsonwebtoken.Claims;
import me.andrusha.vpnpayment.jwt_manager.JwtAuthentication;
import me.andrusha.vpnpayment.jwt_manager.JwtProvider;
import me.andrusha.vpnpayment.jwt_manager.JwtRequest;
import me.andrusha.vpnpayment.jwt_manager.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import me.andrusha.vpnpayment.model.user.User;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для авторизации и аутентификации пользователей.
 */
@Service
public class AuthService {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    @Autowired
    JwtProvider jwtProvider;

    /**
     * Метод для выполнения входа пользователя в систему.
     * @param authRequest объект с данными авторизации пользователя
     * @return ответ с JWT токенами и информацией о пользователе
     */
    public ResponseEntity<?> login(JwtRequest authRequest) {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final User user = (User) userService.loadUserByUsername(authRequest.getLogin());
        if (user == null){
            return ResponseEntity.ok(null);
        }
        if ( bCryptPasswordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUsername(), refreshToken);
            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken, user));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    /**
     * Метод для получения нового access токена на основе refresh токена.
     * @param refreshToken refresh токен
     * @return объект с новым access токеном
     */
    public JwtResponse getAccessToken(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }

        return new JwtResponse(null, null);
    }

    /**
     * Метод для обновления access и refresh токенов.
     * @param refreshToken текущий refresh токен
     * @return объект с новым access и refresh токенами
     * @throws AuthException если токен невалидный
     */
    public JwtResponse refresh(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("not valid JWT");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
