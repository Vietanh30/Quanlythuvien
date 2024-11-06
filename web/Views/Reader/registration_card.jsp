<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <%@ include file="../header.jsp"%>
        <style>

            .form-container {
                background-color: white;
                padding: 2rem;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <%@ include file="navbar.jsp"%>

        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="form-container mt-2">
                        <h1 class="text-center my-1">Đăng Ký Thẻ Bạn Đọc</h1>
                        <form action="${pageContext.request.contextPath}/RegistrationCard" method="post">
                            <input type="hidden" id="memberId" name="memberId"> <!-- Trường ẩn cho ID thành viên -->
                            <div class="form-group mt-2">
                                <label for="readerCode">Mã Bạn Đọc:</label>
                                <input type="text" class="form-control" id="readerCode" name="readerCode" required>
                            </div>
                            <div class="form-group mt-2">
                                <label for="className">Lớp:</label>
                                <input type="text" class="form-control" id="className" name="className" required>
                            </div>
                            <div class="form-group mt-2">
                                <label for="name">Tên:</label>
                                <input type="text" class="form-control" id="name" name="name" required readonly>
                            </div>
                            <div class="form-group mt-2">
                                <label for="email">Email:</label>
                                <input type="email" class="form-control" id="email" name="email" required readonly>
                            </div>
                            <div class="form-group mt-2">
                                <label for="birthdate">Ngày Sinh:</label>
                                <input type="date" class="form-control" id="birthdate" name="birthdate" required>
                            </div>
                            <div class="form-group mt-2">
                                <label for="address">Địa Chỉ:</label>
                                <input type="text" class="form-control" id="address" name="address" required>
                            </div>
                            <div class="form-group mt-2">
                                <label for="phone">Số Điện Thoại:</label>
                                <input type="tel" class="form-control" id="phone" name="phone" required pattern="[0-9]{10}">
                            </div>
                            <button type="submit" class="btn btn-primary btn-block mt-2">Đăng Ký</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <%@ include file="../footer.jsp"%>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            // Lấy tên và email từ localStorage và điền vào trường tương ứng
            document.addEventListener("DOMContentLoaded", function () {
                const user = localStorage.getItem('user');
                if (user) {
                    const userObj = JSON.parse(user);
                    const email = userObj.email;
                    const name = userObj.name;
                    const memberId = userObj.id;
                    document.getElementById('memberId').value = memberId;
                    if (email) {
                        document.getElementById('email').value = email;
                    }
                    if (name) {
                        document.getElementById('name').value = name;
                    }
                }

                // Hiển thị thông báo nếu có message
                const message = "${message}";
                if (message) {
                    Swal.fire({
                        title: 'Thông báo',
                        text: message,
                        icon: 'info',
                        confirmButtonText: 'OK'
                    });
                }
            });
        </script>
    </body>
</html>