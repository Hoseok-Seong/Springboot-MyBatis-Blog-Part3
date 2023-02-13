<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
    <div class="container my-3">
            <c:if test="${boardDto.userId == principal.id}">
            <div class="mb-3">
                <a href="/board/${boardDto.id}/updateForm" class="btn btn-warning">수정</a>
                <button onClick="deleteById(${boardDto.id})" class="btn btn-danger">삭제</button>
            </div>
            </c:if>

        <script>
            function deleteById(id) {
                $.ajax({
                    type:"delete",
                    url:"/board/"+id,
                    dataType:"json" //json으로 받을 것이다
                }).done((res)=>{ //20x일 때
                    alert(res.msg);
                    location.href="/";
                }).fail((err)=>{ // 40x, 50x 일 때
                    alert(err.responseJSON.msg);
                });
            }
        </script>

        <div class="mb-2 text-white">
            글 번호 : <span id="id"><i>${boardDto.id} </i></span> 작성자 : <span><i>${boardDto.username} </i></span>
            <i id="heart" class="fa-regular fa-heart ms-2 my-cursor" value=""></i>
        </div>

        <div class="text-white">
            <h3>${boardDto.title}</h3>
        </div>
        <hr />
        <div class="text-white">
            <div>${boardDto.content}</div>
        </div>
        <hr />

        <div class="card">
            <form action="/reply" method="post">
                <input type="hidden" name="boardId" value="${boardDto.id}">
                <div class="card-body">
                    <textarea name="comment" id="reply-comment" class="form-control" rows="1"></textarea>
                </div>
                <div class="card-footer">
                    <button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
                </div>
            </form>
        </div>
        <br />
        <div class="card">
            <div class="card-header">댓글 리스트</div>
            <ul id="reply-box" class="list-group">
                <c:forEach items="${replyDtos}" var="reply">
                <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                    <div>${reply.comment}</div>
                    <div class="d-flex">
                        <div class="font-italic">작성자 : ${reply.username}</div>
                        <c:if test="${principal.id == reply.userId}" >
                            <button onClick="DeleteByReplyId(${reply.id})" class="badge bg-secondary">삭제</button>
                        </c:if>
                    </div>
                </li>
                </c:forEach>
            </ul>
        </div>
    </div>
        <script>
            function deleteByReplyId(id) {
                $.ajax({
                    type:"delete",
                    url:"/reply/"+id,
                    dataType:"json" //json으로 받을 것이다
                }).done((res)=>{ //20x일 때
                    alert(res.msg);
                    // location.reload(); //간단하게 현재 페이지로 리로드. 통신 두 번 요청
                    $("#reply-"+id).remove(); // ajax 통신. 통신 한 번에 끝냄.
                }).fail((err)=>{ // 40x, 50x 일 때
                    alert(err.responseJSON.msg);
                });
            }
        </script>
<%@ include file="../layout/footer.jsp" %>