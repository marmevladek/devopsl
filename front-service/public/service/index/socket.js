import buildItemsList from "./itemsListBuilder.js";

var socket = new WebSocket("ws://localhost:1000/ws");

socket.onopen = function() {
    console.log("Соединение установлено.");
};

socket.onclose = function(event) {
    if (event.wasClean) {
        console.log('Соединение закрыто чисто');
    } else {
        console.log('Обрыв соединения'); // например, "убит" процесс сервера
    }
    
    console.log('Код: ' + event.code + ' причина: ' + event.reason);
};
    
socket.onmessage = function(event) {

    console.log("Получены данные " + event.data);
    let item = JSON.parse(event.data);

    buildItemsList(item);
};
    
socket.onerror = function(error) {
    console.log("Ошибка " + error.message);
};


