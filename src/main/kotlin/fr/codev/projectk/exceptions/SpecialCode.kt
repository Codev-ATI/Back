package fr.codev.projectk.exceptions

enum class SpecialCode(val value: Int) {
    LOGIN_BAD_CREDENTIALS(0), LOGIN_USERNAME_ALREADY_EXISTS(1), FILM_NOT_FOUND(20), ACTOR_NOT_FOUND(30);

}