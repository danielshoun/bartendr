package io.bartendr.bartendr.config.security

import io.bartendr.bartendr.models.User
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    @Value("\${jwt.secret}")
    lateinit var jwtSecret: String

    @Value("\${jwt.issuer}")
    lateinit var jwtIssuer: String

    @Value("\${jwt.expiration.time}")
    var jwtExpireTime: Int = 0

    fun generateToken(user: User): String {
        return Jwts.builder()
            .setSubject("${user.id},${user.email}")
            .setIssuer(jwtIssuer)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpireTime))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUserId(token: String): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.split(",")[0]
    }

    fun getEmail(token: String): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.split(",")[1]
    }

    fun getExpirationDate(token: String): Date {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.expiration
    }

    fun validate(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            println("Invalid JWT Signature - ${e.message}")
        } catch (e: MalformedJwtException) {
            println("Invalid JWT - ${e.message}")
        } catch (e: ExpiredJwtException) {
            println("Expired JWT - ${e.message}")
        } catch (e: UnsupportedJwtException) {
            println("Unsupported JWT - ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("Empty JWT Claim - ${e.message}")
        }
        return false
    }
}