let popularBooks = [];
let news = [];
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
                    Array.prototype.forEach.call(JSON.parse((xhr.responseText)), coolNews => news.push(coolNews));
                    for (let i = 0; i < news.length; i++) {
                        let element = document.createElement("div");
                        element.className = "h-list-item";
                        document.getElementById('latestNewsInd').appendChild(element);
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

    }
};

let getPopularBook = function(){
    let path = "http://localhost:9080/search/book";
    let method =  "POST";
    let requestBody = [
        {
            key: "popularity",
            operation: "=",
            value: 1
        }
    ];
    let func = "getAndSetPopularBook";
    ajaxReq(path,method,requestBody,func);



};
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

window.onload = function() {
    getPopularBook();
    getNews();
};