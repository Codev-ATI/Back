package fr.codev.projectk.rooms

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.SimpleQuestion
import fr.codev.projectk.robj.EndGameStats
import fr.codev.projectk.robj.PlayerAnswer
import fr.codev.projectk.robj.PlayerStatus

class Room(id : String) {

    private var users: ArrayList<PlayerStatus> = ArrayList()
    private lateinit var  quiz: Quiz
    private var actualQuestion: Int = -1
    private var answers: List<PlayerAnswer> = ArrayList()

    constructor(id: String, quiz: Quiz) : this(id) {
        this.quiz = quiz
        this.quiz.questions.shuffle()
    }

    fun getQuiz(): Quiz {
        return quiz
    }

    fun join(pseudo: String): List<PlayerStatus> {
        users.add(PlayerStatus(pseudo, false))
        answers = ArrayList<PlayerAnswer>()

        return users
    }

    fun isReady(pseudo: String): Boolean {
        return users.find { playerStatus -> playerStatus.pseudo == pseudo }!!.ready
    }

    fun isNotHere(pseudo: String): Boolean {
        return users.find { playerStatus -> playerStatus.pseudo == pseudo } == null
    }

    fun leave(pseudo: String) {
        users.remove(users.find { user -> user.pseudo.equals(pseudo) })
    }

    private fun indexOfId(pseudo: String): Int? {
        for (i: Int in 0 until users.size - 1) {
            if (users[i].pseudo == pseudo)
                return i
        }

        return null
    }

    fun setReady(pseudo: String): List<PlayerStatus> {
        users.find { playerStatus -> playerStatus.pseudo == pseudo }!!.ready = true

        return users
    }

    fun allReady():Boolean {
        var ready: Boolean = true
        users.forEach { user -> ready = ready && user.ready }

        return ready
    }

    fun answer(pseudo: String, questionId: Int, answer: Int) {
        answers.find { playerAnswer -> playerAnswer.pseudo == pseudo }?.addAnswer(questionId, answer)
    }

    fun getNextQuestion(): SimpleQuestion {

        return quiz.questions?.get(++actualQuestion)?.let { question -> SimpleQuestion(question.question, question.answeres) }!!
    }

    fun getAnswer():Int {
        return (quiz.questions!!.get(actualQuestion).correct!!)
    }

    fun existNextQuestion(): Boolean {
        return (quiz.questions.size > actualQuestion + 1)
    }

    fun giveMeStats(): List<EndGameStats> {
        return answers.map { playerAnswer ->
            var scoreCounter = 0
            playerAnswer.answerlist.forEach {
                if (it.answer == quiz.questions!!.get(it.id).correct)
                    scoreCounter++
            }

            EndGameStats(playerAnswer.pseudo, scoreCounter)
        }
    }
}