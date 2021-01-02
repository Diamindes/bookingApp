package bookingApp.controllers

import bookingApp.repositories.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.*


class SecurityService(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(login: String): UserDetails {
        val userAuth = userRepository.getByLogin(login)
        if (userAuth != null) {
            val authorities: MutableList<GrantedAuthority> = ArrayList()
            authorities.add(SimpleGrantedAuthority("ROLE_" + userAuth.roleType.toString().toUpperCase()))
            return User(
                    userAuth.login,
                    userAuth.password,
                    authorities)
        }
        throw UsernameNotFoundException(" User $login not found!")
    }
}