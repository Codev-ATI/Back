package fr.codev.projectk.rooms

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.User

class Room(id : String) {

    private var users: ArrayList<User> = ArrayList()
    private var ready: ArrayList<Boolean> = ArrayList()
    private var  quiz: Quiz = Quiz(id);

    fun getQuiz(): Quiz {
        return quiz
    }

    fun join(user: User) {
        users.add(user)
        ready.add(false)
    }

    fun leave(userId: String) {
        users.remove(users.find { user -> user.id.equals(userId) })
    }

    private fun indexOfId(userId: String): Int? {
        for (i: Int in 0 until users.size - 1) {
            if (users[i].id == userId)
                return i
        }

        return null
    }

    fun setReady(userId: String) {
        ready[indexOfId(userId)!!] = true
    }
}