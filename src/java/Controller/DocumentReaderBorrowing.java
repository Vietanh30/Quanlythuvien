/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DocumentBorrow023DAO;
import Model.DocumentBorrowed023;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "DocumentReaderBorrowing", urlPatterns = {"/DocumentReaderBorrowing"})
public class DocumentReaderBorrowing extends HttpServlet {

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
            out.println("<title>Servlet DocumentReaderBrrowing</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DocumentReaderBrrowing at " + request.getContextPath() + "</h1>");
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
            // Forward the request to the JSP page
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/Views/Librarian/recevie_documents.jsp");
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                throw new ServletException("RequestDispatcher is null. Check the JSP path.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
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
        try {
            // Lấy ID độc giả từ tham số
            int readerId = Integer.parseInt(request.getParameter("id"));

            // Truy xuất danh sách tài liệu đã mượn
            DocumentBorrow023DAO documentBorrow023DAO = new DocumentBorrow023DAO();
            List<DocumentBorrowed023> borrowedDocuments = documentBorrow023DAO.getAllBorrowingByReaderId(readerId);

            // Đưa danh sách tài liệu đã mượn vào request
            request.setAttribute("borrowedDocuments", borrowedDocuments);
            request.setAttribute("readerId", readerId); // Có thể cần để hiển thị thông tin độc giả

            // Chuyển tiếp đến JSP để hiển thị danh sách tài liệu
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/Views/Librarian/list_documents_borrow.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reader ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving borrowed documents");
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
