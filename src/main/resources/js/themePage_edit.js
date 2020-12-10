function save() {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/forum?id=' + document.getElementById("roomId").value;
    xhr.open('PUT', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let id = document.getElementById("roomId").value;
    if (id === 0) {
        id = null
    }
    // 3. Отсылаем запрос
    let room = {
        id: id,
        author: {
            id: document.getElementById("userId").value,
            name: "",
            picture: {},
            description: "",
            writtenBooks: ""
        },
        title: document.getElementById("roomTitle").value,
        message: document.getElementById("roomMessage").value,
        printTime: Date.now(),
        messages: [{}],
    };
    console.log(room);
    xhr.send(JSON.stringify(room));
    xhr.onreadystatechange = function () { // (3)
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            let req = JSON.parse(xhr.responseText).title.replace(" ", "%20");
            path = 'http://localhost:9080/forum/get?room=' + req;
            location.href = path;
        }
    }
}

document.getElementById("saveSpan").addEventListener("click", function () {
    save();
});