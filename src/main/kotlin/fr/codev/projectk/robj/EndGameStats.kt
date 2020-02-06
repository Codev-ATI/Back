package fr.codev.projectk.robj

class EndGameStats (var id: String, var pseudo: String, var score: Int) {

    fun increment() {
        score++
    }
}