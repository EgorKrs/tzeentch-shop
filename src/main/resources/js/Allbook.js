function addBook(book) {
    let mediaCard = document.createElement("div");
    mediaCard.className = 'media-card-wrap';
    document.getElementsByClassName('media-cards-grid')[0].appendChild(mediaCard);
    let bookCard = document.createElement("a");
    bookCard.className = "media-card";
    bookCard.setAttribute("href", "http://localhost:9080/book?id=" + book.id);
    bookCard.dataset.slug = book.name;
    bookCard.dataset.id = book.id;
    bookCard.dataset.src = book.id;
    bookCard.setAttribute("data-media-slug", book.name);
    bookCard.setAttribute("data-media-id", book.id);
    bookCard.setAttribute("style", "background-image: url('" + book.picture.url + "')");
    bookCard.setAttribute("data-src", "background-image: url('" + book.picture.url + "')");
    bookCard.setAttribute("data-was-processed", "background-image: url('" + true + "')");
    mediaCard.appendChild(bookCard);
    let capt = document.createElement("div");
    capt.className = 'media-card__caption';
    bookCard.appendChild(capt);
    let bookSt = document.createElement("h5");
    bookSt.className = 'media-card__subtitle';
    bookSt.innerText = book.bookStatus;
    capt.appendChild(bookSt);
    let bookName = document.createElement("h5");
    bookName.className = 'media-card__title line-clamp';
    bookName.innerText = book.name;
    capt.appendChild(bookName);


}


let object = [];
let mapGenre = [];
let mapBookStatus = [];

function setObject() {
    let doc = document.getElementsByClassName("control search-filter__checkbox");
    for (let i = 0; i < doc.length; i++) {
        if (doc[i].getElementsByTagName("input")[0].value === "0") {
            continue;
        }
        let mapItem = {
            key: doc[i].dataset.val,
            value: doc[i].getElementsByTagName("input")[0].value
        };
        if (doc[i].dataset.type === "genre") {
            mapGenre.push(mapItem);
        } else {
            mapBookStatus.push(mapItem);
        }
    }
    object.push(mapGenre);
    object.push(mapBookStatus);
}

function clearGenre() {
    let doc = document.getElementsByClassName("control search-filter__checkbox");
    for (let i = 0; i < doc.length; i++) {
        if (doc[i].dataset.type === "genre") {
            doc[i].getElementsByTagName("input")[0].value = "0";
        }
    }
}

function clearBookStatus() {
    let doc = document.getElementsByClassName("control search-filter__checkbox");
    for (let i = 0; i < doc.length; i++) {
        if (doc[i].dataset.type === "bookStatus") {
            doc[i].getElementsByTagName("input")[0].value = "0";
        }
    }
}

function clearAll() {
    let doc = document.getElementsByClassName("control search-filter__checkbox");
    for (let i = 0; i < doc.length; i++) {
        doc[i].getElementsByTagName("input")[0].value = "0";
    }

}

function getBooks() {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:9080/edit/books/catalog", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    setObject();
    console.log(JSON.stringify(object));
    xhr.send(JSON.stringify(object));
    object = [];
    mapGenre = [];
    mapBookStatus = [];

    xhr.onreadystatechange = function () {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            document.getElementsByClassName("media-cards-grid")[0].innerHTML = "";
            Array.prototype.forEach.call(JSON.parse((xhr.responseText)), book => addBook(book));
        }
    };
}

function init() {
    Array.prototype.forEach.call(JSON.parse(document.getElementById("firstData").value), book => addBook(book));
    document.getElementById("firstData").value = "";
    document.getElementById("ClearGenreBut").addEventListener("click", function () {
        clearGenre();
    });
    document.getElementById("genrePanel").addEventListener("click", function (e) {
        if (e.target.id === "ClearGenreBut") {
            clearGenre();
        }
    });
    let elem = document.getElementsByClassName("search-filter-layout");
    for (let i = 0; i < elem.length; i++) {
        elem[i].addEventListener("click", function (e) {
            if (e.target.id === "showBookStatus" || e.target.id === "showGenre" || e.target.id === "showAll") {
                getBooks();
            }
        });
    }


}

document.getElementsByClassName("search-filter-submenu")[0].addEventListener("click", function (e) {
    document.getElementsByClassName("search-filter-layout")[0].className = 'search-filter-layout is-hidden';
    document.getElementsByClassName("search-filter-layout search-filter-layout_hidden")[1].className = 'search-filter-layout search-filter-layout_hidden is-visible';
});
document.getElementsByClassName("search-filter__head-title")[1].addEventListener("click", function (e) {
    document.getElementsByClassName("search-filter-layout is-hidden")[0].className = 'search-filter-layout';
    document.getElementsByClassName("search-filter-layout search-filter-layout_hidden is-visible ")[0].className = 'search-filter-layout search-filter-layout_hidden';
});
document.getElementsByClassName("search-filter-submenu")[1].addEventListener("click", function (e) {
    document.getElementsByClassName("search-filter-layout")[0].className = 'search-filter-layout is-hidden';
    document.getElementsByClassName("search-filter-layout search-filter-layout_hidden")[0].className = 'search-filter-layout search-filter-layout_hidden is-visible';
});
document.getElementsByClassName("search-filter__head-title")[0].addEventListener("click", function (e) {
    document.getElementsByClassName("search-filter-layout is-hidden")[0].className = 'search-filter-layout';
    document.getElementsByClassName("search-filter-layout search-filter-layout_hidden is-visible ")[0].className = 'search-filter-layout search-filter-layout_hidden';
});
let controls = [];

function changeInputVal(div) {
    switch (div.getElementsByTagName("input")[0].value) {
        case "0":
            div.getElementsByTagName("input")[0].value = 1;
            break;
        case "1":
            div.getElementsByTagName("input")[0].value = 2;
            break;
        case "2":
            div.getElementsByTagName("input")[0].value = 0;
            break;
    }
}

function setGenres(genre) {
    let div = document.createElement('div');
    div.innerHTML = `    <input type="checkbox" class="control__input search-filter__with-exclude" value="0">
                                    <span
                                        class="control__indicator control__indicator_checkbox control__indicator_sm">
                                     </span>
                                    <span class="control__text text-truncate">
                                        ` + genre + `
                                    </span>`;
    div.className = "control search-filter__checkbox";
    div.dataset.type = "genre";
    div.dataset.val = genre;
    div.addEventListener("click", function () {
        changeInputVal(div);
    });
    document.getElementById("genreCatalog").appendChild(div);

}

function setBookStatus(bookStatus) {
    let div = document.createElement('div');
    div.innerHTML = `    <input type="checkbox" class="control__input search-filter__with-exclude" value="0">
                                    <span
                                        class="control__indicator control__indicator_checkbox control__indicator_sm">
                                     </span>
                                    <span class="control__text text-truncate">
                                        ` + bookStatus + `
                                    </span>`;
    div.addEventListener("click", function () {
        changeInputVal(div);
    });
    div.className = "control search-filter__checkbox";
    div.dataset.type = "bookStatus";
    div.dataset.val = bookStatus;
    document.getElementById("BookStatusCatalog").appendChild(div);

}

function initFilter() {
    Array.prototype.forEach.call(JSON.parse(document.getElementById("AllGenre").value), genre => setGenres(genre));
    Array.prototype.forEach.call(JSON.parse(document.getElementById("AllBookStatus").value), bookStatus => setBookStatus(bookStatus));
}

