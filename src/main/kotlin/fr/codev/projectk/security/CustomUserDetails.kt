package fr.codev.projectk.security

import fr.codev.projectk.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetails(@field:Autowired var userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val (_, email, password) = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("User $email not found.")
        return User.withUsername(email)
                .password(password)
                .authorities("SimpleUser")
                .build()
    }

}