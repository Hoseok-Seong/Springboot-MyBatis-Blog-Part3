<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
    <div class="container my-3">
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
          메뉴
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
          <li><a class="dropdown-item" href="/admin/user">유저 관리 페이지</a></li>
          <li><a class="dropdown-item" href="/admin/board">게시글 관리 페이지</a></li>
          <li><a class="dropdown-item" href="/admin/reply">댓글 관리 페이지</a></li>
        </ul>
      </div>
      <br/>
      <h2 class="text-center text-white">관리자 댓글 관리 페이지</h2>
      <br/>
            <table class="table table-dark table-striped">
            <thead>
                <tr>
                <th scope="col" class="text-white">번호</th>
                <th scope="col" class="text-white">댓글</th>
                <th scope="col" class="text-white">회원번호</th>
                <th scope="col" class="text-white">게시글번호</th>
                <th scope="col" class="text-white">작성일</th>
                <th scope="col" class="text-white">삭제하기</th>
                </tr>
            </thead>
            <c:forEach items="${replyDetailInfo}" var="reply">
            <tbody id="reply-${reply.id}">
                <tr>
                <th scope="row" class="text-white">${reply.id}</th>
                <td class="text-white">${reply.comment}</td>
                <td class="text-white">${reply.userId}</td>
                <td class="text-white">${reply.boardId}</td>
                <td class="text-white">${reply.createdAt}</td>
                <td class="text-white" >
                <button onClick="deleteByReplyId(${reply.id})" class="badge bg-secondary">삭제</button></td>
                </tr>
            </tbody>
            </c:forEach>
            </table>
            <form action="/admin/replyDetail" method="get">
                <div class="mx-auto" style="width:500px; text-align: center;">
                <input id="keyword" name="keyword" type="text" class="form-control" placeholder="번호, 댓글, 회원번호, 게시글번호로 검색" aria-label="Recipient's username" aria-describedby="button-addon2" autofocus>
                <button class="btn text-white btn-secondary" type="submit" id="button-addon2">검색</button>
                <button class="btn text-white btn-dark" type="button" id="button-addon2" onclick="location.href='/admin/reply';">전체보기</button>
                </div>
                </form>
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