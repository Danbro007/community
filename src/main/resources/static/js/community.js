function addComment() {
    var content = $("#commentContent").val();
    var questionId = $("#questionId").val();
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success:function (res) {
            if(res.code == 200){
                $("#commentBox").hide();
            }else{
                if (res.code == 4003){
                    var isAccpeted = confirm(res.message);
                    if (isAccpeted){
                        window.open("https://github.com/login/oauth/authorize?client_id=06fdb966a8cbcf08c463&redirect_uri=http://localhost:8888/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);
                    }else{

                    }
                } 
                alert(res.message)
            }
        },
        dataType:"json"
    })
}