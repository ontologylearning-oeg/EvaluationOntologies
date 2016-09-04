package com.ontoeval.controller.services;

import com.ontoeval.model.Access.*;
import com.ontoeval.model.OntologyVO;
import com.ontoeval.model.RelationVO;
import com.ontoeval.model.TermVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by dchavesf on 1/09/16.
 */
public class OntologyHelper {
    private final HttpServletRequest request;
    private final OntologyDAO ontology;
    private final TermDAO terms;
    private final RelationDAO relations;

    public OntologyHelper (HttpServletRequest request) throws SQLException, IOException {
        this.request = request;
        ontology = new OntologyImpl(OntologyImpl.CrearConexion());
        terms = new TermImpl(TermImpl.CrearConexion());
        relations = new RelationImpl(RelationImpl.CrearConexion());
    }

    public boolean loadOntologies() throws  SQLException{
        ServletContext context = request.getSession().getServletContext();
        ArrayList<OntologyVO> onts = ontology.recuperarOntologias();
        if(onts==null){
            throw  new SQLException("Error en OntologyHelper");
        }
        else{
            context.setAttribute("ontos",onts);
            return true;
        }

    }
    /**Aqui se debe comprobar como está la evaluación para dejar o no lo de la relaciones**/
    public boolean loadFeatures(String name) throws SQLException{
        ServletContext context = request.getSession().getServletContext();
        ArrayList<TermVO> t = terms.loadTerms(name);
        if(t!=null){
            context.setAttribute("terms",t);
            context.setAttribute("ontology",ontology.recuperarOntologias(name));
            return true;
        }
        else{
            throw new SQLException("Error en LoadFeatures/OntologyHelper");
        }
    }

    public boolean insertOntology(String text, String filename) throws  SQLException{
        String domain = loadDomain(text);
        if(!ontology.insertOntology(new OntologyVO(filename, domain)) ||
                !terms.InsertTerms(loadTerms(text,filename, domain)) ||
            !relations.insertRelations(loadRelations(text,filename, domain)))
            throw  new SQLException("Error en insertOntology/OntologyHelper");
        else{
             return true;
        }
    }

    private String loadDomain(String text){
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        tokenizer = new StringTokenizer(tokenizer.nextToken(),":");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }

    private ArrayList<TermVO> loadTerms(String text, String filename, String domain){
        ArrayList<TermVO> terms = new ArrayList<TermVO>();
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        tokenizer.nextToken();
        String term = tokenizer.nextToken();
        while(!term.equals("Taxonomic Relations")){
            TermVO aux = new TermVO(term,filename, domain);
            terms.add(aux);
            term = tokenizer.nextToken();
        }
        return terms;
    }

    private ArrayList<RelationVO> loadRelations(String text, String filename, String domain){
        ArrayList<RelationVO> relations = new ArrayList<RelationVO>();
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        String term = tokenizer.nextToken();
        while(!term.equals("Taxonomic Relations")){
            term = tokenizer.nextToken();
        }
        while(tokenizer.hasMoreTokens()){
            StringTokenizer tokenizer1 = new StringTokenizer(tokenizer.nextToken()," ");
            String term1 = tokenizer1.nextToken();
            tokenizer1.nextToken();tokenizer1.nextToken();tokenizer1.nextToken();
            String term2 = tokenizer1.nextToken();
            RelationVO relation = new RelationVO(filename,term1,term2,domain);
            relations.add(relation);
        }
        return relations;
    }
}
