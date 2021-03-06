package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.*;
import com.ontoeval.model.Access.Implement.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dchavesf on 16/09/16.
 */
public class AdminHelper {
    private HttpServletRequest request;
    private final TermDAO terms;
    private RelationDAO relations;
    private RelationEvaluationDAO evalRel;
    private final TermEvaluationDAO evalTerms;
    private final OntologyDAO ontos;
    private AdminVO admin;
    private UserEvalDAO userEval;

    public AdminHelper(HttpServletRequest request) throws SQLException, IOException {
        this.request=request;
        terms = new TermImpl(EncryptConnection.CrearConexion());
        ontos = new OntologyImpl(EncryptConnection.CrearConexion());
        evalTerms = new TermEvaluationImpl(EncryptConnection.CrearConexion());
        userEval=new UserEvalImpl(EncryptConnection.CrearConexion());
        relations = new RelationImpl(EncryptConnection.CrearConexion());
        evalRel = new RelationEvaluationImpl(EncryptConnection.CrearConexion());
        admin = new AdminVO();
    }

    public void close(){
       terms.close();
       evalTerms.close();
       ontos.close();
       userEval.close();
    }


    public boolean loadAdmin(String ontology){
        OntologyVO o = ontos.recuperarOntologias(ontology);
        ArrayList<TermVO> t = terms.loadTerms(o.getName());
        ArrayList<RelationVO> rel = relations.getRelations(o.getName());
        ArrayList<TermEvaluationVO> teval = evalTerms.evaluatedTerms(o.getName());
        yesandno(t,(ArrayList<TermEvaluationVO>)teval.clone());
        this.RelationsResults(rel,(ArrayList<RelationEvaluationVO>)evalRel.getEvaluatedRelations(o.getName()).clone());
        admin.setTerms(t);
        admin.setRelations(rel);
        admin.setNterms(terms.loadNormal(o.getName()).size());
        admin.setTevaluators(userEval.nUsers(ontology));
        admin.setNevaluations(t.size()*5);
        admin.setNcontrol(terms.loadControl(o.getName()).size());
        admin.setNevaluators(countEvaluators(teval));
        if(admin.getNevaluations()!=0)
            admin.setComplete((double)teval.size()/(double)admin.getNevaluations());
        else
            admin.setComplete(0.0);
        admin.setOntology(o.getName());
        request.getSession().setAttribute("admin",admin);
        return true;
    }

    public boolean removeOntology(String name){
        return ontos.removeOntology(name);
    }

    private Integer countEvaluators(ArrayList<TermEvaluationVO> teval){
        ArrayList<String> users= new ArrayList<>();
        if(teval.size()>0){
            users.add(teval.get(0).getUser().getEmail());
            for(TermEvaluationVO t: teval){
                if(!users.contains(t.getUser().getEmail())){
                    users.add(t.getUser().getEmail());
                }
            }
            return users.size();
        }
        else
            return 0;

    }


    private void yesandno(ArrayList<TermVO> t, ArrayList<TermEvaluationVO> teval){
        ArrayList<TermEvaluationVO> tevalaux = new ArrayList<>();
        while(teval.size()!=0){
            TermEvaluationVO aux = teval.get(0);
            for(int i=1; i<teval.size();i++){
                if(aux.getTerm().equals(teval.get(i).getTerm())){
                    tevalaux.add(teval.get(i));
                    teval.remove(i);
                    i--;
                }
            }
            tevalaux.add(aux);
            teval.remove(0);
            Integer yes = countForRelavant(tevalaux,true);
            Integer no = countForRelavant(tevalaux,false);
            for(TermVO taux: t){
                if(taux.getWord().equals(tevalaux.get(0).getTerm())){
                    taux.setYes(yes);
                    taux.setNo(no);
                    break;
                }
            }

            tevalaux = new ArrayList<>();
        }

    }

    private Integer countForRelavant(ArrayList<TermEvaluationVO> t, boolean flag){
        Integer rel=0;
        for(TermEvaluationVO aux:t){
            if(flag){
                if(aux.isRelevant()){
                    rel++;
                }
            }
            else {
                if (!aux.isRelevant())
                    rel++;
            }
        }
        return rel;
    }

    private void RelationsResults(ArrayList<RelationVO> t, ArrayList<RelationEvaluationVO> teval){
        ArrayList<RelationEvaluationVO> tevalaux = new ArrayList<>();
        while(teval.size()!=0){
            RelationEvaluationVO aux = teval.get(0);
            for(int i=1; i<teval.size();i++){
                if(aux.getTerm1().equals(teval.get(i).getTerm1()) && aux.getTerm2().equals(teval.get(i).getTerm2())){
                    tevalaux.add(teval.get(i));
                    teval.remove(i);
                    i--;
                }
            }
            tevalaux.add(aux);
            teval.remove(0);
            Integer yes = RelationsRelevant(tevalaux,true);
            Integer no = RelationsRelevant(tevalaux,false);
            for(RelationVO taux: t){
                if(taux.getTerm1().equals(tevalaux.get(0).getTerm1()) && taux.getTerm2().equals(tevalaux.get(0).getTerm2())){
                    taux.setYes(yes);
                    taux.setNo(no);
                    break;
                }
            }

            tevalaux = new ArrayList<>();
        }

    }

    private Integer RelationsRelevant(ArrayList<RelationEvaluationVO> t, boolean flag){
        Integer rel=0;
        for(RelationEvaluationVO aux:t){
            if(flag){
                if(aux.isRelevant()){
                    rel++;
                }
            }
            else {
                if (!aux.isRelevant())
                    rel++;
            }
        }
        return rel;
    }

}
