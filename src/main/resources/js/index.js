let popularBooks = [];
let news = [];
let forum = [];
let newBooks = [];
let ajaxReq = function (path, method, requestBody, onreadystatechangeFunction) {
    let xhr = new XMLHttpRequest();
    xhr.open(method, path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(requestBody));
    switch (onreadystatechangeFunction) {
        case "getAndSetPopularBook":
            xhr.onreadystatechange = function () {
                if (xhr.readyState !== 4) return;
                if (xhr.status !== 200) {
                    console.log(xhr.status + ': ' + xhr.statusText);
                } else {
                    Array.prototype.forEach.call(JSON.parse((xhr.responseText)), book => popularBooks.push(book));
                    for (let i = 0; i < popularBooks.length; i++) {
                        let element = document.createElement("div");
                        element.className = "hot-media-item media-card-wrap media-card-wrap_sm";
                        let a = document.createElement("a");
                        a.setAttribute("href", "http://localhost:9080/book?id=" + popularBooks[i].id);
                        a.setAttribute("title", popularBooks[i].name);
                        a.className = "hot-media-item__card media-card";
                        a.setAttribute("style", "background-image: url('" + popularBooks[i].picture.url + "')");
                        element.appendChild(a);
                        let divElement = document.createElement("div");
                        divElement.className = "media-card__caption";
                        let divElementTitle = document.createElement("div");
                        divElementTitle.className = "media-card__title";
                        divElementTitle.value = popularBooks[i].name;
                        divElement.appendChild(divElementTitle);
                        a.appendChild(divElement);
                        document.getElementsByClassName("hot-media-wrap")[0].appendChild(element);
                    }
                }
            };
            break;
        case "getAndSetNews":
            xhr.onreadystatechange = function () {
                if (xhr.readyState !== 4) return;
                if (xhr.status !== 200) {
                    console.log(xhr.status + ': ' + xhr.statusText);
                } else {
                    Array.prototype.forEach.call(JSON.parse((xhr.responseText)), coolNews => forum.push(coolNews));
                    for (let i = 0; i < forum.length; i++) {
                        let element = document.createElement("div");
                        element.className = "h-list-item";
                        document.getElementById('latestNewsInd').appendChild(element);
                        let div = document.createElement('div');
                        div.className = 'h-list-item__title text-truncate';
                        element.appendChild(div);
                        let a = document.createElement("a");
                        a.setAttribute("href", "http://localhost:9080/news?id=" + forum[i].id);
                        a.innerHTML = forum[i].title ;
                        a.className = "link-default";
                        div.appendChild(a);
                        let info = document.createElement("div");
                        info.className = "h-list-item__info";
                        element.appendChild(info);



                        let user = document.createElement('span');

                        info.appendChild(user);
                        user.className = 'h-list-item__user';
                        let icon = document.createElement('i');
                        icon.className = "far fa-user";
                        let p =document.createElement('span');
                        p.style.margin = "6px";
                        p.innerText = forum[i].author.name;
                        // user.innerText = news[i].author.name;
                        // document.getElementById('userIcon').cloneNode(icon);
                        user.appendChild(icon);
                        user.appendChild(p);
                        let date = document.createElement('span');
                        date.className = 'h-list-item__date';
                        date.innerHTML =  moment(forum[i].printTime).startOf('day').fromNow()  ;
                        info.appendChild(date);
                    }
                }
            };
            break;
        case "getAndSetForums" :
            xhr.onreadystatechange = function () {
                if (xhr.readyState !== 4) return;
                if (xhr.status !== 200) {
                    console.log(xhr.status + ': ' + xhr.statusText);
                } else {
                    Array.prototype.forEach.call(JSON.parse((xhr.responseText)), coolForum => news.push(coolForum));
                    for (let i = 0; i < news.length; i++) {
                        let element = document.createElement("div");
                        element.className = "h-list-item";
                        document.getElementById('latestForumInd').appendChild(element);
                        let div = document.createElement('div');
                        div.className = 'h-list-item__title text-truncate';
                        element.appendChild(div);
                        let a = document.createElement("a");
                        a.setAttribute("href", "http://localhost:9080/news?id=" + news[i].id);
                        a.innerHTML = news[i].title ;
                        a.className = "link-default";
                        div.appendChild(a);
                        let info = document.createElement("div");
                        info.className = "h-list-item__info";
                        element.appendChild(info);



                        let user = document.createElement('span');

                        info.appendChild(user);
                        user.className = 'h-list-item__user';
                        let icon = document.createElement('i');
                        icon.className = "far fa-user";
                        let p =document.createElement('span');
                        p.style.margin = "6px";
                        p.innerText = news[i].author.name;
                        // user.innerText = news[i].author.name;
                        // document.getElementById('userIcon').cloneNode(icon);
                        user.appendChild(icon);
                        user.appendChild(p);
                        let date = document.createElement('span');
                        date.className = 'h-list-item__date';
                        date.innerHTML =  moment(news[i].printTime).startOf('day').fromNow()  ;
                        info.appendChild(date);
                    }
                }
            };
            break;
        case "getAndSetNewBooks":
            xhr.onreadystatechange = function () {
                if (xhr.readyState !== 4) return;
                if (xhr.status !== 200) {
                    console.log(xhr.status + ': ' + xhr.statusText);
                } else {
                    Array.prototype.forEach.call(JSON.parse((xhr.responseText)), newBook => newBooks.push(newBook));
                    for (let i = 0; i < newBooks.length; i++) {
                        let element = document.createElement("div");
                        element.className = "updates__item";
                        document.getElementsByClassName('updates tabs__content tabs__content_show')[0].appendChild(element);
                        let div = document.createElement('div');
                        div.className = 'updates__left';
                        element.appendChild(div);
                        let bookType = document.createElement('div');
                        bookType.className = "updates__type";
                        bookType.innerText = newBooks[i].genres[0];
                        div.appendChild(bookType);
                        let a = document.createElement("a");
                        a.setAttribute("href", "http://localhost:9080/book?id=" + newBooks[i].id);
                        div.appendChild(a);
                        let img = document.createElement("div");
                        img.className = "cover cover_responsive lazyload updates__cover";
                        img.setAttribute("style", "background-image: url("+newBooks[i].picture.url+")");
                        img.dataset.src = newBooks[i].picture.url;
                        a.appendChild(img);


                        let node = document.createElement('div');
                        node.className = 'updates__right';
                        element.appendChild(node);
                        let header = document.createElement('div');
                        header.className = 'updates__header';
                        node.appendChild(header);
                        let headerTop = document.createElement('div');
                        headerTop.className = 'updates__header-top';
                        header.appendChild(headerTop);
                        let headerTopLabel = document.createElement("div");
                        headerTopLabel.className = 'updates__labels';
                        headerTop.appendChild(headerTopLabel);

                        let updName = document.createElement('h4');
                        updName.className = "updates__name";
                        headerTop.appendChild(updName);
                        let bookLink = document.createElement('a');
                        bookLink.className = "link-default";
                        bookLink.setAttribute("href", "http://localhost:9080/book?id=" + newBooks[i].id);
                        bookLink.innerText = newBooks[i].name;
                        updName.appendChild(bookLink);
                        if (newBooks[i].popularity > 50 ){
                            let lab = document.createElement("div");
                            lab.className = "m-label m-label_alert";
                            lab.innerText = "Популярное";
                            headerTopLabel.appendChild(lab);
                        }
                        let releaseDiv = document.createElement("div");
                        releaseDiv.className = "updates__header-bottom";
                        header.appendChild(releaseDiv);
                        let releaseDate = document.createElement('div');
                        releaseDate.className = "updates__date";
                        releaseDate.innerText = moment(newBooks[i].printTime).startOf('day').fromNow()  ;
                        releaseDiv.appendChild(releaseDate);

                        let updatesBody = document.createElement('div');
                        updatesBody.className = 'updates__body';
                        node.appendChild(updatesBody);
                        let updatesChapters =document.createElement('div');
                        updatesChapters.className = 'updates__chapters';
                        updatesBody.appendChild(updatesChapters);
                        let updatesChapterLink = document.createElement('a');
                        updatesChapterLink.className = 'updates__chapter';
                        updatesChapterLink.setAttribute("href", "http://localhost:9080/book?id=" + newBooks[i].id);
                        updatesChapters.appendChild(updatesChapterLink);
                        let descr = document.createElement("strong");
                        descr.className = 'updates__chapter-vol';
                        descr.innerText = newBooks[i].description;
                        updatesChapterLink.appendChild(descr);
                        let transl = document.createElement('span');
                        transl.className = 'updates__chapter-team text-truncate';
                        let trName = ' ';
                        for (let c = 0 ; c < newBooks[i].author.length; c++ ){
                            trName += newBooks[i].author[c].name;
                        }
                        // Array.prototype.forEach.call(newBooks[i].author, author => trName.concat(trName,author));
                        transl.innerText = trName;
                        updatesChapterLink.appendChild(transl);



                    }
                }
            };

            break;


    }
};

let getPopularBook = function(){
    let path = "http://localhost:9080/search/book";
    let method =  "POST";
    let requestBody = [
        {
            key: "popularity",
            operation: ">",
            value: 50
        }
    ];
    let func = "getAndSetPopularBook";
    ajaxReq(path,method,requestBody,func);
};
function getNewBook() {
    let path = "http://localhost:9080/edit/books/getBooks";
    let method =  "GET";
    let requestBody = [
        {

        }
    ];
    let func = "getAndSetNewBooks";
    ajaxReq(path,method,requestBody,func);

}
function getNews() {
    let path = "http://localhost:9080/edit/news/getNews";
    let method =  "GET";
    let requestBody = [
        {

        }
    ];
    let func = "getAndSetNews";
    ajaxReq(path,method,requestBody,func);

}
function getForum() {
    let path = "http://localhost:9080/edit/forum/getForums";
    let method =  "GET";
    let requestBody = [
        {

        }
    ];
    let func = "getAndSetForums";
    ajaxReq(path,method,requestBody,func);

}

window.onload = function() {
    getPopularBook();
    getNews();
    getForum();
    getNewBook();
};