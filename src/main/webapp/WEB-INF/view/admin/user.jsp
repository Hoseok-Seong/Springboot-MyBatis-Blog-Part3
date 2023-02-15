<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
    <div class="container my-3">
    <nav class="nav nav-pills flex-column flex-sm-row">
    <a class="flex-sm-fill text-sm-center nav-link active" aria-current="page" href="/admin/user">유저 관리 페이지</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/admin/board">게시글 관리 페이지</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/admin/reply">댓글 관리 페이지</a>
    </nav>
    <br/>
        <div class="container">
            <h2 class="text-center text-white">관리자 유저관리 페이지</h2>
            <div class="card">
            <div class="card-header">유저 리스트</div>
            <ul id="reply-box" class="list-group">
                <c:forEach items="${userInfo}" var="user">
                <li id="user-${user.id}" class="list-group-item d-flex justify-content-between">
                    <div>${user.id}</div>
                    <div>${user.username}</div>
                    <div>${user.password}</div>
                    <div>${user.email}</div>
                    <div>${user.profile}</div>
                    <div>${user.role}</div>
                    <div>${user.createdAt}</div>
                    <div class="d-flex">
                    <c:if test="${user.role != principal.role}" >
                            <button onClick="deleteByUserId(${user.id})" class="badge bg-secondary">삭제</button>
                    </c:if>        
                    </div>
                </li>
                </c:forEach>
            </ul>
            </div>
        </div>
    </div>
    <script>
            function deleteByUserId(id) {
                $.ajax({
                    type:"delete",
                    url:"/admin/user/"+id,
                    dataType:"json" //json으로 받을 것이다
                }).done((res)=>{ //20x일 때
                    alert(res.msg);
                    // location.reload(); //간단하게 현재 페이지로 리로드. 통신 두 번 요청
                    $("#user-"+id).remove(); // ajax 통신. 통신 한 번에 끝냄.
                }).fail((err)=>{ // 40x, 50x 일 때
                    alert(err.responseJSON.msg);
                });
            }
        </script>
<%@ include file="../layout/footer.jsp" %>