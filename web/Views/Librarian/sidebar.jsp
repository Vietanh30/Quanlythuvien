<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="sidebar">
    <div class="head">
        <div class="user-img">
            <img src="https://imgs.search.brave.com/veG4OVZZqX4_46Ba2yHa3d4s7xMU2JOC9zTYWtSi7gE/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9jZG4u/aGFpdHJpZXUuY29t/L3dwLWNvbnRlbnQv/dXBsb2Fkcy8yMDIx/LzEwL0xvZ28tSG9j/LVZpZW4tQ29uZy1O/Z2hlLUJ1dS1DaGlu/aC1WaWVuLVRob25n/LVBUSVRTaW1wbGUu/cG5n" alt="alt"/>
        </div>
        <div class="user-details">
            <p class="title">Quản lý</p>
            <p class="name">Thư viện sách</p>
        </div>
    </div>
    <div class="nav">
        <div class="menu">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/ReciveDocumentList">
                        <i class="fas fa-book"></i><span class="text">Quản lý Trả Tài Liệu</span>
                    </a>
                </li>
               
                <li>
                    <a class="auth-btn">
                        <i class="fas fa-sign-out-alt"></i>
                        <button id="authButton" class="btn"></button>
                    </a>
                </li>
            </ul>
        </div>
        <!-- Nút Đăng nhập/Đăng xuất -->

    </div>
</div>

