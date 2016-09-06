package com.ontoeval.controller;

import com.ontoeval.controller.services.OntologyHelper;
import com.ontoeval.model.Access.TermDAO;
import com.ontoeval.model.Access.TermImpl;
import com.ontoeval.model.TermVO;
import com.sun.org.apache.xpath.internal.SourceTree;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
