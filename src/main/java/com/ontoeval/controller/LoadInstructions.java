package com.ontoeval.controller;

import com.ontoeval.controller.services.InstructionsHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchaves on 4/10/16.
 */
public class LoadInstructions extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String relevant = request.getParameter("relevant");
        String norelevant = request.getParameter("norelevant");
        String stricly = request.getParameter("strictly");
        String reason = request. getParameter("reason");
        boolean flag=true;
        try{
            InstructionsHelper helper = new InstructionsHelper(request);
            flag=helper.loadInstructions(relevant,norelevant,stricly,reason);
        }catch (SQLException e){
            System.out.println("Error en LoadInstructions "+e.getMessage());
        }
        if(flag==false){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
