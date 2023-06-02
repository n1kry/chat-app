let $chatHistory; // Переменная для хранения элемента истории чата
let $button; // Переменная для хранения кнопки отправки сообщения
let $textarea; // Переменная для хранения текстового поля ввода сообщения
let $chatHistoryList; // Переменная для хранения списка сообщений чата

function init() {
    cacheDOM(); // Вызов функции для кэширования элементов DOM
    bindEvents(); // Вызов функции для привязки событий
}

function bindEvents() {
    $button.on('click', addMessage.bind(this)); // Привязка события клика на кнопке отправки сообщения
    $textarea.on('keyup', addMessageEnter.bind(this)); // Привязка события нажатия клавиши Enter в текстовом поле ввода сообщения
}

function cacheDOM() {
    $chatHistory = $('.chat-history'); // Кэширование элемента истории чата
    $button = $('#sendBtn'); // Кэширование кнопки отправки сообщения
    $textarea = $('#message-to-send'); // Кэширование текстового поля ввода сообщения
    $chatHistoryList = $chatHistory.find('ul'); // Кэширование списка сообщений чата
}

function render(sender, recipient) {
    let templateResponse = Handlebars.compile($("#message-response-template").html()); // Компиляция шаблона для отображения полученных сообщений
    let template = Handlebars.compile($("#message-template").html()); // Компиляция шаблона для отображения отправленных сообщений

    console.log(sender, recipient)

    setTimeout(function () {
        $.get(url + "/getmessages?sender=" + sender.id + "&recipient=" + recipient.id, function (response) {
            let messages = response; // Получение списка сообщений
            console.log(messages);
            console.log(messages[0].timestamp)
            for (let i = 0; i < messages.length; i++) {
                if (messages[i].user.username === principle.username) {
                    $chatHistoryList.append(template({
                        messageOutput: messages[i].text,
                        time: getTime(messages[i].timestamp),
                    })); // Отображение отправленных сообщений в чате
                } else {
                    $chatHistoryList.append(templateResponse({
                        response: messages[i].text,
                        time: getTime(messages[i].timestamp),
                        userName: selectedUser.username
                    })); // Отображение полученных сообщений в чате
                }
            }
            scrollToBottom(); // Прокрутка до конца истории чата
        });
    }.bind(this), 200);
}

function sendMessage(message) {
    let currenTime = new Date();
    sendMsg(principle, message, currenTime); // Отправка сообщения
    scrollToBottom(); // Прокрутка до конца истории чата
    if (message.trim() !== '') {
        let template = Handlebars.compile($("#message-template").html());
        let context = {
            messageOutput: message,
            time: getTime(currenTime),
            toUserName: selectedUser
        };

        $chatHistoryList.append(template(context)); // Отображение отправленного сообщения в чате
        scrollToBottom(); // Прокрутка до конца истории чата
        $textarea.val(''); // Очистка текстового поля ввода сообщения
    }
}

function liveRender(message, userName, timestamp) {
    scrollToBottom(); // Прокрутка до конца истории чата
    // responses
    let templateResponse = Handlebars.compile($("#message-response-template").html());
    let contextResponse = {
        response: message,
        time: getTime(timestamp),
        userName: userName
    };

    setTimeout(function () {
        $chatHistoryList.append(templateResponse(contextResponse)); // Отображение полученного сообщения в чате
        scrollToBottom(); // Прокрутка до конца истории чата
    }.bind(this), 200);
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight); // Прокрутка до конца истории чата
    console.log($chatHistory.scrollTop($chatHistory[0].scrollHeight));
}

function getTime(timestamp) {
    console.log(timestamp)
    return new Date(timestamp).toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3"); // Получение текущего времени
}

function addMessage() {
    sendMessage($textarea.val()); // Добавление сообщения
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage(); // Добавление сообщения при нажатии клавиши Enter
    }
}

init(); // Инициализация приложения при загрузке страницы