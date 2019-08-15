
//对问题回复
function addComment2Question() {
    var content = $("#commentContent").val();
    var questionId = $("#questionId").val();
    if (!content){
        alert("回答内容不能为空!")
    }
    comment2target(questionId, 1, content);
}
//对评论回复
function addComment2Comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    if (!content){
        alert("回答内容不能为空!")
    }
    comment2target(commentId, 2, content)
}

//发送评论的ajax方法
function comment2target(targetId, type, content) {
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (res) {
            if (res.code == 2000) {
                window.location.reload();
            } else {
                if (res.code == 4003) {
                    var isAccpeted = confirm(res.message);
                    if (isAccpeted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=06fdb966a8cbcf08c463&redirect_uri=http://localhost:8888/callback&scope=user&state=1")
                        window.localStorage.setItem("closable", true);
                    } else {

                    }
                }
                alert(res.message)
            }
        },
        dataType: "json"
    })
}

/*
*展开二级评论
* */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //展开二级评论状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.list.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded heading",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content,
                        "class":"media-content"
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right icon",
                        "html": moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));

                    var hrElement = $("<hr/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12",
                        "style":"margin-top: 10px"
                    });

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement).append(hrElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment"
                    }).append(mediaElement);
                    commentElement.append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            })
        }
    }
}

/*
* 展示标签选择框
* */
function showSelectTags() {
    $("#question-tag").show();
}
//添加标签到标签框
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1){
        if (previous){
            $("#tag").val(previous  + "," + value)
        } else{
            $("#tag").val(value);
        }
    }
}