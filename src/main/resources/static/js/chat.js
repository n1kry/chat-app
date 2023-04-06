const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let newMessages = new Map();
let principle;
registration();
let socket = new SockJS(url + '/chat');

function connectToChat(userName) {
    console.log("connecting to chat...")
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.sender) {
                liveRender(data.text, data.sender);
            } else {
                newMessages.set(data.sender, data.text);
                $('#userNameAppender_' + data.sender).append('<span id="newMessage_' + data.sender + '" style="color: red">+1</span>');
            }
        });
    });
}

function sendMsg(from, text) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        sender: from,
        recipient: selectedUser,
        text: text
    }));
}

function registration() {
    $.get(url + "/getprincipal", function (response) {
        principle = response;
        $('#userName').text('Your name:\n' + principle);
        console.log(principle);
        connectToChat(principle);
        fetchAll();
    });
}

function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
    $('#chat-history').html('');
    render(principle, selectedUser);
}

function fetchAll() {
    $.get(url + "/fetchallusers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="list-group-item list-group-item-action mb-2 clearfix" data-toggle="list">\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#usersList').html(usersTemplateHTML);
    });
}