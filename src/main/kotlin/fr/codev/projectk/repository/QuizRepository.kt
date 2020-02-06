package fr.codev.projectk.repository

import fr.codev.projectk.model.Quiz
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

interface QuizRepository: MongoRepository<Quiz, String>