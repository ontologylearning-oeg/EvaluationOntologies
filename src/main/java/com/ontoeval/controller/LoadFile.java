package com.ontoeval.controller;

import com.ontoeval.controller.services.OntologyHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dachafra on 12/05/16.
 */
public class LoadFile extends javax.servlet.http.HttpServlet {

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String texto = request.getParameter("texto");
        String name = request.getParameter("nombre");
        boolean flag=true;
        try{
            OntologyHelper helper = new OntologyHelper(request);
            flag=helper.insertOntology(texto,name);
            helper.close();
        }catch (SQLException e){
            System.out.println("Error en LoadFile "+e.getMessage());
        }
        if(flag==false){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }
}
