<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<!-- DataTables JS -->
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bs-custom-file-input@1.3.4/dist/bs-custom-file-input.min.js"></script>
<script>
    $(".menu > ul > li").click(function (e) {
        // Remove the 'active' class from other menu items
        $(this).siblings().removeClass("active");
        // Toggle the 'active' class on the clicked menu item
        $(this).toggleClass("active");
        // Toggle the visibility of the submenu
        $(this).find("ul").slideToggle();
        // Close other submenus if they are open
        $(this).siblings().find("ul").slideUp();
        // Remove the 'active' class from submenu items
        $(this).siblings().find("ul").find("li").removeClass("active");
    });
    $(".menu-btn").click(function () {
        // Toggle the 'active' class on the sidebar
        $(".sidebar").toggleClass("active");
    });
    $(document).ready(function () {
        const user = localStorage.getItem("user");
        const authButton = $("#authButton");
        // Kiểm tra xem người dùng có đang đăng nhập hay không
        if (user) {
            authButton.text("Đăng xuất");
            $("#authButton").click(function () {
                // Xóa user khỏi localStorage khi đăng xuất
                localStorage.removeItem("user");
                window.location.href = "${pageContext.request.contextPath}/Register"; // Chuyển hướng đến trang đăng nhập
            });
        } else {
            authButton.text("Đăng nhập");
            // Sự kiện khi nhấn nút Đăng nhập
            $("#authButton").click(function () {
                window.location.href = "${pageContext.request.contextPath}/Register"; // Chuyển hướng đến trang đăng nhập
            });
        }
    });



</script>