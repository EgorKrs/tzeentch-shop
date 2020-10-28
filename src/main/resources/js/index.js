let popularBooks = [];
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
            }
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
window.onload = function() {
    getPopularBook();
};