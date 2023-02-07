<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
    <div class="container my-3">
            <div class="mb-3">
                <a href="/board/1/updateForm" class="btn btn-warning">수정</a>
                <button onClick="deleteById(${dto.id})" class="btn btn-danger">삭제</button>
            </div>

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
            글 번호 : <span id="id"><i>${dto.id} </i></span> 작성자 : <span><i>${dto.username} </i></span>
            <i id="heart" class="fa-regular fa-heart ms-2 my-cursor" value=""></i>
        </div>

        <div class="text-white">
            <h3>${dto.title}</h3>
        </div>
        <hr />
        <div class="text-white">
            <div>${dto.content}</div>
        </div>
        <hr />

        <div class="card">
            <form>
                <div class="card-body">
                    <textarea id="reply-content" class="form-control" rows="1"></textarea>
                </div>
                <div class="card-footer">
                    <button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
                </div>
            </form>
        </div>
        <br />
        <div class="card">
            <div class="card-header">댓글 리스트</div>
            <ul id="reply-box" class="list-group">
                <li id="reply-1" class="list-group-item d-flex justify-content-between">
                    <div>댓글내용입니다</div>
                    <div class="d-flex">
                        <div class="font-italic">작성자 : cos &nbsp;</div>
                        <button onClick="replyDelete()" class="badge bg-secondary">삭제</button>
                    </div>
                </li>
            </ul>
        </div>
    </div>
<%@ include file="../layout/footer.jsp" %>