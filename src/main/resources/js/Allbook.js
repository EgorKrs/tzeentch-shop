function addBook(book) {
    console.log(book);
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

function searchCriteria(key, operation, value) {
    return {
        key: key,
        operation: operation,
        value: value
    };
}

function getBooks() {
    let xhr = new XMLHttpRequest();
    xhr.open(method, "http://localhost:9080/search/book", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let searchCriteria = [];

    xhr.send(JSON.stringify(searchCriteria));

    xhr.onreadystatechange = function () {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            Array.prototype.forEach.call(JSON.parse((xhr.responseText)), book => addBook(book));
        }
    };
}

function init(){
    console.log(document.getElementById("firstData").value);
    Array.prototype.forEach.call(JSON.parse(document.getElementById("firstData").value), book => addBook(book));
    document.getElementById("firstData").value = "";

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
let controls =[];
let  changeState = function(control){
alert('finaly');
    switch (control.value) {
        case "0":
            control.value =1;
            break;
        case "1":
            control.value =2;
            break;
        case "2":
            control.value =0;
            break;
    }
};
 function addEvt(control) {
      control.change =  function(){ alert('finaly');};
      control.input = function(){ alert('finaly');};
     control.addEventListener("change", function(){
         alert('finaly');
     });
     control.addEventListener("input", function(){
         alert('finaly');
     });
     // control.addEventListener("click",
     //     changeState(control)
     // );
     // control.addEventListener("click", function(){
     //     changeState(control)
     // });
     // control.addEventListener("onclick", function(){
     //     changeState(control)
     // });
 }

 window.onload= function() {
     Array.prototype.forEach.call(document.getElementsByClassName("control__input search-filter__with-exclude"), control => controls.push(control));

     for (let i = 0; i < controls.length; i++) {
         controls[i].addEventListener("click", function () {
             changeState(controls[i]);
         });
         addEvt(controls[i]);
         // controls[i].onclick =  function(){ addEvt(controls[i])};
         // controls[i].click = function(){ addEvt(controls[i])};
     }

 };

