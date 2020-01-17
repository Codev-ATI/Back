package fr.codev.projectk.model

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "quiz")
class ShortQuiz(var id: Int?, var title: String?, var owner: String?) {

}