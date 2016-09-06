package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.RelationDAO;
import com.ontoeval.model.Access.RelationEvaluationDAO;
import com.ontoeval.model.Access.RelationEvaluationImpl;
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
    private final RelationEvaluationDAO evalRelations;


    public TaxonomicHelper (HttpServletRequest request) throws SQLException, IOException {
        this.request = request;
        relations = new RelationImpl(RelationImpl.CrearConexion());
        evalRelations=new RelationEvaluationImpl(RelationEvaluationImpl.CrearConexion());
    }

    public ArrayList<RelationVO> recuperar(String o){
        return relations.getNormalRelations(o);
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
        boolean flag = relations.checkRandomRelations();
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

    public String comprobarTaxonomic(OntologyVO ontology,UserVO user){
        ArrayList<RelationVO> randomRel = relations.getRandomRelations(ontology.getName());
        ArrayList<RelationEvaluationVO> randomEvalUser = evalRelations.getEvaluatedRelations(ontology.getName(),user.getEmail());
        ArrayList<RelationEvaluationVO> randomEval = evalRelations.getEvaluatedRelations(ontology.getName());

        if(randomEval.size()!=(randomRel.size()*5)){//no ha terminado
            if(randomRel.size()!=randomEvalUser.size()){ //no ha terminado el usuario
                for(RelationVO randomRelaux: randomRel){
                    for(RelationEvaluationVO aux: randomEvalUser){
                        if(randomRelaux.getTerm1()==aux.getTerm1() && randomRelaux.getTerm2()==aux.getTerm2()){
                            randomRel.remove(randomRelaux);
                        }
                    }
                }
                request.getSession().getServletContext().setAttribute("relations", randomRel);
                return "./eval/taxonomic.jsp";
            }
            else
                return null;
        }
        else {
            rellenarBD(ontology,relations.getNormalRelations(ontology.getName()),randomEval);
            return "results";
        }
    }

    private void rellenarBD(OntologyVO o,ArrayList<RelationVO> normal,ArrayList<RelationEvaluationVO> random){
        ArrayList<RelationEvaluationVO> aux = new ArrayList<RelationEvaluationVO>();
        boolean flag=false;
        while(random.size()!=0){
            RelationEvaluationVO singleAux= random.get(0);
            for(int i=1; i<random.size();i++){
                if(singleAux.getTerm1()==random.get(i).getTerm1() && singleAux.getTerm2()==random.get(i).getTerm2()){
                    aux.add(random.get(i));
                    random.remove(random.get(i));
                }
            }
            random.remove(0);
            if(countForRelevant(aux)){
                for(RelationVO r: normal){
                    if(random.get(0).getTerm1()==r.getTerm1() && random.get(0).getTerm2()==r.getTerm2()){
                        r.setGS(true);
                        flag=true;
                        break;
                    }
                }
                if(flag!=true){
                    RelationVO v = new RelationVO(o.getName(),random.get(0).getTerm1(),random.get(0).getTerm2(),o.getDomain(),true,true);
                    relations.insertRelation(v);
                }
            }
            flag=false;
            aux = new ArrayList<RelationEvaluationVO>();
        }
        relations.updateRelations(normal);
    }

    private boolean countForRelevant(ArrayList<RelationEvaluationVO> r){
        Integer rel=0;
        for(RelationEvaluationVO aux:r){
            if(aux.isRelevant()){
                rel++;
            }
        }
        if(rel>2){
            return true;
        }
        else{
            return false;
        }
    }
}
