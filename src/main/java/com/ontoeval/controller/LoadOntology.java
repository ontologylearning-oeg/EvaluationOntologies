package com.ontoeval.controller;

import com.ontoeval.model.Access.TermDAO;
import com.ontoeval.model.Access.TermImpl;
import com.ontoeval.model.TermVO;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by dachafra on 12/05/16.
 */
public class LoadOntology extends javax.servlet.http.HttpServlet {

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
       String words = request.getParameter("term");
       String termhood= request.getParameter("termhood");
       ArrayList<TermVO> terms  = new ArrayList<TermVO>();
       StringTokenizer tokenizer =  new StringTokenizer(words,"\n");
       StringTokenizer tokenizer1 = new StringTokenizer(termhood,"\n");

       while(tokenizer.hasMoreTokens()){
           String w = tokenizer.nextToken();
           Double th = Double.parseDouble(tokenizer1.nextToken());
           terms.add(new TermVO(w,th));
       }
        try{

            TermDAO termsDAO = (TermDAO) new TermImpl(TermImpl.CrearConexion());
            termsDAO.InsertTerms(terms);
        }catch (SQLException e){
            System.out.println("Error");
        }

    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }
}
