function setRating(rating) {
    document.getElementsByClassName("rating__star")[5 - Math.ceil(rating)].className = " rating__star rating__current";
}

function setRelatedBook(book) {
    let a = document.createElement("a");
    a.setAttribute("href", "http://localhost:9080/book?id=" + book.id);
    a.setAttribute("title", book.name);
    a.className = "manga-list-item list-item";
    a.innerHTML = ` <img class="manga-list-item__cover lazyload shadow-sm loaded"
                         src="` + book.picture.url + `"
                         data-src="` + book.picture.url + `"
                         alt="` + book.picture.name + `" title="` + book.name + `" data-was-processed="true">
                    <div class="manga-list-item__body">
                        <h3 class="manga-list-item__title"> book.name </h3>
                    </div>`;
    document.getElementsByClassName("aside__panel paper")[1].appendChild(a);
}

function setGenre(genre) {
    let a = document.createElement('a');
    a.setAttribute("href", "http://localhost:9080/book/catalog?genre=" + genre);
    a.innerText = genre;
    a.className = 'link-default';
    document.getElementById("genreInfo").appendChild(a);
}

let initBook = function () {
    setRating(document.getElementsByClassName("manga-rating__value")[0].innerHTML);
    Array.prototype.forEach.call(JSON.parse((document.getElementById("relatedBooks").value)), book => setRelatedBook(book));
    Array.prototype.forEach.call(JSON.parse((document.getElementById("bookGenre").value)), genre => setGenre(genre));
};

function setUser(user) {
    let a = document.createElement('a');
    a.innerHTML = `  <a href=\"http://localhost:9080/user?id=` + user.id + `\" class=\"aside-user\" title=\"` + user.name + `\">` +
        `                        <img class=\"lazyload loaded\"\n` +
        `                             src=\"` + user.picture.url + `\"\n` +
        `                             data-src=\" ` + user.picture.url + `\" alt=\"` + user.picture.name + `\"\n` +
        `                             data-was-processed=\"true\">\n` +
        `                    </a>`;
    document.getElementsByClassName("aside__panel paper")[0].appendChild(a);

}

function initPeople() {
    let xhr = new XMLHttpRequest();

    let path = 'http://localhost:9080/edit/user/byeBook?id=' + document.getElementById("bookID").value;
    xhr.open('GET', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
// 3. Отсылаем запрос
    xhr.send();
    xhr.onreadystatechange = function () { // (3)
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            Array.prototype.forEach.call(JSON.parse((xhr.responseText)), user => setUser(user));
            console.log(dataSearchHelp);
            autocomplete(document.getElementById("MainSearchInput"), dataSearchHelp);
        }
    }
}

window.onload = function () {
    initBook();
    initPeople();

};