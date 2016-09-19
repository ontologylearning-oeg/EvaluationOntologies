package com.ontoeval.controller.services;

import com.ontoeval.model.Access.*;
import com.ontoeval.model.AdminVO;
import com.ontoeval.model.OntologyVO;
import com.ontoeval.model.TermEvaluationVO;
import com.ontoeval.model.TermVO;

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
    private final TermEvaluationDAO evalTerms;
    private final OntologyDAO ontos;
    private AdminVO admin;

    public AdminHelper(HttpServletRequest request) throws SQLException, IOException {
        this.request=request;
        terms = new TermImpl(TermImpl.CrearConexion());
        ontos = new OntologyImpl(OntologyImpl.CrearConexion());
        evalTerms = new TermEvaluationImpl(TermEvaluationImpl.CrearConexion());
        admin = new AdminVO();
    }


    public boolean loadAdmin(String ontology){
        OntologyVO o = ontos.recuperarOntologias(ontology);
        ArrayList<TermVO> t = terms.loadTerms(o.getName());
        ArrayList<TermEvaluationVO> teval = evalTerms.evaluatedTerms(o.getName());
        yesandno(t,(ArrayList<TermEvaluationVO>)teval.clone());
        admin.setTerms(t);
        admin.setNterms(terms.loadNormal(o.getName()).size());
        admin.setNevaluations(t.size()*5);
        admin.setNcontrol(terms.loadControl(o.getName()).size());
        admin.setNevaluators(countEvaluators(teval));
        admin.setComplete((double)teval.size()/(double)admin.getNevaluations());
        request.getSession().setAttribute("admin",admin);
        return true;
    }

    private Integer countEvaluators(ArrayList<TermEvaluationVO> teval){
        ArrayList<String> users= new ArrayList<>();
        users.add(teval.get(0).getUser());
        for(TermEvaluationVO t: teval){
            if(!users.contains(t.getUser())){
                users.add(t.getUser());
            }
        }
        return users.size();
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

}
