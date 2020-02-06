package fr.codev.projectk.repository

import fr.codev.projectk.model.Quiz
import org.springframework.data.mongodb.repository.MongoRepository

interface QuizRepository: MongoRepository<Quiz, String>