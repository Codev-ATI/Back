package fr.codev.projectk.robj

class EndGameStats (var id: Int, var pseudo: String, var score: Int) {

    fun increment() {
        score++;
    }
}