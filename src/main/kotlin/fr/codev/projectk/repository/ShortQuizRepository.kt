package fr.codev.projectk.repository

import fr.codev.projectk.model.ShortQuiz
import org.springframework.data.mongodb.repository.MongoRepository

interface ShortQuizRepository: MongoRepository<ShortQuiz, String>