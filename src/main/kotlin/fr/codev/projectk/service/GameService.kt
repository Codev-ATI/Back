package fr.codev.projectk.service

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.ShortQuiz
import fr.codev.projectk.repository.QuizRepository
import fr.codev.projectk.repository.ShortQuizRepository
import fr.codev.projectk.rooms.RoomsManager
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
    @Autowired
    lateinit var roomManager: RoomsManager

    fun getGames(page: Pageable): List<ShortQuiz> {

        return shortQuizRepository.findAll(page).get().toList()
    }

    fun createQuiz(quiz: Quiz): Quiz {
       // if (quiz.id == null) quiz.id = 2;
        return quizRepository.save(quiz);
    }

    fun getQuiz(id: String): Quiz {
        return quizRepository.findById(id).get()
    }

    fun clearRooms() {
        roomManager.clearRooms()
    }
}