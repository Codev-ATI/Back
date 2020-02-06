package fr.codev.projectk.model

class Quiz(var id: String?, var title: String?, var owner: String, var questions: ArrayList<Question>) {

    constructor(title: String?, owner: String, questions: ArrayList<Question>) : this(null, title, owner, questions)
}