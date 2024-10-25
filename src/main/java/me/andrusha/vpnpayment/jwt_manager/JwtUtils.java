package me.andrusha.vpnpayment.jwt_manager;

import io.jsonwebtoken.Claims;

public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

}
