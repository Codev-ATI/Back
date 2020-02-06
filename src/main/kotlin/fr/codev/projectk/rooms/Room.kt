package fr.codev.projectk.rooms

import fr.codev.projectk.enum.State
import fr.codev.projectk.model.Question
import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.SimpleQuestion
import fr.codev.projectk.robj.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Room(id : String) {

    private var users: HashMap<String, PlayerStatus> = HashMap()
    private lateinit var  quiz: Quiz
    private var actualQuestion: Int = 0
    private var answers: HashMap<Int, ArrayList<PlayerAnswer>> = HashMap()

    private var state: State = State.WAITING

    constructor(id: String, quiz: Quiz) : this(id) {
        this.quiz = quiz
        this.quiz.questions.shuffle()

        this.quiz.questions.forEach { question: Question -> question.answers?.shuffle() }

        for (i in this.quiz.questions.indices) {
            answers[i] = ArrayList()

        }
    }

    fun inGame(): Boolean {
        return state == State.GAME
    }

    fun getStatus(): List<PlayerStatus> {
        return users.values.toList()
    }

    fun join(pseudo: String): PlayerInfos {

        val generatedUserId = UUID.randomUUID().toString().replace("-", "")
        users[generatedUserId] = PlayerStatus(generatedUserId, pseudo, false)

        val status: PlayerStatus = users[generatedUserId]!!

        return PlayerInfos(generatedUserId, status.pseudo, quiz.title, quiz.questions.size)
    }

    fun quit(id: String) {
        users.remove(id)
    }

    fun setReady(id: String): List<PlayerStatus> {
        users[id]!!.ready = true

        return users.values.toList()
    }

    fun allReady():Boolean {
        var ready = true
        users.values.forEach { user -> ready = ready && user.ready }

        if (ready) {
            state = State.GAME
        }

        return ready
    }

    fun answer(id: String, questionId: Int, answer: Int) {
        answers[questionId]?.add(PlayerAnswer(id, answer))
    }

    fun everyoneAnswered(questionId: Int): Boolean {

        return answers[questionId]?.size == users.size
    }

    fun nextQuestion() {
        actualQuestion++
    }

    fun getNextQuestion(): SimpleQuestion {

        return quiz.questions[actualQuestion].let { question -> SimpleQuestion(actualQuestion, question.question, question.answers) }
    }

    fun getActualQuestionIndex(): Int {
        return actualQuestion
    }

    fun getAnswer():Int {
        return (quiz.questions[actualQuestion].correct!!)
    }

    fun existNextQuestion(): Boolean {
        return (quiz.questions.size > actualQuestion + 1)
    }

    fun giveMeStats(): List<EndGameStats> {

        val stats = ArrayList<EndGameStats>()

        for (user in users.values) {
            stats.add(EndGameStats(user.id, user.pseudo, 0))
        }

        answers.forEach { (key, value) ->
            for (answer in value) {
                if (quiz.questions[key].correct == answer.answer) {
                    stats.find { endGameStats -> endGameStats.id == answer.userId }?.increment()
                }
            }
        }

        return stats.sortedByDescending { endGameStats -> endGameStats.score }
    }
}