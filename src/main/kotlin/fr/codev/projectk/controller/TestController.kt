package fr.codev.projectk.controller

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
public class TestController() {

    @MessageMapping("/rooms")
    @SendTo("/topic/messages")
    public fun connect(): String {
        return "room";
    }
}