function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment_section").hide();
            } else {
                if (response.code == 2003){
                    let isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://gitee.com/oauth/authorize?client_id=3771f18ada3a6fd9f46a66910f74fa01348d085957452c6c74552fcaad26b9e7&redirect_uri=http://localhost:8887/callback&response_type=code&scope=user_info")
                        window.localStorage.setItem("closable", true);
                    }
                }else{
                    alert(response.message)
                }
            }

            console.log(response);
        },
        dataType: "json"
    });
    console.log(questionId);
    console.log(content);


}

