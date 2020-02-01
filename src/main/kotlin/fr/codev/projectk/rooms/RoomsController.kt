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
public class RoomsController() {

    @Autowired
    lateinit var roomManager: RoomsManager

//    @MessageMapping("/rooms/join/{roomId}")
//    @SendTo("/topic/messages/{roomId}")
//    public fun joinRoom(@DestinationVariable roomId: String, pseudo: String): List<PlayerStatus>? {
//
//        return roomManager.joinRoom(roomId, pseudo)
//    }

    @MessageMapping("/rooms/ready/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public fun readyUser(@DestinationVariable roomId: String, id: String): List<PlayerStatus>? {
        return roomManager.ready(roomId, Integer.parseInt(id))
    }

    @MessageMapping("/rooms/answer/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public fun answer(@DestinationVariable roomId: String, answer: AnswerDTO) {

        roomManager.answer(roomId, answer.id, answer.questionId, answer.answer)
    }
}