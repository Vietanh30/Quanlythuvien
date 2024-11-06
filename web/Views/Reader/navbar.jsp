<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg bg-danger text-white">
    <div class="container">
        <div class="d-flex justify-content-between w-100">
            <a class="navbar-brand text-white" href="${pageContext.request.contextPath}/DocumentList" style="font-size: 28px; font-weight: 600">Phân tích thiết kế PTIT</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="${pageContext.request.contextPath}/Views/Reader/registration_card.jsp" style="font-weight: 500">Đăng ký thẻ bạn đọc</a>
                    </li>
                    <li class="nav-item" id="login-link">
                        <a class="nav-link text-white" href="${pageContext.request.contextPath}/register_login.jsp" style="font-weight: 500">Đăng nhập</a>
                    </li>
                    <li class="nav-item" id="logout-link" style="display: none;">
                        <a class="nav-link text-white" href="#" style="font-weight: 500" onclick="logout()">Đăng xuất</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</nav>

<script>
    // Kiểm tra xem có user trong localStorage không
    document.addEventListener("DOMContentLoaded", function () {
        const user = localStorage.getItem('user');

        const loginLink = document.getElementById('login-link');
        const logoutLink = document.getElementById('logout-link');

        if (user) {
            // Nếu có user, ẩn link đăng nhập và hiển thị link đăng xuất
            loginLink.style.display = 'none';
            logoutLink.style.display = 'block';
        } else {
            // Nếu không có user, hiển thị link đăng nhập và ẩn link đăng xuất
            loginLink.style.display = 'block';
            logoutLink.style.display = 'none';
        }
    });

    function logout() {
        // Xóa thông tin người dùng khỏi localStorage
        localStorage.removeItem('user');
        
        // Điều hướng đến trang đăng nhập hoặc trang chính
        window.location.href = '${pageContext.request.contextPath}/register_login.jsp'; // Hoặc trang khác bạn muốn
    }
</script>