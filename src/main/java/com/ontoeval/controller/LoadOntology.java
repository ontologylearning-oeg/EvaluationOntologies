package com.ontoeval.controller;

import com.ontoeval.controller.services.OntologyHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 1/09/16.
 */
public class LoadOntology extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag=true;
        String name = request.getParameter("user");
        try{
            OntologyHelper helper = new OntologyHelper(request);
            flag=helper.loadOntologies(name);
            helper.close();
        }catch (SQLException e){
            System.out.println("Error en LoadOntology "+e.getMessage());
        }
        if(flag==false){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else {
            String page;
            if (name != null) {
                page = "./eval/admin.jsp";
            } else {
                page = "./eval/index.jsp";
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(page);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
