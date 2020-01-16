package fr.codev.projectk.service

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.ShortQuiz
import fr.codev.projectk.repository.QuizRepository
import fr.codev.projectk.repository.ShortQuizRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class GameService {

    @Autowired
    private lateinit var quizRepository: QuizRepository
    @Autowired
    private lateinit var shortQuizRepository: ShortQuizRepository

    fun getGames(page: Pageable): List<ShortQuiz> {

        var newPage = quizRepository.findAll(page)
        return newPage.get().map { t -> ShortQuiz(t.id, t.title, t.owner) }.toList()
    }
}