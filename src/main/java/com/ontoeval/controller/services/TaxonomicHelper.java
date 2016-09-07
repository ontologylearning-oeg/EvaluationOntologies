package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.RelationDAO;
import com.ontoeval.model.Access.RelationEvaluationDAO;
import com.ontoeval.model.Access.RelationEvaluationImpl;
import com.ontoeval.model.Access.RelationImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Array;
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
            for(int j=0; j<relevant.size(); j++) {
                for (int i=(j+1); i<relevant.size();i++) {
                    TermVO t = relevant.get(j);
                    TermVO aux = relevant.get(i);
                    if(t.getWord()!=aux.getWord()){
                        RelationVO r = new RelationVO(ontology.getName(), t.getWord(), aux.getWord(), ontology.getDomain(), true);
                        RelationVO r2 = new RelationVO(ontology.getName(), aux.getWord(), t.getWord(), ontology.getDomain(), true);
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
            rellenarBD(ontology,recuperar(ontology.getName()),randomEval);
            return "results";
        }
    }

    public String saveRelations(String text){
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        ServletContext context = request.getSession().getServletContext();
        UserVO user = (UserVO) context.getAttribute("user");
        OntologyVO ontology = (OntologyVO) context.getAttribute("ontology");
        ArrayList<RelationEvaluationVO> relEval = new ArrayList<RelationEvaluationVO>();
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
            RelationEvaluationVO r = new RelationEvaluationVO(ontology.getName(),ontology.getDomain(),term1,term2,user.getEmail(),eval);
            relEval.add(r);
        }
        evalRelations.insertRelations(relEval);
        ArrayList<RelationVO> relations = (ArrayList<RelationVO>) context.getAttribute("randomRel");
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
        ArrayList<RelationVO> relforeval = new ArrayList<RelationVO>(); int i=0;
        if(random.size()>0){
            while(i<8 && i<random.size()){
                relforeval.add(random.get(i));
                i++;
            }
            request.getSession().getServletContext().setAttribute("randomRel",random);
            request.getSession().getServletContext().setAttribute("relations",relforeval);
            return true;
        }
        else{
            return false;
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
                    i--;
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
