/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.Reader023DAO;
import Model.Reader023;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "SearchReader", urlPatterns = {"/SearchReader"})
public class SearchReader extends HttpServlet {

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
            out.println("<title>Servlet SearchReader</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchReader at " + request.getContextPath() + "</h1>");
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
        try {
            Reader023DAO readerDAO = new Reader023DAO();
            List<Reader023> readers = readerDAO.getAllReaders();

            // Kiểm tra xem danh sách trả về có rỗng không
            if (readers != null && !readers.isEmpty()) {
                request.setAttribute("readers", readers);
            } else {
                request.setAttribute("message", "Không có phiếu trả nào.");
            }

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/Views/Librarian/search_reader.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // In ra thông báo lỗi
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình lấy dữ liệu. Vui lòng thử lại."); // Thêm thông báo lỗi cho người dùng
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/Views/Librarian/search_reader.jsp");
            dispatcher.forward(request, response);
        }
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
        String action = request.getParameter("action");
        try {
            if ("search".equals(action)) {
                String readerCode = request.getParameter("readerCode");
                Reader023DAO readerDAO = new Reader023DAO();

                // Tìm kiếm độc giả theo mã
                List<Reader023> readers = readerDAO.searchReader(readerCode);
                request.setAttribute("readers", readers);

                // Chuyển tiếp đến JSP hiển thị kết quả
                request.getRequestDispatcher("/Views/Librarian/search_reader.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi SQL
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi kết nối đến cơ sở dữ liệu. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/Views/Librarian/search_reader.jsp").forward(request, response); // Chuyển tiếp đến JSP với thông báo lỗi
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // In ra lỗi ClassNotFound
            request.setAttribute("errorMessage", "Lớp không tìm thấy. Vui lòng kiểm tra cấu hình.");
            request.getRequestDispatcher("/Views/Librarian/search_reader.jsp").forward(request, response); // Chuyển tiếp đến JSP với thông báo lỗi
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi chung
            request.setAttribute("errorMessage", "Có lỗi không xác định. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/Views/Librarian/search_reader.jsp").forward(request, response); // Chuyển tiếp đến JSP với thông báo lỗi
        }
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
