
let userId;
let roomId;
let currentSubscription;
let stompClient;
let topic;

function setPastMessage(message, first) {
    let mainDiv = document.createElement('div');
    mainDiv.className = 'comment post-item';
    mainDiv.dataset.postid = message.id;
    if (first) {
        document.getElementsByClassName("post-items")[0].insertBefore(
            mainDiv, document.getElementsByClassName("comment post-item")[0]);
    } else {
        document.getElementsByClassName("post-items")[0].appendChild(mainDiv);
    }
    let body = document.createElement('div');
    body.className = 'comment__body';
    mainDiv.appendChild(body);
    let head = document.createElement('div');
    head.className = 'comment__head comment__head_lg';
    body.appendChild(head);
    let img = document.createElement('img');
    img.alt = message.author.picture.name;
    img.src = message.author.picture.url;
    img.className = "comment__avatar avatar avatar_circle";
    head.appendChild(img);
    let postHead = document.createElement('dic');
    postHead.className = 'post-item__head-wrap';
    head.appendChild(postHead);
    let postItem = document.createElement('div');
    postItem.className = "post-item__user";
    postHead.appendChild(postItem);
    let link = document.createElement('a');
    link.setAttribute("href", "http://localhost:9080/user?id=" + message.author.id);
    link.className = "comment__user";
    postItem.appendChild(link);
    let nameSpan = document.createElement('span');
    nameSpan.className = 'comment__username text-truncate';
    nameSpan.innerText = message.author.name;
    link.appendChild(nameSpan);
    let timeItem = document.createElement('div');
    timeItem.className = 'comment__date-time';
    postHead.appendChild(timeItem);
    let time = document.createElement('time');
    time.setAttribute('datetime', message.date);
    time.innerText = moment(message.date).startOf('minute').fromNow();
    timeItem.appendChild(time);

    let content = document.createElement('div');
    content.className = "comment__content";
    content.innerHTML = message.message;
    body.appendChild(content);
    let commentControl = document.createElement("div");
    commentControl.className = 'comment__controls';
    body.appendChild(content);
}

let getPastMessage = function () {
    let room = document.getElementById("roomTitle").value;
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/message/getByRoom?room=' + room;
    xhr.open('GET', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            Array.prototype.forEach.call(JSON.parse((xhr.responseText)),
                message => setPastMessage(message, false));
        }

    }
};


window.onload = function () {
    getUser();
    roomId = document.getElementById("roomId").value;
    document.getElementById('NewMessageInput').addEventListener("keyup", function (event) {
        if (event.keyCode === 13) {
            sendMessage();
        }
    });
    getPastMessage();


};

function getUser() {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/user/get';
    xhr.open('GET', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            let user = JSON.parse(xhr.responseText);
            userId = user.id;
            document.getElementById("userId").value = user.id;
            connect();
        }

    }
}

function connect(event) {
    Cookies.set('userId', userId);
    if (userId) {

        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    // event.preventDefault();
}

// Leave the current room and enter a new one.
function enterRoom(newRoomId) {
    roomId = newRoomId;
    Cookies.set('roomId', roomId);
    // roomIdDisplay.textContent = roomId;
    topic = `/app/chat/` + newRoomId;

    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }
    currentSubscription = stompClient.subscribe('/channel/' + roomId, onMessageReceived);

    let message = {
        id: "",
        author: {
            id: userId,
            googleId: "",
            name: "",
            active: true,
            email: "",
            activationCode: "",
            locale: "",
            picture: {},
            books: [{}],
            roles: [],
            lastVisit: "",
            expired: false,
            locked: false,
        },
        message: "новый пользователь происоединился",
        date: "",//Date.now(),
        room: {
            id: document.getElementById("roomId").value,
            author: {},
            title: document.getElementById("roomTitle").value,
            message: "",
            printTime: Date.now(),
            messages: [{}]
        }
    };

    stompClient.send(topic + `/addUser`,
        {},
        JSON.stringify(message)
    );
}

function onConnected() {
    enterRoom(roomId);
    console.log("connecting ...");
    // connectingElement.classList.add('hidden');
}

function onError(error) {
    console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
    console.log(error);
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
}

function sendMessage(event) {
    let message = {
        id: "",
        author: {
            id: userId,
            googleId: "",
            name: "",
            active: true,
            email: "",
            activationCode: "",
            locale: "",
            picture: {},
            books: [{}],
            roles: [],
            lastVisit: "",
            expired: false,
            locked: false,
        },
        message: document.getElementById("NewMessageInput").value,
        date: "",//Date.now(),
        room: {
            id: document.getElementById("roomId").value,
            author: {},
            title: document.getElementById("roomTitle").value,
            message: "",
            printTime: "",//Date.now(),
            messages: [{}]
        }
    };

    if (message && stompClient) {
        stompClient.send(topic + `/sendMessage`, {}, JSON.stringify(message));
    }
    document.getElementById("NewMessageInput").value = '';
    // messageInput.value = '';
    // event.preventDefault();
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);


    setPastMessage(message, true);


}