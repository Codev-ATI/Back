package fr.codev.projectk.rooms

import fr.codev.projectk.model.User

class Room {

    private var users: ArrayList<User> = ArrayList()

    public fun join(user: User) {
        users.add(user)
    }

    public fun leave(userId: String) {
        users.remove(users.find { user -> user.id.equals(userId) })
    }
}