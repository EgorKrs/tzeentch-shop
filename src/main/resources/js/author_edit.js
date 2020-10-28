
let changeImg = function(){
   document.getElementById("imageDraw").src = document.getElementById("pictureUrl").value ;
};
document.getElementById("pictureUrl").addEventListener("onfocusout",changeImg,false);

let written_book = [];
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
    xhr.onreadystatechange = function() {
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
document.getElementById("pictureId").addEventListener("onfocusout",changePictureByID,false);

function saveChanges() {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/author?id='+document.getElementById("authorId").value;
    xhr.open('PUT', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let Author = {
        id: document.getElementById("authorId").value,
        name: document.getElementById("authorName").value,
        picture: {
            id: document.getElementById("pictureId").value,
            name: document.getElementById("pictureName").value,
            url: document.getElementById("pictureUrl").value
        },
        description: document.getElementById("authorDescriptionEdit").value,
        writtenBooks: written_book
    };
    xhr.send(JSON.stringify(Author));
    xhr.onreadystatechange = function() {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            let author = JSON.parse(xhr.responseText);
            path = 'http://localhost:9080/author'+"?id=" + author.id;
            location.href = path;
        }

    }
}

 let deleteThisUser = function() {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/author?id=' + document.getElementById("authorId").value;
    xhr.open('DELETE', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    path = 'http://localhost:9080/';
     location.href = path;
    xhr.send();

    xhr.onreadystatechange = function () {
        console.log(xhr.status + ': ' + xhr.statusText);
        console.log(xhr.responseText);
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
        document.getElementById("authorId").value = "";
        document.getElementById("authorName").value = "";

        document.getElementById("pictureId").value = "";
        document.getElementById("pictureName").value = "";
        document.getElementById("pictureUrl").value = "";

        document.getElementById("authorDescriptionEdit").value = "";
        written_book = [];
            path = 'http://localhost:9080/';
            location.href = path;
        }
    };

};
document.getElementById("deleteAuthorIcon").onclick = deleteThisUser;
// document.getElementById("deleteAuthorIcon").addEventListener("click",deleteThisUser,false);
