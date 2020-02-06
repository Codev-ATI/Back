package fr.codev.projectk.service

import fr.codev.projectk.exceptions.SNException
import fr.codev.projectk.exceptions.SpecialCode
import fr.codev.projectk.model.Credentials
import fr.codev.projectk.model.User
import fr.codev.projectk.properties.AppProperties
import fr.codev.projectk.repository.UserRepository
import fr.codev.projectk.security.CustomUserDetails
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.Key
import java.time.Instant
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap

@Service
class LoginService(@field:Autowired private val userRepository: UserRepository,
                   @field:Autowired private val userDetailsService: CustomUserDetails,
                   @field:Autowired private val appProperties: AppProperties,
                   @field:Autowired private val encoder: PasswordEncoder) {

    private val key: String = appProperties.secret!!
    private val algorithm: SignatureAlgorithm
    private val expirationPlus: Long = 10800000
    private val keyObj: Key
    // METHODS FOR CONTROLLER
    fun login(email: String?, password: String?): Credentials {
        val user = userRepository.findByEmail(email)
        if (encoder.matches(password, user?.password)) {
            var claims = HashMap<String, String?>()
            claims["sub"] = user?.pseudo
            claims["email"] = user?.email
            val token = Jwts.builder()
                    .setClaims(claims as Map<String, Any>?)
                    .signWith(keyObj)
                    .setExpiration(Date.from(Instant.now().plusMillis(expirationPlus)))
                    .compact()

            return Credentials(token)
        } else {
            throw SNException("Bad credentials !", HttpStatus.BAD_REQUEST, SpecialCode.LOGIN_BAD_CREDENTIALS)
        }
    }

    // METHODS FOR SECURITY
    fun register(pseudo: String, email: String, password: String?) {
        if (userRepository.findByEmail(email) != null) {
            throw SNException("Username already exists !", HttpStatus.BAD_REQUEST, SpecialCode.LOGIN_USERNAME_ALREADY_EXISTS)
        }
        val passwordVar = encoder.encode(password)
        val user = User(pseudo, email, passwordVar)
        userRepository.save(user)
    }

    @Throws(JwtException::class)
    fun parseToken(token: String?): Authentication {
        val email = Jwts.parser().setSigningKey(keyObj).parseClaimsJws(token).body["email"].toString()
        val userDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    // Method for services
    val currentUser: String
        get() {
            val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
            return userDetails.username
        }

    init {
        // 3 hours = 10 800 000 ms
        keyObj = Keys.hmacShaKeyFor(key.toByteArray())

        algorithm = SignatureAlgorithm.HS256
    }
}