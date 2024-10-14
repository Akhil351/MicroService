package org.Akhil.login.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.Akhil.common.exception.TokenInvalid;
import org.Akhil.common.util.JwtUtils;
import org.Akhil.login.config.userDetails.CustomerDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    public String generateKey(Authentication authentication){
        CustomerDetails userPrincipal= (CustomerDetails) authentication.getPrincipal();
        List<String>roles=userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .claim("id",userPrincipal.getId())
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+60*60*1000))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtUtils.JWT_SECRET_KEY));
    }
    public String getEmail(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody().getSubject();
    }
    public boolean validateToken(String jwtToken) throws Exception {
        try{
                Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        }
        catch (TokenInvalid e){
            throw new TokenInvalid("token is invalid");
        }
    }
}
