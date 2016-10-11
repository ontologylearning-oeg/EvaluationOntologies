package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.Implement.EncryptConnection;
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
            RelationVO relation = new RelationVO(o,tokenizer1.nextToken(),tokenizer1.nextToken(),false);
            relationsaux.add(relation);
        }
        return relations.insertRelations(relationsaux);
    }

    public boolean createGSRelations(ArrayList<TermVO> relevant, OntologyVO ontology, String user){
        ArrayList<RelationVO> flag = relations.getRandomRelations(ontology.getName());
        if(flag.size()==0) {
            ArrayList<RelationVO> randomRelations = new ArrayList<>();
            for(int j=0; j<relevant.size(); j++) {
                for (int i=(j+1); i<relevant.size();i++) {
                    TermVO t = relevant.get(j);
                    TermVO aux = relevant.get(i);
                    if(t.getWord()!=aux.getWord()){
                        RelationVO r = new RelationVO(ontology, t.getWord(), aux.getWord(), true);
                        RelationVO r2 = new RelationVO(ontology, aux.getWord(), t.getWord(), true);
                        randomRelations.add(r);randomRelations.add(r2);
                    }
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
                for(int i=0; i<randomRel.size();i++){
                    for(RelationEvaluationVO aux: randomEvalUser){
                        if(randomRel.get(i).getTerm1()==aux.getTerm1() && randomRel.get(i).getTerm2()==aux.getTerm2()){
                            randomRel.remove(i);
                            i--;
                        }
                    }
                }
                if(relationsForEval(randomRel))
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
                if(aux.get(0).getTerm1().equals(r.getTerm1()) && aux.get(0).getTerm2().equals(r.getTerm2()) && r.isRandom()){
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
