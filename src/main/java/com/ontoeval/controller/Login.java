package com.ontoeval.controller;

import com.ontoeval.controller.services.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 1/09/16.
 */
public class Login extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(Login.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("doPost:: " + request);
        System.out.println("doPost");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String signup = request.getParameter("signup");
        boolean flag=true;
        if(email==null){
            request.getSession().getServletContext().removeAttribute("user");
        }
        else{
            try {
                UserHelper helper = new UserHelper(request);
                flag=helper.insertUser(email,pass,signup);
                helper.close();
            }catch (SQLException e){
                System.out.println("Error en Servlet Login"+e.getMessage());
            }
        }
        if(flag==false){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
