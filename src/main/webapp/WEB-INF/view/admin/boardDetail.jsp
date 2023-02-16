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
      <h2 class="text-center text-white">관리자 게시글 관리 페이지</h2>
            <table class="table table-dark table-striped">
            <thead>
                <tr>
                <th scope="col" class="text-white">번호</th>
                <th scope="col" class="text-white">작성자명</th>
                <th scope="col" class="text-white">제목</th>
                <th scope="col" class="text-white">내용</th>
                <th scope="col" class="text-white">썸네일</th>
                <th scope="col" class="text-white">좋아요</th>
                <th scope="col" class="text-white">작성일</th>
                <th scope="col" class="text-white">삭제하기</th>
                </tr>
            </thead>
            <c:forEach items="${boardDetailInfo}" var="board">
            <tbody id="board-${board.id}">
                <tr>
                <th scope="row" class="text-white">${board.id}</th>
                <td class="text-white">${board.userId}</td>
                <td class="text-white">${board.title}</td>
                <td class="text-white">${board.content}</td>
                <td class="text-white">${board.thumbnail}</td>
                <td class="text-white">${board.likes}</td>
                <td class="text-white">${board.createdAt}</td>
                <td class="text-white" >
                <button onClick="deleteByBoardId(${board.id})" class="badge bg-secondary">삭제</button></td>
                </tr>
            </tbody>
            </c:forEach>
            </table>
            <form action="/admin/userDetail" method="post">
                <div class="input-group mb-3">
                <input id="username" name="username" type="text" class="form-control" placeholder="아이디로 검색이 가능합니다" aria-label="Recipient's username" aria-describedby="button-addon2">
                <button class="btn btn-primary" type="submit" id="button-addon2">검색</button>
                </div>
                </form>
    </div>
    <script>
            function deleteByBoardId(id) {
                $.ajax({
                    type:"delete",
                    url:"/admin/board/"+id,
                    dataType:"json" //json으로 받을 것이다
                }).done((res)=>{ //20x일 때
                    alert(res.msg);
                    // location.reload(); //간단하게 현재 페이지로 리로드. 통신 두 번 요청
                    $("#board-"+id).remove(); // ajax 통신. 통신 한 번에 끝냄.
                }).fail((err)=>{ // 40x, 50x 일 때
                    alert(err.responseJSON.msg);
                });
            }
        </script>
<%@ include file="../layout/footer.jsp" %>