<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <%@ include file="../header.jsp"%>

    <body>
        <div style="display: flex; width: 100%; min-height: 100vh;">
            <%@ include file="sidebar.jsp"%>

            <section class="w-100">
                <div class="container mt-4">
                    <h2 class="text-center">Tìm kiếm bạn đọc</h2>
                    <div class="row d-flex justify-content-center">
                        <div class="col-11 text-end">
                            <form action="SearchReader" method="post" class="mb-3">
                                <input type="hidden" name="action" value="search">
                                <input type="text" name="readerCode" id="searchInput" class="p-2" style="border-radius:7px; font-size: 14px" placeholder="Nhập mã bạn đọc để tìm kiếm...">
                                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                            </form>
                        </div>
                    </div>
                    <div class="row justify-content-center">
                        <div class="col-11">
                            <table class="table table-striped mt-4">
                                <thead>
                                    <tr>
                                        <th>Mã Bạn Đọc</th>
                                        <th>Tên Bạn Đọc</th>
                                        <th>Email</th>
                                        <th>Lớp</th>
                                        <th>Số Điện Thoại</th>
                                        <th>Hành Động</th>
                                    </tr>
                                </thead>
                                <tbody id="readerTableBody">
                                    <c:choose>
                                        <c:when test="${not empty readers}">
                                            <c:forEach var="reader" items="${readers}">
                                                <tr>
                                                    <td>${reader.readerCode}</td>
                                                    <td>${reader.member.name}</td>
                                                    <td>${reader.member.email}</td>
                                                    <td>${reader.className}</td>
                                                    <td>${reader.member.phone != null ? reader.member.phone : 'N/A'}</td>
                                                    <td>
                                                        <form action="DocumentReaderBorrowing" method="post" style="display: inline;">
                                                            <input type="hidden" name="id" value="${reader.getMember().getId()}">
                                                            <button type="submit" class="btn btn-success">Chọn</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="6" class="text-center">Không tìm thấy bạn đọc.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <%@ include file="../footer.jsp"%>
    </body>
</html>