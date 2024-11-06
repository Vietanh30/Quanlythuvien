/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CardReader023DAO;
import DAO.Reader023DAO;
import Model.Reader023;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "RegistrationCard", urlPatterns = {"/RegistrationCard"})
public class RegistrationCard extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistrationCard</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistrationCard at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin từ request
        String readerCode = request.getParameter("readerCode");
        String className = request.getParameter("className");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birthdate = request.getParameter("birthdate");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String memberIdString = request.getParameter("memberId"); // Lấy ID từ request
        int memberId = Integer.parseInt(memberIdString); // Chuyển đổi ID sang kiểu int

// Khởi tạo DAO
        CardReader023DAO cardReaderDAO;
        Reader023DAO readerDAO;
        try {
            cardReaderDAO = new CardReader023DAO();
            readerDAO = new Reader023DAO();

            // Lấy thông tin thành viên bằng ID
            Reader023 reader = readerDAO.getReaderById(memberId); // Phương thức để lấy thành viên
            if (reader != null) {
                // Tạo thẻ bạn đọc
                boolean isCardCreated = cardReaderDAO.createOrUpdateCardReader(memberId);
                if (isCardCreated) {
                    // Cập nhật thông tin độc giả
                    boolean isReaderUpdated = readerDAO.updateReader(memberId, readerCode, className);
                    // Cập nhật thông tin thành viên
                    boolean isMemberUpdated = readerDAO.updateMember(memberId, name, email, phone, birthdate, address);

                    if (isReaderUpdated && isMemberUpdated) {
                        request.setAttribute("message", "Đăng ký thành công!");
                    } else {
                        request.setAttribute("message", "Đăng ký thành công nhưng không thể cập nhật thông tin độc giả hoặc thành viên.");
                    }
                } else {
                    request.setAttribute("message", "Đăng ký thành công nhưng không thể tạo thẻ bạn đọc.");
                }
            } else {
                request.setAttribute("message", "Không thể lấy thông tin thành viên bằng ID.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegistrationCard.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Đã xảy ra lỗi khi đăng ký: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationCard.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Không tìm thấy lớp: " + ex.getMessage());
        }

        request.getRequestDispatcher("/Views/Reader/registration_card.jsp").forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
