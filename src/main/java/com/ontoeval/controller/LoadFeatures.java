package com.ontoeval.controller;

import com.ontoeval.controller.services.OntologyHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 2/09/16.
 */
public class LoadFeatures extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String page=null;
        try{
           OntologyHelper helper = new OntologyHelper(request);
           page=helper.loadFeatures(name);
        }catch (SQLException e){
            System.out.println("Error en LoadFeatures "+e.getMessage());
        }
        if(page==null){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else if(page.equals("notUser")){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("notUser");
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
