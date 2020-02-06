package fr.codev.projectk.rooms

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import fr.codev.projectk.model.SimpleQuestion
import fr.codev.projectk.robj.EndGameStats
import fr.codev.projectk.robj.PlayerStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.JsonParserFactory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class RoomNRTasks {

    @Autowired
    private lateinit var simpMessage: SimpMessagingTemplate

    fun sendStatus(roomId: String, status: List<PlayerStatus>) {
        this.simpMessage.convertAndSend("/topic/players/" + roomId, status)
    }
    
    fun sendNextQuestion(roomId: String, question: SimpleQuestion) {
        this.simpMessage.convertAndSend("/topic/questions/" + roomId, question)
    }

    fun sendAnswer(roomId: String, answer: Int) {
        this.simpMessage.convertAndSend("/topic/answers/" + roomId, answer)
    }

    fun sendStats(roomId: String, stats: List<EndGameStats>) {
        this.simpMessage.convertAndSend("/topic/stats/" + roomId, stats)
    }
}