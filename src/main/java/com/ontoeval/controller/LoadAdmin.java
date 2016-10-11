package com.ontoeval.controller;

import com.ontoeval.controller.services.AdminHelper;
import com.ontoeval.model.UserVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 16/09/16.
 */
public class LoadAdmin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag=true;
        String name = request.getParameter("name");
        String r = request.getParameter("remove");
        try{
            AdminHelper helper = new AdminHelper(request);
            if(r==null)
                flag=helper.loadAdmin(name);
            else
                flag= helper.removeOntology(name);
            helper.close();
        }catch (SQLException e){
            System.out.println("Error en LoadOntology "+e.getMessage());
        }
        if(flag==false){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
