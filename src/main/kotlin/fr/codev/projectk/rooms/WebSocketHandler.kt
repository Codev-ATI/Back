package fr.codev.projectk.rooms

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class WebSocketHandler: TextWebSocketHandler() {

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {

    }
}