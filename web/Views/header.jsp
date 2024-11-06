<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thư viện</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <!-- DataTables CSS -->
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.css">
</head>
<style>
    /*
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/CascadeStyleSheet.css to edit this template
    */
    /* 
        Created on : Aug 21, 2024, 12:49:08 AM
        Author     : admin
    */


    @import url(https://fonts.googleapis.com/css?family=Inter:100,200,300,regular,500,600,700,800,900);

    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: "Inter", sans-serif;
    }
    body {
        background-color: #e0f4db86;
    }

    .sidebar {
        position: relative;
        width: 256px;
        min-height: 100vh;
        display: flex;
        flex-direction: column;
        gap: 20px;
        background-color: #fff;
        padding: 24px;
        border-radius: 30px;
        transition: all 0.3s;
    }
    .sidebar {
        display: flex;
        gap: 20px;
        padding-bottom: 20px;
        border-bottom: 1px solid #f6f6f6;
    }

    .head {
        display: flex;
        gap: 20px;
        padding-bottom: 20px;
        border-bottom: 1px solid #f6f6f6;
    }

    .user-img {
        width: 50px;
        overflow: hidden;
    }
    .user-img img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    .user-details .title,
    .menu .title {
        font-size: 10px;
        font-weight: 500;
        color: #757575;
        text-transform: uppercase;
        margin-bottom: 10px;
    }
    .user-details .name {
        font-size: 14px;
        font-weight: 500;
    }
    .nav {
        flex: 1;
    }
    .menu ul {
        padding-left: 0px;
    }
    .menu ul li {
        position: relative;
        list-style: none;
        margin-bottom: 5px;
    }
    .menu ul li a {
        display: flex;
        align-items: center;
        gap: 10px;
        font-size: 16px;
        font-weight: 500;
        color: #757575;
        text-decoration: none;
        padding: 12px 8px;
        border-radius: 8px;
        transition: all 0.3s;
    }
    .menu ul li > a:hover,
    .menu ul li.active > a {
        color: #000;
        background-color: #f6f6f6;
    }
    .menu ul li .icon {
        font-size: 20px;
    }
    .menu ul li .text {
        flex: 1;
    }
    .menu ul li .arrow {
        font-size: 14px;
        transition: all 0.3s;
    }
    .menu ul li.active .arrow {
        transform: rotate(180deg);
    }
    .menu .sub-menu {
        display: none;
        margin-left: 20px;
        padding-left: 20px;
        padding-top: 5px;
        border-left: 1px solid #f6f6f6;
    }
    .menu .sub-menu li a {
        padding: 10px 8px;
        font-size: 14px;
    }
    .menu:not(:last-child) {
        padding-bottom: 10px;
        margin-bottom: 20px;
        border-bottom: 2px solid #f6f6f6;
    }
    .menu-btn {
        position: absolute;
        right: -14px;
        top: 3.5%;
        width: 28px;
        height: 28px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        color: #757575;
        border: 2px solid #f6f6f6;
        background-color: #fff;
    }
    .menu-btn:hover i {
        color: #000;
    }
    .menu-btn i {
        transition: all 0.3s;
    }
    .sidebar.active {
        width: 92px;
    }
    .sidebar.active .menu-btn i {
        transform: rotate(180deg);
    }
    .sidebar.active .user-details {
        display: none;
    }
    .sidebar.active .menu .title {
        text-align: center;
    }
    .sidebar.active .menu ul li .arrow {
        display: none;
    }
    .sidebar.active .menu > ul > li > a {
        position: relative;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .sidebar.active .menu > ul > li > a .text {
        position: absolute;
        left: 70px;
        top: 50%;
        transform: translateY(-50%);
        padding: 10px;
        border-radius: 4px;
        color: #fff;
        background-color: #000;
        opacity: 0;
        visibility: hidden;
        transition: all 0.3s;
    }
    .sidebar.active .menu > ul > li > a .text::after {
        content: "";
        position: absolute;
        left: -5px;
        top: 20%;
        width: 20px;
        height: 20px;
        border-radius: 2px;
        background-color: #000;
        transform: rotate(45deg);
        z-index: -1;
    }
    .sidebar.active .menu > ul > li > a:hover .text {
        left: 50px;
        opacity: 1;
        visibility: visible;
    }
    .sidebar.active .menu .sub-menu {
        position: absolute;
        top: 0;
        left: 20px;
        width: 200px;
        border-radius: 20px;
        padding: 10px 20px;
        border: 1px solid #f6f6f6;
        background-color: #fff;
        box-shadow: 0px 10px 8px rgba(0, 0, 0, 0.1);
    }

</style>