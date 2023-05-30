let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;

function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
}

function render(sender, recipient) {
    let templateResponse = Handlebars.compile($("#message-response-template").html());
    let template = Handlebars.compile($("#message-template").html());

    setTimeout(function () {
        $.get(url + "/getmessages?sender=" + sender + "&recipient=" + recipient, function (response) {
            let messages = response;
            console.log(messages);
            for (let i = 0; i < messages.length; i++) {
                if (messages[i].sender === principle) {
                    $chatHistoryList.append(template({
                        messageOutput: messages[i].text,
                        time: getCurrentTime(),
                        toUserName: messages[i].sender
                    }));
                } else {
                    $chatHistoryList.append(templateResponse({
                        response: messages[i].text,
                        time: getCurrentTime(),
                        userName: messages[i].sender
                    }));
                }
            }
            scrollToBottom();
        });
    }.bind(this), 200);
}

function sendMessage(message) {
    sendMsg(principle, message);
    scrollToBottom();
    if (message.trim() !== '') {
        let template = Handlebars.compile($("#message-template").html());
        let context = {
            messageOutput: message,
            time: getCurrentTime(),
            toUserName: selectedUser
        };

        $chatHistoryList.append(template(context));
        scrollToBottom();
        $textarea.val('');
    }
}

function liveRender(message, userName) {
    scrollToBottom();
    // responses
    let templateResponse = Handlebars.compile($("#message-response-template").html());
    let contextResponse = {
        response: message,
        time: getCurrentTime(),
        userName: userName
    };

    setTimeout(function () {
        $chatHistoryList.append(templateResponse(contextResponse));
        scrollToBottom();
    }.bind(this), 200);
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
    console.log($chatHistory.scrollTop($chatHistory[0].scrollHeight))
}

function getCurrentTime() {
    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
}

init();