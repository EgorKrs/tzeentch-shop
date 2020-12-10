let defaultSearchParam = "";
let searchActive = false;
var dataSearchHelp= [];
let changeSearchStatus = function(){
   let body =  document.getElementsByTagName("body")[0];
   if (searchActive ) {
       document.getElementsByClassName("search hidden")[0].className = "search";
       body.setAttribute("style", "overflow: hidden; padding-right: 12px; overscroll-behavior: none;");
   }
   else{
       body.setAttribute("style","");
       document.getElementsByClassName("search ")[0].className = "search hidden";
       dataSearchHelp= [];
   }
};
let clickToSearchItem = function clickToSearchItem(searchItem) {
    dataSearchHelp= [];
    Array.prototype.forEach.call(document.getElementsByClassName("search__type search__type_active"), searchType => searchType.className ="search__type" );
    searchItem.className = "search__type search__type_active";
    getSearchHelpList(searchItem.dataset.type);
    document.getElementsByClassName("search__input form__input autocomplete__input")[0].placeholder = searchItem.innerHTML;

};
function getSearchElem(elementName) {
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/search/'+document.getElementsByClassName("search__type search__type_active")[0].dataset.type;
    xhr.open('POST', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let searchCriteria;
    if (document.getElementsByClassName("search__type search__type_active")[0].dataset.type === 'book'
    ||  document.getElementsByClassName("search__type search__type_active")[0].dataset.type === 'author'
        ||  document.getElementsByClassName("search__type search__type_active")[0].dataset.type === 'user') {
        searchCriteria = [
            {
                key: "name",
                operation: "=",
                value: elementName
            }
        ];
    }
    else if(document.getElementsByClassName("search__type search__type_active")[0].dataset.type === 'news'
        || document.getElementsByClassName("search__type search__type_active")[0].dataset.type === 'forum'){
        searchCriteria = [
            {
                key: "title",
                operation: "=",
                value: elementName
            }
        ];
    }
    xhr.send(JSON.stringify(searchCriteria));
    xhr.onreadystatechange = function() {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            let type = document.getElementsByClassName("search__type search__type_active")[0].dataset.type;
            changeSearchStatus();
            let parse = JSON.parse(xhr.responseText);
            console.log(parse);
            if (type === 'forum') {
                let req = parse[0].title.replace(" ", "%20");
                path = 'http://localhost:9080/forum/get?room=' + req;
            } else {
                path = 'http://localhost:9080/' + document.getElementsByClassName("search__type search__type_active")[0].dataset.type + "?id=" + parse[0].id;
            }
            location.href = path;
        }

    }
}
function getSearchHelpList(category){
    let xhr = new XMLHttpRequest();

    let path = 'http://localhost:9080/search/'+category;
    xhr.open('POST', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let searchCriteria = [
        {
            key: "id",
            operation: "like",
            value: ""
        }
    ];
// 3. Отсылаем запрос
    xhr.send(JSON.stringify(searchCriteria));
    xhr.onreadystatechange = function() { // (3)
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            if (category === 'book' || category === 'author') {
                Array.prototype.forEach.call(JSON.parse((xhr.responseText)), searchType => dataSearchHelp.push(searchType.name));
            } else {
                Array.prototype.forEach.call(JSON.parse((xhr.responseText)), searchType => dataSearchHelp.push(searchType.title));
            }
            console.log(dataSearchHelp);
            autocomplete(document.getElementById("MainSearchInput"), dataSearchHelp);
        }
    }
}
let searchLinkClick = function () {
    searchActive = true;
    changeSearchStatus();
    // document.getElementsByTagName("body")[0].setAttribute("style", "overflow: hidden; padding-right: 12px; overscroll-behavior: none;");
    // document.getElementsByClassName("search hidden")[0].className = "search";
    document.getElementsByClassName("search__input form__input autocomplete__input")[0].placeholder = defaultSearchParam;
    clickToSearchItem(document.getElementsByClassName("search__type")[0]);
    Array.prototype.forEach.call(document.getElementsByClassName("search__type"), searchType =>
        searchType.onclick = function () {
        clickToSearchItem(searchType);
    });

};
document.getElementById('search-link').onclick = searchLinkClick;
document.getElementsByClassName("search__icon search__icon_right search__icon_close")[0].onclick = function(){
    searchActive = false;
    changeSearchStatus();
};

let changeTheme = function () {
let html = document.getElementsByTagName("html")[0];
if (html.dataset.mode === null || html.dataset.mode === undefined || html.dataset.mode === "null"){
    html.dataset.mode = "dark";
    document.querySelector('meta[name="theme-color"]').content = "#242424";
}
else {
    html.dataset.mode = null;
    document.querySelector('meta[name="theme-color"]').content = "#e48f13";
}
};
document.getElementById("changeThemeIcon").onclick = changeTheme ;
let createNewOne = function () {
    location.href = 'http://localhost:9080/' + document.getElementsByClassName("search__type search__type_active")[0].dataset.type + "/create?name=" + document.getElementsByClassName("search__type search__type_active")[0].dataset.type;
};
document.getElementById("addNewOne").onclick = createNewOne;
function autocomplete(inp, arr) {
    /*the autocomplete function takes two arguments,
    the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", function(e) {
        var a, b, i, val = this.value;
        /*close any already open lists of autocompleted values*/
        closeAllLists();
        if (!val) { return false;}
        currentFocus = -1;
        /*create a DIV element that will contain the items (values):*/
        // a = document.createElement("DIV");
        // a.setAttribute("id", this.id + "autocomplete-list");
        // a.setAttribute("class", "search__suggestions autocomplete__suggestions");
        a = document.getElementsByClassName("search__suggestions autocomplete__suggestions")[0];
        /*append the DIV element as a child of the autocomplete container:*/
        // document.getElementsByClassName("search__suggestions autocomplete__suggestions")[0].parentNode.appendChild(a);
        // this.parentNode.appendChild(a);
        /*for each item in the array...*/
        for (i = 0; i < arr.length; i++) {
            /*check if the item starts with the same letters as the text field value:*/
            if (arr[i].substr(0, val.length).toUpperCase() === val.toUpperCase()) {
                /*create a DIV element for each matching element:*/
                b = document.createElement("DIV");
                b.className = "list-item autocomplete__item";
                /*make the matching letters bold:*/
                b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
                /*insert a input field that will hold the current array item's value:*/
                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                /*execute a function when someone clicks on the item value (DIV element):*/
                b.addEventListener("click", function(e) {
                    /*insert the value for the autocomplete text field:*/
                    inp.value = this.getElementsByTagName("input")[0].value;
                    /*close the list of autocompleted values,
                    (or any other open lists of autocompleted values:*/
                    searchActive = false;
                    getSearchElem(inp.value);
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });
    /*execute a function presses a key on the keyboard:*/
    inp.addEventListener("keydown", function(e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode === 40) {
            /*If the arrow DOWN key is pressed,
            increase the currentFocus variable:*/
            currentFocus++;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode === 38) { //up
            /*If the arrow UP key is pressed,
            decrease the currentFocus variable:*/
            currentFocus--;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode === 13) {
            /*If the ENTER key is pressed, prevent the form from being submitted,*/
            e.preventDefault();
            if (currentFocus >= -1) {
                /*and simulate a click on the "active" item:*/
                searchActive = false;
                getSearchElem(inp.value);
                if (x) x[currentFocus].click();

            }
        }
    });
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        // x[currentFocus].classList.add("autocomplete-active");
        x[currentFocus].classList.add("list-item autocomplete__item-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            // x[i].classList.remove("autocomplete-active");
            x[i].classList.remove("list-item autocomplete__item-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("list-item autocomplete__item");
        for (let i = 0; i < x.length; i++) {
            // if(elmnt===undefined){
            //     x[i].parentNode.removeChild(x[i]);
            //     continue;
            // }
            if (elmnt !== x[i] && elmnt !== inp) {
                x[i].parentNode.removeChild(x[i]);
                i = -1;
            }
        }
    }
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}



