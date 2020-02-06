package fr.codev.projectk.model

data class User(val pseudo: String, var email: String, val password: String) {

    constructor(email: String, password: String) : this("", email, password)

    constructor(pseudo: String) : this(pseudo, "", "")
}