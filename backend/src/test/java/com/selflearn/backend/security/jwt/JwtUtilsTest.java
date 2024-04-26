package com.selflearn.backend.security.jwt;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@RequiredArgsConstructor
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    private boolean isJWT(String jwt) {
        String[] jwtSplitted = jwt.split("\\.");
        if (jwtSplitted.length != 3) // The JWT is composed of three parts
            return false;
        try {
            String jsonFirstPart = new String(Base64.getDecoder().decode(jwtSplitted[0]));
            JSONObject firstPart = new JSONObject(jsonFirstPart); // The first part of the JWT is a JSON
            if (!firstPart.has("alg")) // The first part has the attribute "alg"
                return false;
        }catch (JSONException err){
            return false;
        }
        return true;
    }

    @Test
    public void tesGenerateToken() {

        Authentication authentication = new UsernamePasswordAuthenticationToken("truongphan", "123456");

        String jwt = jwtUtils.generateJwtToken(authentication);

        assertTrue(isJWT(jwt));
    }

    @Test
    void testVerifyToken(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("truongphan", "123456");
        String token = jwtUtils.generateJwtToken(authentication);
        boolean result = jwtUtils.validateJwtToken(token);
        assertTrue(result);
    }

    @Test
    void testGetPayloadFromToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("truongphan", "123456");
        String token = jwtUtils.generateJwtToken(authentication);
        UUID id = jwtUtils.getSubjectFromToken(token);
        assertNotNull(id);
    }

}
