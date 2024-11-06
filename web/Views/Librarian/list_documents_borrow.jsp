<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <%@ include file="../header.jsp"%>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <body>
        <div style="display: flex; width: 100%; min-height: 100vh;">
            <%@ include file="sidebar.jsp"%>

            <section class="w-100">
                <div class="container mt-4">
                    <h2 class="text-center">Danh Sách Tài Liệu Đang Mượn</h2>
                    <form id="returnForm" action="ReciveDocumentList" method="post">
                        <input type="hidden" name="readerId" value="${readerId}" />
                        <table class="table table-striped mt-4">
                            <thead>
                                <tr>
                                    <th><input type="checkbox" id="selectAll" /></th>
                                    <th>ID Tài Liệu</th>
                                    <th>Tên Tài Liệu</th>
                                    <th>Ngày Mượn</th>
                                    <th>Ngày Trả Dự Kiến</th>
                                    <th>Số Lượng Đang Mượn</th>
                                    <th>Số Lượng Trả</th>
                                </tr>
                            </thead>
                            <tbody id="documentTableBody">
                                <c:choose>
                                    <c:when test="${not empty borrowedDocuments}">
                                        <c:forEach var="item" items="${borrowedDocuments}">
                                            <tr>
                                                <td><input type="checkbox" name="documentIds" value="${item.document.id}" class="selectCheckbox" /></td>
                                                <td>${item.document.id}</td>
                                                <td>${item.document.name}</td>
                                                <td>${item.borrowingOrder.borrowDate}</td>
                                                <td>${item.borrowingOrder.returnDate}</td>
                                                <td>${item.quantity}</td>
                                                <td><input type="number" name="returnQuantities" value="1" min="1" max="${item.quantity}" class="form-control return-quantity" /></td>
                                                <input type="hidden" name="borrowingOrderIds" value="${item.borrowingOrder.id}" />
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="text-center">Không có tài liệu nào đang mượn.</td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                        <button type="button" class="btn btn-success" onclick="confirmReturn()">Trả Tài Liệu Đã Chọn</button>
                    </form>
                </div>
            </section>
        </div>

        <%@ include file="../footer.jsp"%>

        <script>
            document.getElementById("selectAll").addEventListener("change", function () {
                const checkboxes = document.querySelectorAll(".selectCheckbox");
                checkboxes.forEach(checkbox => checkbox.checked = this.checked);
            });

            function confirmReturn() {
                const selectedDocuments = document.querySelectorAll(".selectCheckbox:checked");

                if (selectedDocuments.length === 0) {
                    Swal.fire({ icon: 'warning', title: 'Chưa chọn tài liệu', text: 'Vui lòng chọn ít nhất một tài liệu để trả.' });
                    return;
                }

                // Validate quantities
                let allQuantitiesValid = Array.from(selectedDocuments).every(checkbox => {
                    const quantityInput = checkbox.closest('tr').querySelector('.return-quantity');
                    const maxQuantity = parseInt(quantityInput.max);
                    const quantity = parseInt(quantityInput.value);
                    return quantity >= 1 && quantity <= maxQuantity;
                });

                if (!allQuantitiesValid) {
                    Swal.fire({ icon: 'error', title: 'Số lượng không hợp lệ', text: 'Vui lòng nhập số lượng trả hợp lệ cho mỗi tài liệu.' });
                    return;
                }

                // Confirmation dialog
                Swal.fire({
                    title: 'Xác nhận trả tài liệu',
                    text: "Bạn có chắc muốn trả những tài liệu đã chọn?",
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Trả tài liệu',
                    cancelButtonText: 'Hủy'
                }).then(result => {
                    if (result.isConfirmed) {
                        document.getElementById("returnForm").submit();
                    }
                });
            }
        </script>
    </body>
</html>