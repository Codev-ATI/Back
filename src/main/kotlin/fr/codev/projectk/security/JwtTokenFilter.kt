package fr.codev.projectk.security

import fr.codev.projectk.service.LoginService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(private val loginService: LoginService) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = loginService.resolveToken(request)
        try {
            val authentication = loginService.parseToken(token)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
            response.sendError(404, "Not authenticated")
            return
        }
        filterChain.doFilter(request, response)
    }

}