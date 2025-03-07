function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val().trim();
    commentWithTarget(questionId, 1, content);

}

function comment(button) {
    // 获取textarea中的回复内容
    var content = button.previousElementSibling.value.trim();

    // 获取评论ID（通过按钮的data-id属性）
    var targetId = button.getAttribute('data-id');

    // 调用你已经写好的commentWithTarget函数
    commentWithTarget(targetId, 2, content);
}


function commentWithTarget(targetId, type, content){
    if(!content){
        alert("回复内容不能为空哦！")
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
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

}


//回复评论
// 点击“回复”按钮时的处理函数
function check(button) {
    const commentId = button.getAttribute('data-id');  // 获取评论的 ID
    const replySection = document.getElementById('reply-container-' + commentId);  // 获取二级评论区域

    if (!replySection) return;

    // 切换 active 状态
    button.classList.toggle('active');

    if (replySection.classList.contains('d-none')) {
        // 显示二级评论区
        replySection.classList.remove('d-none');

        // 发送请求获取二级评论
        fetch(`/comment/${commentId}`)
            .then(response => response.json())
            .then(data => {
                if (data && data.data && data.data.length > 0) {
                    const commentListDiv = document.getElementById('comment-list-' + commentId);
                    commentListDiv.innerHTML = '';  // 清空当前的二级评论区域

                    data.data.forEach(reply => {
                        const replyDiv = document.createElement('div');
                        replyDiv.classList.add('reply');
                        replyDiv.innerHTML = `
                            <div class="mb-4 border-bottom pb-3" style="background-color: #fff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
                                <div class="d-flex align-items-start" style="margin-bottom: 10px;">
                                    <img src="${reply.users.avatarUrl}" alt="${reply.users.name}" class="rounded-3" width="45" height="45" style="object-fit: cover; margin-right: 16px; margin-top: 3px;" />
                                    <div class="ms-3 flex-grow-1">
                                        <div class="d-flex justify-content-between">
                                            <strong class="text-primary" style="font-size: 15px; font-weight: 600; color: #333; letter-spacing: 0.5px;">
                                                ${reply.users.name}
                                            </strong>
                                            <small class="text-muted" style="font-size: 12px; color: #777; margin-left: 8px;">
                                                ${new Date(reply.gmtCreate).toLocaleDateString('en-GB')}
                                            </small>
                                        </div>
                                        <p class="text-muted mb-2" style="font-size: 14px; color: #555; line-height: 1.6; margin-top: 5px;">
                                            ${reply.content}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        `;
                        commentListDiv.appendChild(replyDiv);
                    });
                }
            })
            .catch(error => console.error('加载二级评论失败:', error));
    } else {
        // 隐藏二级评论区
        replySection.classList.add('d-none');
    }
}

// 选择标签函数
function selectTag(value) {
    var input = $("#tag");
    var previous = input.val();
    var tags = previous ? previous.split(", ") : [];

    var index = tags.indexOf(value);
    if (index === -1) {
        tags.push(value); // 如果标签不存在则添加
    } else {
        tags.splice(index, 1); // 如果已存在，则删除
    }
    input.val(tags.join(", ")); // 更新输入框
}

// 展开/折叠标签分类
function toggleTags(categoryIndex) {
    $("#tags-" + categoryIndex).toggleClass("d-none"); // 展开/折叠标签
}






// function check(e) {
//     var id = e.getAttribute("data-id"); // 获取评论 ID
//     var replyContainer = document.getElementById("reply-container-" + id); // 找到对应的二级评论区
//
//     if (!replyContainer) {
//         console.error("未找到对应的回复区域：reply-container-" + id);
//         return;
//     }
//
//     replyContainer.classList.toggle("d-none"); // 切换显示状态
//
//     var isVisible = !replyContainer.classList.contains("d-none");
//
//     // 判断是否显示
//     if (isVisible) {
//         e.setAttribute("data-check", "in");  // 设置data-check属性为"in"，表示展开
//         e.classList.add("active");  // 给按钮添加"active"类，改变按钮样式
//     } else {
//         e.removeAttribute("data-check");  // 移除data-check属性
//         e.classList.remove("active");  // 移除"active"类，恢复按钮默认样式
//     }
// }