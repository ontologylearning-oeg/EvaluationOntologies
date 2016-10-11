package com.ontoeval.controller;

import com.ontoeval.controller.services.LexicalHelper;
import com.ontoeval.controller.services.OntologyHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 6/09/16.
 */
public class EvaluatedTerms extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("text");
        String page =null;
        try{
            LexicalHelper helper = new LexicalHelper(request);
            page = helper.saveTerms(text);
            helper.close();
        }catch (SQLException e){
            System.out.println("Error en EvaluatedTerms "+e.getMessage());
        }
        if(page==null){
            page = "./eval/index.jsp";
            try{
                OntologyHelper o = new OntologyHelper(request);
                o.loadOntologies(null);
                o.close();
            }catch (SQLException e){
                System.out.println("Error en EvaluatedTerms "+e.getMessage());
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(page);
        }
        else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(page);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
