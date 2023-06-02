const url = 'http://localhost:8080'; // URL-адрес сервера, с которым будет установлено соединение

let stompClient; // Объект StompClient для обмена сообщениями по протоколу STOMP
let selectedUser; // Имя выбранного пользователя для чата
let principle; // Переменная для хранения имени текущего пользователя

registration(); // Вызов функции регистрации

let socket = new SockJS(url + '/chat'); // Создание нового WebSocket-соединения


// Функция для подключения к чату
function connectToChat(principle) {
    console.log("connecting to chat...") // Вывод сообщения о попытке подключения к чату
    stompClient = Stomp.over(socket); // Создание объекта StompClient для управления соединением
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame); // Вывод сообщения об успешном подключении к чату
        stompClient.subscribe("/topic/messages/" + principle.id, function (response) {
            let data = JSON.parse(response.body); // Разбор полученных данных в формате JSON
            console.log("Data", data);
            if (!selectedUser) {
                $('#userNameAppender_' + data.user.id).append('<span id="newMessage_' + data.user.id + '" style="color: red">+1</span>'); // Отображение индикатора нового сообщения для пользователя в списке пользователей
            } else {
                if (selectedUser.username === data.user.username) {
                    liveRender(data.text, data.user.username, data.timestamp); // Вызов функции для отображения полученного сообщения в чате
                } else {
                    $('#userNameAppender_' + data.user.id).append('<span id="newMessage_' + data.user.id + '" style="color: red">+1</span>'); // Отображение индикатора нового сообщения для пользователя в списке пользователей
                }
            }
        });
        stompClient.subscribe("/topic/newdialog/" + principle.id, function (r) {
            console.log(r)
            $.get(url + "/fetchuser?id="+r.body, function (response) {
                users.push(response);
                appendUsers(response.id, response.username);
            });
        })
    });
}

// Функция для отправки сообщения
function sendMsg(from, text, timestamp) {
    stompClient.send("/app/chat/" + selectedUser.id, {}, JSON.stringify({
        senderId: from.id,
        recipientId: selectedUser.id,
        text: text,
        timestamp: timestamp
    }));
}

// Функция для регистрации пользователя
function registration() {
    $.get(url + "/getprincipal", function (response) {
        principle = response; // Получение имени текущего пользователя

        console.log('Principal -> ', principle)

        $('#userName').text(principle.username); // Отображение имени текущего пользователя

        connectToChat(principle); // Подключение к чату с использованием имени текущего пользователя

        fetchKnown(); // Получение списка пользователей
    });
}

// Функция для выбора пользователя для чата
function selectUser(userId) {
    console.log("selecting users: " + userId); // Вывод выбранного пользователя в консоль
    console.log()

    selectedUser = users.find(u => u.id === Number(userId)); // Установка выбранного пользователя
    console.log('Selected user', selectedUser)

    let isNew = document.getElementById("newMessage_" + selectedUser.id) !== null; // Проверка, есть ли у выбранного пользователя новые сообщения
    if (isNew) {
        let element = document.getElementById("newMessage_" + selectedUser.id);
        element.parentNode.removeChild(element); // Удаление индикатора нового сообщения для выбранного пользователя
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + selectedUser.username); // Отображение выбранного пользователя для чата
    $('#chat-history').html('').removeClass('unselected'); // Очистка истории чата
    $('.chat-message').show();

    render(principle, selectedUser); // Отображение сообщений между текущим пользователем и выбранным пользователем
}

let users; //массив объектов user (тех с которыми общаеся principal)

// Функция для получения списка всех пользователей
function fetchKnown() {
    $.get(url + "/fetchknownusers", function (response) {
        users = response; // Получение списка пользователей
        console.log('Fetch',users)
        $('#usersList').html(''); // Отображение списка пользователей
        $('#selectedUserId').html('');
        $('#chat-history').html('Chose someone to start chatting :)').addClass('unselected');
        $('.chat-message').hide();

        selectedUser = null;

        for (let i = 0; i < users.length; i++) {
            appendUsers(users[i].id, users[i].username)
        }
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
function writeToUser(id) {

    $.get(url + '/writetofound?principalId=' + principle.id + '&recipientId=' + id , function (response) {
        let room = response;
        console.log('Room', room)

    })
    console.log("write to user " + id)
    console.log("write to user1 " + list.find(u => u.id === Number(id)).id)
    let user = list.find(u => u.id === Number(id));
    appendUsers(user.id, user.username)
    users.push(list.find(u => u.id === Number(id)));
    console.log('Pushed users', users)
}

function appendUsers(id, username) {
    let usersTemplateHTML = '<a href="#" onclick="selectUser(\'' + id + '\')"><li class="list-group-item list-group-item-action mb-2 clearfix" data-toggle="list">\n' +
        '                <div class="about">\n' +
        '                    <div id="userNameAppender_' + id + '" class="name">' + username + '</div>\n' +
        '                    <div class="status">\n' +
        '                        <i class="fa fa-circle offline"></i>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </li></a>';
    $('#usersList').append(usersTemplateHTML);
}
