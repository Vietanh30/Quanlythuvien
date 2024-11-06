
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Stylesheets/register.css"/>
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <link href="Views/Stylesheets/register.css" rel="stylesheet">
        <!-- SweetAlert CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.css">
        <title>Quản lý thư viện</title>
    </head>
    <body>
        <div class="container">
            <div class="forms-container">
                <!--form đăng nhập-->
                <div class="signin-signup">
                    <form id="sign_in_form" role="form" method="post" action="${pageContext.request.contextPath}/Login" class="sign-in-form">
                        <h2 class="title">Đăng nhập</h2>
                        <div class="input-field">
                            <i class="fas fa-user"></i>
                            <input id ="email_sign_in" name="email_sign_in" type="text" placeholder="Email" required />
                        </div>
                        <div class="input-field">
                            <i class="fas fa-lock"></i>
                            <input id ="password_sign_in" name="password_sign_in" type="password" placeholder="Mật khẩu" required />
                        </div>
                        <input type="submit" value="Đăng nhập" class="btn solid" />
                    </form>

                    <!--Form đăng ký-->
                    <form id="sign_up_form" role="form" method="post" action="${pageContext.request.contextPath}/Register" class="sign-up-form">
                        <h2 class="title">Đăng ký</h2>
                        <div class="input-field">
                            <i class="fas fa-user"></i>
                            <input id ="name_sign_up" name="name_sign_up" type="text" placeholder="Tên đăng nhập" required />
                        </div>
                        <div class="input-field">
                            <i class="fas fa-envelope"></i>
                            <input id ="email_sign_up" name="email_sign_up"  type="email" placeholder="Email" required />
                        </div>
                        <div class="input-field">
                            <i class="fas fa-lock"></i>
                            <input id ="password_sign_up" name="password_sign_up" type="password" placeholder="Mật khẩu" required />
                        </div>
                        <input type="submit" value="Đăng ký" class="btn solid" />                        
                    </form>
                </div>
            </div>
            <div class="panels-container">

                <div class="panel left-panel">
                    <div class="content">
                        <h3>Chưa có tài khoản ?</h3>
                        <p>Tạo tài khoản để khám phá các tài liệu và dịch vụ mà chúng tôi cung cấp.</p>
                        <button class="btn transparent" id="sign-up-btn">Đăng ký</button>
                    </div>
                </div>

                <div class="panel right-panel">
                    <div class="content">
                        <h3>Đã có tài khoản</h3>
                        <p>Cập nhật thông tin của bạn và tận hưởng những tiện ích mà chúng tôi mang lại.</p>
                        <button class="btn transparent" id="sign-in-btn">Đăng nhập</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>
    <script>
        const sign_in_btn = document.querySelector("#sign-in-btn");
        const sign_up_btn = document.querySelector("#sign-up-btn");
        const container = document.querySelector(".container");

        sign_up_btn.addEventListener("click", () => {
            container.classList.add("sign-up-mode");
        });

        sign_in_btn.addEventListener("click", () => {
            container.classList.remove("sign-up-mode");
        });
        $(document).ready(function () {
            $("#sign_up_form").on("submit", function (event) {
                event.preventDefault(); // Ngăn chặn mặc định gửi biểu mẫu

                // Lấy dữ liệu từ biểu mẫu
                const name = $("#name_sign_up").val();
                const email = $("#email_sign_up").val();
                const password = $("#password_sign_up").val();

                // Gửi biểu mẫu bằng AJAX
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/Register", // Đường dẫn đến servlet
                    data: {
                        name_sign_up: name,
                        email_sign_up: email,
                        password_sign_up: password
                    },
                    success: function (response) {
                        
                        console.log(response)
                        // Hiển thị thông báo thành công
                        Swal.fire({
                            icon: 'success',
                            title: 'Đăng ký tài khoản thành công',
                            showConfirmButton: false,
                            timer: 1500
                        }).then(function () {
                            // Xóa dữ liệu của biểu mẫu
                            $("#name_sign_up").val('');
                            $("#email_sign_up").val('');
                            $("#password_sign_up").val('');
                        });
                    },
                    error: function (xhr, status, error) {
                        // Hiển thị thông báo lỗi
                        const message =
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Đã xảy ra lỗi',
                                    text: xhr.responseText,
                                    showConfirmButton: true
                                });
                    }
                });
            });
        });
        $(document).ready(function () {
            $("#sign_in_form").on("submit", function (event) {
                event.preventDefault(); // Ngăn chặn mặc định gửi biểu mẫu

                // Lấy dữ liệu từ biểu mẫu
                const email = $("#email_sign_in").val();
                const password = $("#password_sign_in").val();
                // Gửi biểu mẫu bằng AJAX
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/Login", // Đường dẫn đến servlet
                    data: {
                        email_sign_in: email,
                        password_sign_in: password
                    },
                    success: function (response) {

                        if (response.status === "success") {
                            // Hiển thị thông báo thành công và chuyển hướng
                            Swal.fire({
                                icon: 'success',
                                title: response.message,
                                showConfirmButton: false,
                                timer: 1500
                            }).then(function () {
                                localStorage.setItem("user", JSON.stringify(response.user));
                                var user = JSON.parse(localStorage.getItem("user"));
                                if (user.role == 'librarian') {
                                    window.location.href = "${pageContext.request.contextPath}/";
                                } else {
                                    window.location.href = "${pageContext.request.contextPath}/DocumentList";
                                }
                            });
                        } else if (response.status === "unauthor") {
                            // Hiển thị thông báo thành công và chuyển hướng
                            Swal.fire({
                                icon: 'error',
                                title: response.message,
                                showConfirmButton: false,
                                showConfirmButton: true
                            })
                        } else {
                            // Hiển thị thông báo lỗi
                            Swal.fire({
                                icon: 'error',
                                title: 'Đã xảy ra lỗi',
                                text: response.message,
                                showConfirmButton: true
                            });
                        }
                    },
                    error: function (xhr, status, error) {
                        // Hiển thị thông báo lỗi nếu có lỗi xảy ra trong AJAX
                        Swal.fire({
                            icon: 'error',
                            title: 'Đã xảy ra lỗi',
                            text: xhr.responseText,
                            showConfirmButton: true
                        });
                    }
                });
            });
        });


    </script>
</html>
