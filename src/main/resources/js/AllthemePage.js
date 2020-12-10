let goToRef = function (path) {
    location.href = path;
};
let createTheme = function (theme) {
    let container = document.createElement('div');
    container.className = 'news-feed-item';
    document.getElementsByClassName("container container_md")[0].appendChild(container);
    let info = document.createElement('div');
    info.className = 'news-feed-item__info';
    container.appendChild(info);
    let date = document.createElement('time');
    date.dateTime = theme.printTime;
    date.innerText = moment(theme.printTime).startOf('day').fromNow();
    info.appendChild(date);
    let bullet = document.createElement('span');
    bullet.className = 'news-feed-item__bullet';
    bullet.innerHTML = '&nbsp;•&nbsp;';
    info.appendChild(bullet);
    let author = document.createElement('span');
    author.className = 'news-feed-item__user';
    author.innerText = theme.author.name;
    info.appendChild(author);
    let title = document.createElement('h2');
    title.className = 'news-feed-item__title';
    let link = document.createElement('a');
    let path = 'http://localhost:9080/forum/get?room=' + theme.title.replace(" ", "%20");
    link.setAttribute('href', path);
    link.innerText = theme.title;
    link.className = 'link-default';
    // link.onclick = function(){
    //     location.href = path;
    // };
    container.appendChild(title);
    title.appendChild(link);

};

let getPage = function () {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/forum/getPage';
    xhr.open('GET', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
// 3. Отсылаем запрос
    xhr.send();
    xhr.onreadystatechange = function () { // (3)
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            console.log(JSON.parse((xhr.responseText)));
            Array.prototype.forEach.call(JSON.parse((xhr.responseText)),
                oneTheme => createTheme(oneTheme));

        }
    }
};


window.onload = function () {
    getPage();
};