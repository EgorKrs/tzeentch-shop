let changePictureByID = function () {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/search/picture';
    xhr.open('POST', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let searchCriteria = [
        {
            key: "id",
            operation: "=",
            value: document.getElementById("pictureId").value
        }
    ];
    xhr.send(JSON.stringify(searchCriteria));
    xhr.onreadystatechange = function () {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            changeSearchStatus();
            let picture = JSON.parse(xhr.responseText);
            console.log(picture);
            document.getElementById("pictureId").value = picture[0].id;
            document.getElementById("pictureName").value = picture[0].name;
            document.getElementById("pictureUrl").value = picture[0].url;
            changeImg();
        }

    }
};
document.getElementById("pictureId").addEventListener("onfocusout", changePictureByID, false);

function saveNews() {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/news?id=' + document.getElementById("newsID").value;
    xhr.open('PUT', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let id = document.getElementById("newsID").value;
    if (id === 0) {
        id = null
    }
    // 3. Отсылаем запрос
    let news = {
        id: id,
        picture: {
            id: document.getElementById("pictureId").value,
            name: document.getElementById("pictureName").value,
            url: document.getElementById("pictureUrl").value
        },
        author: {
            id: document.getElementById("userId").value,
            name: "",
            picture: {},
            description: "",
            writtenBooks: ""
        },
        title: document.getElementById("newsTitle").value,
        message: document.getElementById("newsMessage").value,
        printTime: Date.now(),
        reviews: [{}],

    };
    console.log(news);
    xhr.send(JSON.stringify(news));
    xhr.onreadystatechange = function () { // (3)
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            let newNews = JSON.parse(xhr.responseText);

            path = 'http://localhost:9080/news?id=' + newNews.id;
            location.href = path;
        }
    }
}

let changeImg = function () {
    document.getElementById("imageDraw").src = document.getElementById("pictureUrl").value;
};
document.getElementById("saveSpan").addEventListener('click', function () {
    saveNews();
});
