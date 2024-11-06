<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <%@ include file="../header.jsp"%>

    <body>
        <div style="display: flex; width: 100%; min-height: 100vh;">
            <%@ include file="sidebar.jsp"%>

            <section class="w-100">
                <div class="container mt-4">
                    <div class="row justify-content-center">
                        <div class="col-11">
                            <h2 class="text-center">Lịch Sử Trả Tài Liệu</h2>
                            <div class="d-flex justify-content-end mb-3">
                                <button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/SearchReader'">Nhận Trả Tài Liệu</button>
                            </div>

                        </div>
                    </div>
                </div>
            </section>
        </div>

        <%@ include file="../footer.jsp"%>
    </body>
</html>