const url = 'http://localhost:8080'; // URL-адрес сервера, с которым будет установлено соединение

let stompClient; // Объект StompClient для обмена сообщениями по протоколу STOMP
let selectedUser; // Имя выбранного пользователя для чата
let principle; // Переменная для хранения имени текущего пользователя

registration(); // Вызов функции регистрации

let socket = new SockJS(url + '/chat'); // Создание нового WebSocket-соединения

// Функция для подключения к чату
function connectToChat(userName) {
    console.log("connecting to chat...") // Вывод сообщения о попытке подключения к чату
    stompClient = Stomp.over(socket); // Создание объекта StompClient для управления соединением
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame); // Вывод сообщения об успешном подключении к чату
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body); // Разбор полученных данных в формате JSON
            console.log("Data", data)
            if (selectedUser === data.user.username) {
                liveRender(data.text, data.user.username, data.timestamp); // Вызов функции для отображения полученного сообщения в чате
            } else {
                $('#userNameAppender_' + data.user.username).append('<span id="newMessage_' + data.user.username + '" style="color: red">+1</span>'); // Отображение индикатора нового сообщения для пользователя в списке пользователей
            }
        });
    });
}

// Функция для отправки сообщения
function sendMsg(from, text, timestamp) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        sender: from,
        recipient: selectedUser,
        text: text,
        timestamp: timestamp
    }));
}

// Функция для регистрации пользователя
function registration() {
    $.get(url + "/getprincipal", function (response) {
        principle = response; // Получение имени текущего пользователя
        console.log('Principal -> ', principle)
        $('#userName').text(principle); // Отображение имени текущего пользователя
        console.log(principle); // Вывод имени текущего пользователя в консоль
        connectToChat(principle); // Подключение к чату с использованием имени текущего пользователя
        fetchAll(); // Получение списка пользователей
    });
}

// Функция для выбора пользователя для чата
function selectUser(userName) {
    console.log("selecting users: " + userName); // Вывод выбранного пользователя в консоль
    selectedUser = userName; // Установка выбранного пользователя
    let isNew = document.getElementById("newMessage_" + userName) !== null; // Проверка, есть ли у выбранного пользователя новые сообщения
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element); // Удаление индикатора нового сообщения для выбранного пользователя
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName); // Отображение выбранного пользователя для чата
    $('#chat-history').html(''); // Очистка истории чата

    render(principle, selectedUser); // Отображение сообщений между текущим пользователем и выбранным пользователем
}

// Функция для получения списка всех пользователей
function fetchAll() {
    $.get(url + "/fetchallusers", function (response) {
        let users = response; // Получение списка пользователей
        console.log(users)
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i].username + '\')"><li class="list-group-item list-group-item-action mb-2 clearfix" data-toggle="list">\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i].username + '" class="name">' + users[i].username + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>'; // Генерация HTML-разметки для отображения списка пользователей
        }
        $('#usersList').html(usersTemplateHTML); // Отображение списка пользователей
    }).done(function () {
        $('#usersList').off('click', 'li').on('click', 'li', function (e) {
            const current = document.getElementsByClassName("selected");
            if (current.length > 0) {
                current[0].classList.remove("selected");
            }
            this.classList.add("selected"); // Выделение выбранного пользователя в списке пользователей
        });
    });
}
