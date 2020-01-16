package fr.codev.projectk.model

class Quiz() {

    var id: Int? = null
    var title: String? = ""
    var owner: String? = ""
    var questions: ArrayList<Question>? = ArrayList()

    constructor(id: String) : this() {

    }
}