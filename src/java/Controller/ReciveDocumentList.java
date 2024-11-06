/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.Document023DAO;
import DAO.DocumentBorrow023DAO;
import DAO.ReturnOrder023DAO;
import Model.BorrowingOrder023;
import Model.DocumentBorrowed023;
import Model.ReturnOrder023;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "ReciveDocumentList", urlPatterns = {"/ReciveDocumentList"})
public class ReciveDocumentList extends HttpServlet {

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
            out.println("<title>Servlet ReciveDocumentList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReciveDocumentList at " + request.getContextPath() + "</h1>");
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
        try {
            int readerId = Integer.parseInt(request.getParameter("readerId"));
            List<DocumentBorrowed023> documentsToReturn = new ArrayList<>();

            String[] documentIds = request.getParameterValues("documentIds");
            String[] returnQuantities = request.getParameterValues("returnQuantities");
            String[] borrowingOrderIds = request.getParameterValues("borrowingOrderIds");

            if (documentIds != null && returnQuantities != null && borrowingOrderIds != null) {
                for (int i = 0; i < documentIds.length; i++) {
                    int documentId = Integer.parseInt(documentIds[i]);
                    int quantity = Integer.parseInt(returnQuantities[i]);
                    int borrowingOrderId = Integer.parseInt(borrowingOrderIds[i]);

                    DocumentBorrowed023 document = new DocumentBorrowed023();
                    Document023DAO document023DAO = new Document023DAO();
                    document.setDocument(document023DAO.getDocumentById(documentId));
                    document.setQuantity(quantity);

                    BorrowingOrder023 borrowingOrder = new BorrowingOrder023();
                    borrowingOrder.setId(borrowingOrderId);
                    document.setBorrowingOrder(borrowingOrder);

                    documentsToReturn.add(document);
                }

                DocumentBorrow023DAO documentBorrow023DAO = new DocumentBorrow023DAO();
                documentBorrow023DAO.addDocumentsToReturnOrder(documentsToReturn);
            }

            request.setAttribute("documentsToReturn", documentsToReturn);
            request.setAttribute("readerId", readerId);

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/Views/Librarian/recevie_documents.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reader ID");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred while retrieving documents");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
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
