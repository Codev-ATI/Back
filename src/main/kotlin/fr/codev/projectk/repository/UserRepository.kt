package fr.codev.projectk.repository

import fr.codev.projectk.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User?, String?> {

    fun findByEmail(email: String?): User?
}