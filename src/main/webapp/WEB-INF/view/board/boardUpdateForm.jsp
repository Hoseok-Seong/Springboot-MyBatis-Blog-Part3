<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
    <div class="container my-3">
        <form>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Enter title" name="title" id="title" value=${dto.title}>
            </div>

            <div class="form-group bg-white">
                <textarea class="form-control summernote" rows="5" id="content" name="content">${dto.content}</textarea>
            </div>
        </form>
        <button onClick="updateById(${dto.id})" class="btn btn-primary">글수정완료</button>

    </div>

    <script>
        function updateById(id) {
                
                // 1. 값 받아오기
                let data = {
                title: $("#title").val(),
                content: $("#content").val()
                };

                $.ajax({
                    type:"put",
                    url:"/board/"+id,
                    data:JSON.stringify(data),
                    headers:{
                    "Content-Type":"application/json; charset=utf-8",
                    "X-HTTP-Method-Override" : "PUT"
                    },
                    dataType:"json" // default : 응답의 MIMETYPE으로 유추함.
                }).done((res)=>{ //20x일 때
                    alert(res.msg);
                    location.href="/board/"+id;
                }).fail((err)=>{ // 40x, 50x 일 때
                    alert(err.responseJSON.msg);
                });
            }
    </script>

    <script>
        $('.summernote').summernote({
            tabsize: 2,
            height: 400
        });
    </script>
<%@ include file="../layout/footer.jsp" %>