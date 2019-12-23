package fr.codev.projectk.rooms

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller

@Controller
public class RoomsController() {

    @MessageMapping("/welcome")
    @SendTo("/topic/rooms")
    public fun getRoom(): String {
        return "hello";
    }

    @Scheduled()
    @SendTo("/topic/update")
    public fun update(): String {
        return "update";
    }
}