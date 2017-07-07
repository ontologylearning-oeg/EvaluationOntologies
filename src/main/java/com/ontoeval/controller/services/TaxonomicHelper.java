package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.EncryptConnection;
import com.ontoeval.model.Access.RelationDAO;
import com.ontoeval.model.Access.RelationEvaluationDAO;
import com.ontoeval.model.Access.Implement.RelationEvaluationImpl;
import com.ontoeval.model.Access.Implement.RelationImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        relations = new RelationImpl(EncryptConnection.CrearConexion());
        evalRelations=new RelationEvaluationImpl(EncryptConnection.CrearConexion());
    }

    public void close(){
        relations.close();
        evalRelations.close();
    }

    public ArrayList<RelationVO> recuperar(String o){
        return relations.getRelations(o);
    }

    public boolean loadRelations(String text, OntologyVO o){
        ArrayList<RelationVO> relationsaux = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        String term = tokenizer.nextToken();
        while(!term.equals("Taxonomic;;")){
            term = tokenizer.nextToken();
        }
        tokenizer.nextToken();
        while(tokenizer.hasMoreTokens()){
            StringTokenizer tokenizer1 = new StringTokenizer(tokenizer.nextToken(),";");
            RelationVO relation = new RelationVO(o,tokenizer1.nextToken(),tokenizer1.nextToken(),tokenizer1.nextToken());
            relationsaux.add(relation);
        }
        return relations.insertRelations(relationsaux);
    }


    public String comprobarTaxonomic(OntologyVO ontology,UserVO user){
        ArrayList<RelationVO> rel = relations.getRelations(ontology.getName());
        ArrayList<RelationEvaluationVO> randomEvalUser = evalRelations.getEvaluatedRelations(ontology.getName(),user.getEmail());
        ArrayList<RelationEvaluationVO> randomEval = evalRelations.getEvaluatedRelations(ontology.getName());

        if(randomEval.size()!=(rel.size()*5)){//no ha terminado
            if(rel.size()!=randomEvalUser.size()){ //no ha terminado el usuario
                for(int i=0; i<rel.size();i++){
                    for(RelationEvaluationVO aux: randomEvalUser){
                        if(rel.get(i).getTerm1()==aux.getTerm1() && rel.get(i).getTerm2()==aux.getTerm2()){
                            rel.remove(i);
                            i--;
                        }
                    }
                }
                if(relationsForEval(rel))
                    return "./eval/taxonomic.jsp";
                else
                    return null;
            }
            else
                return null;
        }
        else {
            rellenarBD(recuperar(ontology.getName()),randomEval);
            ontology.setState("Results");
            return "results";
        }
    }

    public String saveRelations(String text){
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        OntologyVO ontology = (OntologyVO) session.getAttribute("ontology");
        ArrayList<RelationEvaluationVO> relEval = new ArrayList<>();
        while (tokenizer.hasMoreTokens()){
            StringTokenizer tokenizer1 = new StringTokenizer(tokenizer.nextToken(),";");
            String term1 = tokenizer1.nextToken();
            String term2 = tokenizer1.nextToken();
            String aux = tokenizer1.nextToken();
            boolean eval;
            if(aux.equals("yes"))
                eval=true;
            else
                eval =false;
            RelationEvaluationVO r = new RelationEvaluationVO(ontology,term1,term2,user,eval);
            relEval.add(r);
        }
        evalRelations.insertRelations(relEval);
        ArrayList<RelationVO> relations = (ArrayList<RelationVO>) session.getAttribute("randomRel");
        for(RelationEvaluationVO aux : relEval){
            for (int i=0; i<relations.size(); i++){
                if(relations.get(i).getTerm1().equals(aux.getTerm1()) && relations.get(i).getTerm2().equals(aux.getTerm2())){
                    relations.remove(i);
                    break;
                }
            }
        }
        if(relationsForEval(relations)){
            return "./eval/taxonomic.jsp";
        }
        else
            return null;
    }

    private boolean relationsForEval(ArrayList<RelationVO> random){
        ArrayList<RelationVO> relforeval = new ArrayList<>(); int i=0;
        if(random.size()>0){
            while(i<8 && i<random.size()){
                relforeval.add(random.get(i));
                i++;
            }
            request.getSession().setAttribute("randomRel",random);
            request.getSession().setAttribute("relations",relforeval);
            return true;
        }
        else{
            return false;
        }
    }

    private void rellenarBD(ArrayList<RelationVO> normal,ArrayList<RelationEvaluationVO> random){
        ArrayList<RelationEvaluationVO> aux = new ArrayList<>();
        while(random.size()!=0){
            RelationEvaluationVO singleAux= random.get(0);
            for(int i=1; i<random.size();i++){
                if(singleAux.getTerm1().equals(random.get(i).getTerm1()) && singleAux.getTerm2().equals(random.get(i).getTerm2())){
                    aux.add(random.get(i));
                    random.remove(random.get(i));
                    i--;
                }
            }
            aux.add(singleAux);
            random.remove(0);
            Integer rel = countForRelevant(aux);
            for(RelationVO r: normal){
                if(aux.get(0).getTerm1().equals(r.getTerm1()) && aux.get(0).getTerm2().equals(r.getTerm2())){
                    r.setYes(rel);
                    r.setNo(5-rel);
                    if(rel>2)
                        r.setGS(true);
                }
            }
            aux = new ArrayList<>();
        }
        relations.updateRelations(normal);
    }

    private Integer countForRelevant(ArrayList<RelationEvaluationVO> r){
        Integer rel=0;
        for(RelationEvaluationVO aux:r){
            if(aux.isRelevant()){
                rel++;
            }
        }
        return rel;
    }
}
