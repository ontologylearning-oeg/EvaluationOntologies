package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.TermDAO;
import com.ontoeval.model.Access.TermEvaluationDAO;
import com.ontoeval.model.Access.TermEvaluationImpl;
import com.ontoeval.model.Access.TermImpl;

import javax.management.openmbean.ArrayType;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by dchavesf on 5/09/16.
 */
public class LexicalHelper {
    private final HttpServletRequest request;
    private final TermDAO terms;
    private final TermEvaluationDAO evalTerms;

    public LexicalHelper (HttpServletRequest request) throws SQLException, IOException {
        this.request = request;
        terms = new TermImpl(TermImpl.CrearConexion());
        evalTerms = new TermEvaluationImpl(TermEvaluationImpl.CrearConexion());
    }

    public String comprobarLexical(OntologyVO ontology, UserVO user){
        ArrayList<TermVO> t = terms.loadTerms(ontology.getName());
        ArrayList<TermEvaluationVO> tevalu = evalTerms.evaluatedTermsUser(ontology.getName(),user.getEmail());
        ArrayList<TermEvaluationVO> teval = evalTerms.evaluatedTerms(ontology.getName());
        if(t.size()!=(teval.size()*5)) { //si no se ha  completado la evaluacion lexica
            if (t.size() != tevalu.size()) { //aun le falta al usuario terminos por evaluar
                for (TermVO aux : t) {
                    for (TermEvaluationVO auxeval : tevalu) {
                        if (aux.getWord().equals(auxeval.getTerm())) {
                            t.remove(aux);
                        }
                    }
                }
                request.getSession().getServletContext().setAttribute("terms", t);
                return "./eval/lexical.jsp";
            }
            else{
                return "./eval/index.jsp";
            }
        }
        else{
            rellenarTermsBD(t,teval);
            return null;
        }
    }

    public ArrayList<TermVO> recuperarRelevants(OntologyVO o){
        return terms.loadRelevant(o.getName());
    }

    public ArrayList<TermVO> recuperar(String o){
        return terms.loadTerms(o);
    }

    public void rellenarTermsBD(ArrayList<TermVO> t, ArrayList<TermEvaluationVO> teval){
        ArrayList<TermEvaluationVO> tevalaux = new ArrayList<TermEvaluationVO>();
        while(teval.size()!=0){
            TermEvaluationVO aux = teval.get(0);
            for(int i=1; i<teval.size();i++){
                if(aux.getTerm().equals(teval.get(i).getTerm())){
                    tevalaux.add(teval.get(i));
                    teval.remove(teval.get(i));
                }
            }
            teval.remove(0);
            if(countForRelavant(tevalaux)){
                for(TermVO taux: t){
                    if(taux.getWord().equals(tevalaux.get(0).getTerm())){
                        taux.setRelevant(true);
                    }
                }
            }
            tevalaux = new ArrayList<TermEvaluationVO>();
        }
        terms.updateTerms(t);
    }




    public boolean loadTerms(String text, String filename, String domain){
        ArrayList<TermVO> termsaux = new ArrayList<TermVO>();
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        tokenizer.nextToken();tokenizer.nextToken();
        String term = tokenizer.nextToken();
        while(!term.equals("Taxonomic;")){
            StringTokenizer tokenizer1 = new StringTokenizer(term,";");
            TermVO aux = new TermVO(tokenizer1.nextToken(),filename, domain,tokenizer1.nextToken());
            termsaux.add(aux);
            term = tokenizer.nextToken();
        }
        return terms.InsertTerms(termsaux);
    }

    private boolean countForRelavant(ArrayList<TermEvaluationVO> t){
        Integer rel=0;
        for(TermEvaluationVO aux:t){
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
