<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <%@ include file="../header.jsp"%>

    <style>
        .card {
            width: 18rem;
            margin: 1rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease; /* Thay đổi kích thước mượt mà */
        }

        .card:hover {
            transform: scale(1.05); /* Phóng to thẻ khi hover */
        }

        .card img {
            height: 200px;
            object-fit: cover;
            border-bottom: 1px solid #ddd;
        }

        .card-body {
            padding: 1rem;
        }

        .card-title, .card-text {
            margin-bottom: 0.5rem;
        }
    </style>

    <body>
        <%@ include file="navbar.jsp"%>
        <h1 class="text-center my-4">Danh Sách Tài Liệu</h1>

        <div class="container">
            <div class="row justify-content-center">
                <c:forEach var="document" items="${documentsList}">
                    <div class="card col-4 p-0">
                        <img src="${pageContext.request.contextPath}/images/${document.getImage()}" class="card-img-top" alt="${document.getName()}">
                        <div class="card-body d-flex justify-content-between" style="flex-direction: column;">
                            <h5 class="card-title">${document.getName()}</h5>
                            <p class="card-text">Tác giả: ${document.getAuthor()}</p>
                            <p class="card-text">Số lượng: ${document.getQuantity()}</p>
                            <p class="card-text">Tồn kho: ${document.getStock()}</p>
                            <!-- Nút mượn sách -->
                            <button class="btn btn-primary" data-toggle="modal" data-target="#borrowModal${document.getId()}">Mượn sách</button>
                        </div>
                    </div>

                    <!-- Modal để nhập số lượng mượn -->
                    <div class="modal fade" id="borrowModal${document.getId()}" tabindex="-1" role="dialog" aria-labelledby="borrowModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="borrowModalLabel">Mượn sách: ${document.getName()}</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form action="borrowBook" method="post">
                                        <div class="form-group">
                                            <label for="quantity">Nhập số lượng muốn mượn (Tối đa: ${document.getStock()}):</label>
                                            <input type="number" class="form-control" id="quantity" name="quantity" max="${document.getStock()}" required>
                                            <input type="hidden" name="documentId" value="${document.getId()}">
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                    <button type="submit" class="btn btn-primary" form="borrowForm${document.getId()}">Mượn</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
    <%@ include file="../footer.jsp"%>
    
    <script>
        // Thêm mã JavaScript nếu cần
    </script>
</html>