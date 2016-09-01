package com.ontoeval.controller.services;

import com.ontoeval.model.Access.*;
import com.ontoeval.model.OntologyVO;
import com.ontoeval.model.RelationVO;
import com.ontoeval.model.TermVO;

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

    public boolean insertOntology(String text, String filename) throws  SQLException{
        if(!terms.InsertTerms(loadTerms(text,filename)) ||
            !relations.insertRelations(loadRelations(text,filename)) ||
            !ontology.insertOntology(new OntologyVO(filename)))
            throw  new SQLException("Error en OntologyHelper");
        else
            return true;
    }

    private ArrayList<TermVO> loadTerms(String text, String filename){
        ArrayList<TermVO> terms = new ArrayList<TermVO>();
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        String term = tokenizer.nextToken();
        while(!term.equals("Taxonomic Relations")){
            TermVO aux = new TermVO(term,filename);
            terms.add(aux);
            term = tokenizer.nextToken();
        }
        return terms;
    }

    private ArrayList<RelationVO> loadRelations(String text, String filename){
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
            RelationVO relation = new RelationVO(filename,term1,term2);
            relations.add(relation);
        }
        return relations;
    }
}
