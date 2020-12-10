function remove(element) {
    element.remove();
}

function setGenre(genre) {
    let a = document.createElement('a');
    a.setAttribute("href", "http://localhost:9080/book/catalog?genre=" + genre);
    a.innerText = genre;
    a.className = 'link-default';
    let spanElement = document.createElement('span');
    spanElement.innerHTML = `<i class="fas fa-trash" ></i>`;
    spanElement.addEventListener('click', function () {
        a.remove();
        spanElement.remove();
    });

    document.getElementById("genreInfo").appendChild(a);
    document.getElementById("genreInfo").appendChild(spanElement);
}

function setBook(book) {
    let a = document.createElement('a');
    a.setAttribute("href", "http://localhost:9080/book?id=" + book.dataset.id);
    a.innerText = book.innerText;
    a.className = 'link-default';
    a.dataset.id = book.dataset.id;
    a.dataset.name = book.innerText;
    let spanElement = document.createElement('span');
    spanElement.innerHTML = `<i class="fas fa-trash" ></i>`;
    spanElement.addEventListener('click', function () {
        a.remove();
        spanElement.remove();
    });
    document.getElementById("bookInfo").appendChild(a);
    document.getElementById("bookInfo").appendChild(spanElement);
}

function setAuthors(author) {
    let a = document.createElement('a');
    console.log(author);
    if (author.dataset !== undefined) {
        a.setAttribute("href", "http://localhost:9080/author?id=" + author.dataset.id);
        a.dataset.id = author.dataset.id;
    } else {
        a.setAttribute("href", "http://localhost:9080/author?id=" + author.id);
        a.dataset.id = author.id;
    }
    if (author.innerText !== undefined) {
        a.innerText = author.innerText;
        a.dataset = author.innerText;
    } else {
        a.innerText = author.name;
        a.dataset = author.name;
    }
    a.className = 'link-default';
    let spanElement = document.createElement('span');
    spanElement.innerHTML = `<i class="fas fa-trash" ></i>`;
    spanElement.addEventListener('click', function () {
        a.remove();
        spanElement.remove();
    });
    document.getElementById("authorsInfo").appendChild(a);
    document.getElementById("authorsInfo").appendChild(spanElement);
}

let isGenreShown = false;

function showGenre() {
    if (isGenreShown) {
        document.getElementById("genreDropdownVal").setAttribute("style", "display : none;");
        isGenreShown = false;
    } else {
        document.getElementById("genreDropdownVal").setAttribute("style", "display : inline-block;");
        isGenreShown = true;
    }
}

let isTranslationStatusShown = false;

function showTranslationStatus() {
    if (isTranslationStatusShown) {
        document.getElementById("translationStatusDropdownVal").setAttribute("style", "display : none;");
        isTranslationStatusShown = false;
    } else {
        document.getElementById("translationStatusDropdownVal").setAttribute("style", "display : inline-block;");
        isTranslationStatusShown = true;
    }
}

let isBookStatusShown = false;

function showBookStatus() {
    if (isBookStatusShown) {
        document.getElementById("bookStatusDropdownVal").setAttribute("style", "display : none;");
        isBookStatusShown = false;
    } else {
        document.getElementById("bookStatusDropdownVal").setAttribute("style", "display : inline-block;");
        isBookStatusShown = true;
    }
}

let isRelatedBookShown = false;

function showRelatedBook() {
    if (isRelatedBookShown) {
        document.getElementById("relatedBookDropdownVal").setAttribute("style", "display : none;");
        isRelatedBookShown = false;
    } else {
        document.getElementById("relatedBookDropdownVal").setAttribute("style", "display : inline-block;");
        isRelatedBookShown = true;
    }
}

let isAuthorsShown = false;

function showAuthors() {

    if (isAuthorsShown) {
        document.getElementById("authorsDropdownVal").setAttribute("style", "display : none;");

        isAuthorsShown = false;
    } else {
        document.getElementById("authorsDropdownVal").setAttribute("style", "display : inline-block;");
        isAuthorsShown = true;
    }
}

let translationStatusHolder = [];

function setAllTranslationStatus(translationStatus) {
    let menu = document.createElement('div');
    menu.innerHTML = `<div class="menu__item" 
                                               title="Статус перевода ` + translationStatus + `">` + translationStatus + `</div>`;
    document.getElementById("translationStatus").appendChild(menu);
    translationStatusHolder.push(menu);

}

let menuGenre = [];

function setAllGenre(genre) {
    let menu = document.createElement('div');
    menu.innerHTML = `<div class="menu__item" 
                                               href="https://localhost:9080/book/catalog?genre=` + genre + `"
                                               title="Каталог ` + genre + `">` + genre + `
                                              
                                                    </div>`;
    document.getElementById("DropdownGenre").appendChild(menu);
    menuGenre.push(menu);

}

let menuAuthors = [];

function setAllAuthors(author) {
    let menu = document.createElement('div');
    menu.innerHTML = `<div class="menu__item" 
                                               href="https://localhost:9080/author?id=` + author.id + `"
                                               title="Автор ` + author.name + `">` + author.name + `                                           
                                                    </div>`;
    menu.dataset.id = author.id;
    document.getElementById("DropdownAuthors").appendChild(menu);
    menuAuthors.push(menu);

}

let menuBookStatus = [];

function setAllBookStatus(bookStatus) {
    let menu = document.createElement('div');
    menu.innerHTML = `<div class="menu__item" 
                                               href="https://localhost:9080/book/catalog?bookStatus=` + bookStatus + `"
                                               title="Статус книги ` + bookStatus + `">` + bookStatus + `</div>`;
    document.getElementById("bookStatus").appendChild(menu);
    menuBookStatus.push(menu);
}

let menuBooks = [];

function setAllBooks(book) {
    let menu = document.createElement('div');
    menu.innerHTML = `<div class="menu__item" 
                                               href="https://localhost:9080/book?id=` + book.id + `"
                                               title="Книга ` + book.name + `">` + book.name + `</div>`;
    menu.dataset.id = book.id;
    document.getElementById("DropdownRelatedBook").appendChild(menu);
    menuBooks.push(menu);

}

function getAuthors() {
    let authors = [];
    let elems = document.getElementById("authorsInfo").getElementsByTagName("a");
    for (let i = 0; i < elems.length; i++) {
        let author = {
            id: elems[i].dataset.id,
            name: elems[i].dataset.name,
            picture: {},
            description: "",
            writtenBooks: ""
        };
        authors.push(author);
    }
// return JSON.stringify(authors);
    return authors;
}

function getGenres() {
    let genres = [];
    let elems = document.getElementById("genreInfo").getElementsByTagName("a");
    for (let i = 0; i < elems.length; i++) {
        genres.push(elems[i].innerText);
    }
    // return JSON.stringify(genres);
    return genres;
}

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

function getRelatedBook() {
    let relatedBook = [];
    let elems = document.getElementById("bookInfo").getElementsByTagName("a");
    for (let i = 0; i < elems.length; i++) {
        let book = {
            id: elems[i].dataset.id,
            name: elems[i].dataset.name,
            picture: {},
            author: [{}],
            bookStatus: "NOT_SET",
            translationStatus: "NOT_SET",
            genres: ["UNKNOWN"],
            usersThatBoughtIt: [{}],
            description: "",
            relatedBooks: [{}],
            availability: "",
            rating: "",
            popularity: "",
            price: "",
            url: "",
            fileName: "",
            printTime: ""
        };
        relatedBook.push(book);
    }
    // return JSON.stringify(relatedBook);
    return relatedBook;
}

let changeImg = function () {
    document.getElementById("imageDraw").src = document.getElementById("pictureUrl").value;
};

function deleteBook() {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/books?id=' + document.getElementById("bookID").value;
    xhr.open('DELETE', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    xhr.onreadystatechange = function () { // (3)
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            console.log(xhr.responseText);

            path = 'http://localhost:9080/';
            location.href = path;
        }
    }
}

function saveBook() {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/books?id=' + document.getElementById("bookID").value;
    xhr.open('PUT', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let id = document.getElementById("bookID").value;
    if (id === 0) {
        id = null
    }
    // 3. Отсылаем запрос
    let book = {
        id: id,
        name: document.getElementById("newBookName").value,
        picture: {
            id: document.getElementById("pictureId").value,
            name: document.getElementById("pictureName").value,
            url: document.getElementById("pictureUrl").value
        },
        author: getAuthors(),
        bookStatus: document.getElementById("bookStatusValue").innerText,
        translationStatus: document.getElementById("translationStatusValue").innerText,
        genres: getGenres(),
        usersThatBoughtIt: [{}],
        description: document.getElementById("bookDescr").value,
        relatedBooks: getRelatedBook(),
        availability: document.getElementById("BookAvailability").value,
        rating: "",
        popularity: "",
        price: document.getElementById("bookPrice").value,
        url: document.getElementById("BookFileUrl").value,
        fileName: document.getElementById("BookFileName").value,
        printTime: ""
    };
    console.log(book);
    xhr.send(JSON.stringify(book));
    xhr.onreadystatechange = function () { // (3)
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            let newBook = JSON.parse(xhr.responseText);

            path = 'http://localhost:9080/book' + "?id=" + newBook.id;
            location.href = path;
        }
    }

}

function initEdit() {
    Array.prototype.forEach.call(JSON.parse((document.getElementById("bookGenre").value)), genre => setGenre(genre));
    Array.prototype.forEach.call(JSON.parse((document.getElementById("Authors").value)), author => setAuthors(author));
    Array.prototype.forEach.call(JSON.parse((document.getElementById("AllGenre").value)), genre => setAllGenre(genre));
    Array.prototype.forEach.call(JSON.parse((document.getElementById("AllBook").value)), book => setAllBooks(book));
    Array.prototype.forEach.call(JSON.parse((document.getElementById("AllAuthors").value)), author => setAllAuthors(author));
    Array.prototype.forEach.call(JSON.parse((document.getElementById("AllTranslationStatus").value)), transactionStatus =>
        setAllTranslationStatus(transactionStatus));
    Array.prototype.forEach.call(JSON.parse((document.getElementById("AllBookStatus").value)), bookStatus => setAllBookStatus(bookStatus));
    menuGenre.forEach(value => value.addEventListener('click', function () {
        setGenre(value.innerText);
    }));
    menuBooks.forEach(value => value.addEventListener('click', function () {
        setBook(value);
    }));
    menuAuthors.forEach(value => value.addEventListener('click', function () {
        setAuthors(value);
    }));
    menuBookStatus.forEach(value => value.addEventListener('click', function () {
        document.getElementById("bookStatusValue").innerText = value.innerText;
        showBookStatus();
    }));
    translationStatusHolder.forEach(value => value.addEventListener('click', function () {
        document.getElementById("translationStatusValue").innerText = value.innerText;
        showTranslationStatus();
    }));

    document.getElementById("deleteBookIcon").addEventListener('click', function () {
        deleteBook();
    });
    document.getElementById("saveBookIcon").addEventListener('click', function () {
        saveBook();
    });
}


