package fr.codev.projectk.rooms

import fr.codev.projectk.model.SimpleQuestion
import fr.codev.projectk.robj.EndGameStats
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class RoomNRTasks {

    @Autowired
    private lateinit var simpMessage: SimpMessagingTemplate
    
    fun sendNextQuestion(roomId: String, question: SimpleQuestion) {
        this.simpMessage.convertAndSend("/topic/messages/" + roomId, question)
    }

    fun sendAnswer(roomId: String, answer: Int) {
        this.simpMessage.convertAndSend("/topic/messages/" + roomId, answer)
    }

    fun sendStats(roomId: String, stats: List<EndGameStats>) {
        this.simpMessage.convertAndSend("/topic/messages/" + roomId, stats)
    }
}