package com.example.Employee.utility;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.Employee.entity.UserEntity;
import com.example.Employee.model.User;
import com.example.Employee.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtility implements Serializable{

	private static final long serialVersionUID = 234234523523L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    

	@Autowired
	private UserRepository userRepository;
    

	private final String SECRET_KEY = "2F413F4428472B4B6250655368566D597133743677397A244326452948404D635166546A576E5A7234753778214125442A472D4A614E645267556B5870327335";

    //retrieve userEmail from jwt token
    public String getUserEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //generate token for user
    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
       // UserEntity userEntity  =  userRepository.findByEmailId (userModelDetails.getEmailId()).orElse(null);
        return doGenerateToken(claims, userDetails.getEmailId());
    }


    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }


    //validate token
    public Boolean validateToken(String token, UserEntity userDetails) {
        final String userEmail = getUserEmailFromToken(token);
        return (userEmail.equals(userDetails.getEmailId()) && !isTokenExpired(token));
    }
    
}
