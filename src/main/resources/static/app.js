var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#ready").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#userinfo").html("");
}

function connect() {
    var socket = new SockJS('rooms');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages' , function (greeting) {
            showGreeting(greeting);
        });
        stompClient.send("/app/rooms/join", {}, JSON.stringify({'roomId': '1', 'userId': '1'}));
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/rooms", {}, JSON.stringify({'name': $("#name").val()}));
}

function joinRoom() {
    connect()
}

function leaveRoom() {
    stompClient.send("/app/rooms/leave", {}, JSON.stringify({'roomId': '1', 'userId': '1'}));
    disconnect()
}

function readyRoom() {
    stompClient.send("/app/rooms/ready", {}, JSON.stringify({'roomId': '1', 'userId': '1'}));
}

function showGreeting(message) {
    $("#userinfo").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { joinRoom(); });
    $( "#disconnect" ).click(function() { leaveRoom(); });
    $( "#ready" ).click(function() { readyRoom(); });
});