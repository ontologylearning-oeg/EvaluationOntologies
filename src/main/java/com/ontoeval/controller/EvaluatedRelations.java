package com.ontoeval.controller;

import com.ontoeval.controller.services.TaxonomicHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 7/09/16.
 */
public class EvaluatedRelations extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("text");
        String page =null;
        try{
            TaxonomicHelper helper = new TaxonomicHelper(request);
            page = helper.saveRelations(text);
            helper.close();
        }catch (SQLException e){
            System.out.println("Error en EvaluatedTerms "+e.getMessage());
        }
        if(page==null){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("./eval/index.jsp");
        }
        else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(page);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
