<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
    <div class="container my-3">
        <form class="mb-1">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Enter title" name="title" id="title">
            </div>
            <div class="form-group bg-white">
                <textarea class="form-control summernote" rows="5" id="content" name="content"></textarea>
            </div>
        </form>
        <button onClick="save()" type="button" class="btn btn-primary">글쓰기완료</button>
    </div>
    <script>
        function save() {
                
                // 1. 값 받아오기
                let data = {
                title: $("#title").val(),
                content: $("#content").val()
                };

                $.ajax({
                    type:"post",
                    url:"/board",
                    data:JSON.stringify(data),
                    headers:{
                    "Content-Type":"application/json; charset=utf-8"
                    },
                    dataType:"json" // default : 응답의 MIMETYPE으로 유추함.
                }).done((res)=>{ //20x일 때
                    alert(res.msg);
                    location.href="/";
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