package com.ontoeval.controller;

import com.ontoeval.controller.services.LexicalHelper;
import com.ontoeval.controller.services.ResultsHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 27/09/16.
 */
public class ChangeRelevance extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer value = Integer.parseInt(request.getParameter("value"));
        try{
            LexicalHelper h = new LexicalHelper(request);
            ResultsHelper helper = new ResultsHelper(request);
            helper.changeRelevevance(value,h);
            helper.close();
        }catch (SQLException e){
            System.out.println("Error en changeRelevance "+e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
