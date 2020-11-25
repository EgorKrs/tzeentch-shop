let sendAnswer = function(answerInput){
    console.log(answerInput.value);
    sendComment("","",answerInput.dataset.id,answerInput.dataset.authorId ,answerInput.value)
};

let setAnswer = function (answerInput,answerButton){
    if(answerButton.innerHTML === 'Ответить') {
        answerInput.hidden = false;
        answerButton.innerHTML = 'Отправить';
    } else {
        answerInput.hidden = true;
        answerButton.innerHTML = 'Ответить';
        answerInput.onclick = sendAnswer(answerInput);
    }
};

let createComment = function (comment,parentNode) {
    let mainDiv = document.createElement('div');
    parentNode.appendChild(mainDiv);
    let commentBody = document.createElement('div');
    commentBody.className = "comment__body";
    mainDiv.className="comment";
    mainDiv.id = "comment_" + comment.id;
    mainDiv.appendChild(commentBody);

    let comment_head = document.createElement('div');
    comment_head.className = "comment__head";
    commentBody.appendChild(comment_head);
    let userRef = document.createElement('a');
    userRef.className = "comment__user";
    userRef.href = "http://localhost:9080/user?id="+ comment.author.id;
    comment_head.appendChild(userRef);
    let image =  document.createElement('img');
    image.setAttribute("src", comment.author.picture.url);
    image.setAttribute("alt", comment.author.picture.name);
    image.className ="comment__avatar";
    userRef.appendChild(image);
    let userNameSpan = document.createElement('span');
    userNameSpan.className = "comment__username text-truncate";
    userNameSpan.innerHTML = comment.author.name;
    userRef.appendChild(userNameSpan);
    let time = document.createElement("time");
    time.className = "comment__date-time";
    let agoTime= document.createElement("time");
    agoTime.dateTime = comment.data;
    agoTime.innerText = moment(comment.data).startOf('day').fromNow()  ;
    time.appendChild(agoTime);
    comment_head.appendChild(time);

    let commentContent = document.createElement("div");
    commentContent.className = 'comment__content';
    commentBody.appendChild(commentContent);
    let commentText = document.createElement("div");
    commentText.innerText = comment.comment;
    commentContent.appendChild(commentText);

    let commentControl = document.createElement("div");
    commentControl.className = 'comment__controls';
    commentBody.appendChild(commentControl);
    let answerButton = document.createElement("button");
    answerButton.className = 'button_link';
    answerButton.innerHTML = 'Ответить';
    commentControl.appendChild(answerButton);

    let answerInput = document.createElement("input");
    answerInput.className = 'comments__form comments__pseudo-form';
    answerInput.id = 'inputAnswer_' + comment.id;
    answerInput.type = 'text';
    answerInput.hidden = true;
    answerInput.dataset.id = comment.id;
    answerInput.dataset.authorId = comment.author.id;
    answerButton.onclick =  function(){ setAnswer(answerInput,answerButton)};
    // answerInput.onclick =  function(){ setAnswer(answerInput,answerButton)};
     // answerInput.addEventListener('click',function(){ setAnswer(answerInput,answerButton)});
    // answerInput.onclick = setAnswer(answerInput,answerButton);
    commentControl.appendChild(answerInput);

    let commentChildren = document.createElement("div");
    commentChildren.className = 'comment__children';
    mainDiv.appendChild(commentChildren);
    let commentCollapse = document.createElement("div");
    commentCollapse.className = 'comment__collapse';
    mainDiv.appendChild(commentCollapse);
        for(let i = 0;i<comment.answers.length;i++){
            createComment(comment.answers[i],commentChildren);
        }
};

function getComment(order){
    let comments = document.getElementsByClassName("comment");
    for(let i = 0 ; i < comments.length ;i++){
        if (comments[i]!==undefined) {
            comments[i].remove();
            i=-1;
        }
    }
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/review/get/newsReview?id='+
        document.getElementById("Comment_related_to_id").value + '&order='+order;
    xhr.open('GET', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            Array.prototype.forEach.call(JSON.parse((xhr.responseText)),
                    comment => createComment(comment, document.getElementsByClassName("section__body")[0]) );
        }

    }

}
let sendComment = function(bookId,newsId,reviewId,authorId,answerText){
    let xhr = new XMLHttpRequest();
    let path = 'http://localhost:9080/edit/review';
    xhr.open('POST', path, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    let comment = {
        id: reviewId,
        comment: document.getElementById('NewCommentInput').value ,
        mark: 0,
        author: {
            id: authorId,
            googleId: "",
            name: "",
            active: "",
            email: "",
            activationCode: "",
            locale: "",
            picture: {},
            books: [{}],
            roles: [{}],
            lastVisit: "",
            expired: "",
            locked: ""
        },
        surveyedBook: [{
            id: bookId,
            name: "",
            picture: {},
            author: [],
            bookStatus: "NOT_SET",
            translationStatus: "NOT_SET",
            genres: [],
            usersThatBoughtIt: [],
            description: "",
            relatedBooks: [],
            reviews: [],
            availability: "",
            popularity: "",
            price: "",
        }],
        newsReview: [{
            id: newsId,
            title: "",
            message: "",
            reviews: [],
            author: {},
            picture: {},
            printTime: "",
        }],
        answers: [{
            id: "",
            comment: answerText,
            mark: "",
            author: {},
            surveyedBook: [{ }],
            newsReview: [{ }],
            answers: [],
            data:""
        }],
        data: Date.now()
    };
    xhr.send(JSON.stringify(comment));
    xhr.onreadystatechange = function() {
        if (xhr.readyState !== 4) return;
        if (xhr.status !== 200) {
            console.log(xhr.status + ': ' + xhr.statusText);
        } else {
            getComment(document.getElementsByClassName("tabs__item tabs__item_active")[0].dataset.type);
        }

    }
};
window.onload = function () {
    document.getElementById('NewCommentInput').addEventListener("keyup", function(event) {
        if (event.keyCode === 13) {
            sendComment("",document.getElementById("Comment_related_to_id").value,"");
        }
    });
    getComment('desc');
};
let clickToCommentItem = function clickToCommentItem(commentItem) {

    Array.prototype.forEach.call(document.getElementsByClassName("tabs__item tabs__item_active"),
            item => item.className ="tabs__item" );
    let move;
    if (commentItem.dataset.type === 'asc')
    {
        move = 65;
    }
    else if(commentItem.dataset.type === 'desc'){
        move = 0;
    }
    else {move = 165;}
    document.getElementsByClassName('tabs__line')[0].setAttribute("style","display: block; width: 46px; transform: translateX("
        +move+"px);");

    commentItem.className = "tabs__item tabs__item_active";
    getComment(commentItem.dataset.type);
};
Array.prototype.forEach.call(document.getElementsByClassName('tabs__item'),
    item => item.onclick = function(){
    clickToCommentItem(item);
}
);