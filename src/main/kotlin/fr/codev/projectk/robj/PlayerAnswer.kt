package fr.codev.projectk.robj

class PlayerAnswer (var pseudo: String, var answerlist: ArrayList<SingleAnswer>) {

    constructor(pseudo: String) : this(pseudo, ArrayList<SingleAnswer>())

    fun addAnswer(questionId: Int, answer: Int) {
        answerlist.add(SingleAnswer(questionId, answer))
    }
}