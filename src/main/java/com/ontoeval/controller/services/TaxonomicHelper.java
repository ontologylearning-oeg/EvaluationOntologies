package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.RelationDAO;
import com.ontoeval.model.Access.RelationImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by dchavesf on 5/09/16.
 */
public class TaxonomicHelper {
    private final HttpServletRequest request;
    private final RelationDAO relations;
    private final RelationEvaluationVO evalRelations;


    public TaxonomicHelper (HttpServletRequest request) throws SQLException, IOException {
        this.request = request;
        relations = new RelationImpl(RelationImpl.CrearConexion());
        evalRelations=null;
    }

    public boolean loadRelations(String text, String filename, String domain){
        ArrayList<RelationVO> relationsaux = new ArrayList<RelationVO>();
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        String term = tokenizer.nextToken();
        while(!term.equals("Taxonomic;")){
            term = tokenizer.nextToken();
        }
        while(tokenizer.hasMoreTokens()){
            StringTokenizer tokenizer1 = new StringTokenizer(tokenizer.nextToken(),";");
            RelationVO relation = new RelationVO(filename,tokenizer1.nextToken(),tokenizer1.nextToken(),domain,false);
            relationsaux.add(relation);
        }
        return relations.insertRelations(relationsaux);
    }

    public boolean createGSRelations(ArrayList<TermVO> relevant, OntologyVO ontology){
        boolean flag = relations.getRandomRelations();
        if(flag==true) {
            ArrayList<RelationVO> randomRelations = new ArrayList<RelationVO>();
            while (relevant.size() != 0) {
                TermVO t = relevant.get(0);
                for (TermVO aux : relevant) {
                    RelationVO r = new RelationVO(ontology.getName(), t.getWord(), aux.getWord(), ontology.getDomain(), true);
                    randomRelations.add(r);
                }
            }
            return relations.insertRelations(randomRelations);
        }
        else
            return true;
    }

    public String comprobarTaxonomic(String ontology,UserVO user){
        ArrayList<RelationVO> randomRel = relations.getRandomRelations(ontology);
        ArrayList<RelationVO> randomRelUser = relations.getRandomRelations(ontology,user.getEmail());
        return "";


    }
}
