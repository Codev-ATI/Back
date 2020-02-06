package fr.codev.projectk.rooms

import fr.codev.projectk.robj.AnswerDTO
import fr.codev.projectk.robj.PlayerStatus
import fr.codev.projectk.robj.SingleAnswer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import javax.print.attribute.IntegerSyntax

@Controller
class RoomsController {

    @Autowired
    lateinit var roomManager: RoomsManager

    @MessageMapping("/rooms/status/{roomId}")
    @SendTo("/topic/players/{roomId}")
    fun statusRoom(@DestinationVariable roomId: String): List<PlayerStatus>? {

        return roomManager.status(roomId)
    }

    @MessageMapping("/rooms/ready/{roomId}")
    @SendTo("/topic/players/{roomId}")
    fun readyUser(@DestinationVariable roomId: String, id: String): List<PlayerStatus>? {
        return roomManager.ready(roomId, id)
    }

    @MessageMapping("/rooms/answer/{roomId}")
    fun answer(@DestinationVariable roomId: String, answer: AnswerDTO) {

        roomManager.answer(roomId, answer.id, answer.questionId, answer.answer)
    }

    @MessageMapping("/rooms/quit/{roomId}")
    fun quit(@DestinationVariable roomId: String, id: String) {

        roomManager.quit(roomId, id)
    }
}