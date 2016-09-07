package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.*;

import javax.management.openmbean.ArrayType;
import javax.servlet.ServletContext;
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

    public String saveTerms(String text){
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        ServletContext context = request.getSession().getServletContext();
        UserVO user = (UserVO) context.getAttribute("user");
        OntologyVO ontology = (OntologyVO) context.getAttribute("ontology");
        ArrayList<TermEvaluationVO> terms = new ArrayList<TermEvaluationVO>();
        while(tokenizer.hasMoreTokens()){
            StringTokenizer tokenizer1 = new StringTokenizer(tokenizer.nextToken(),";");
            String term = tokenizer1.nextToken();
            String aux = tokenizer1.nextToken();
            boolean eval;
            if(aux.equals("yes")){
                eval = true;
            }
            else
                eval=false;
            TermEvaluationVO t = new TermEvaluationVO(ontology.getName(),ontology.getDomain(),term,user.getEmail(),eval);
            terms.add(t);
        }
        evalTerms.insertTerms(terms);
        ArrayList<TermVO> t = (ArrayList<TermVO>)request.getSession().getServletContext().getAttribute("terms");
        for(TermEvaluationVO taux: terms){
            for (int i=0; i<t.size();i++){
                if(taux.getTerm().equals(t.get(i).getWord())){
                    t.remove(i);
                    break;
                }
            }
        }
        if(termsForEval(t))
            return "./eval/lexical.jsp";
        else
            return null;
    }

    public String comprobarLexical(OntologyVO ontology, UserVO user){
        ArrayList<TermVO> t = terms.loadTerms(ontology.getName());
        ArrayList<TermEvaluationVO> tevalu = evalTerms.evaluatedTermsUser(ontology.getName(),user.getEmail());
        ArrayList<TermEvaluationVO> teval = evalTerms.evaluatedTerms(ontology.getName());
        if(t.size()!=(teval.size()/5)) { //si no se ha  completado la evaluacion lexica
            if (t.size() != tevalu.size()) { //aun le falta al usuario terminos por evaluar
                for(TermEvaluationVO taux: tevalu){
                    for (int i=0; i<t.size();i++){
                        if(taux.getTerm().equals(t.get(i).getWord())){
                            t.remove(i);
                            break;
                        }
                    }
                }
                if(termsForEval(t))
                    return "./eval/lexical.jsp";
                else
                    return null;
            }
            else{
                return null;
            }
        }
        else{
            if(checkControl(tevalu,ontology)){
                rellenarTermsBD(t,teval);
                return "relations";
            }
            else{
                return null;
            }
        }
    }

    private boolean termsForEval(ArrayList<TermVO> t){
        ArrayList<TermVO> tforeval = new ArrayList<TermVO>(); int i=0;
        if(t.size()>0) {
            while (i<9  && i<t.size()) {
                tforeval.add(t.get(i));
                i++;
            }
            request.getSession().getServletContext().setAttribute("termsUser", tforeval);
            request.getSession().getServletContext().setAttribute("terms", t);
            return true;
        }
        return false;
    }

    public ArrayList<TermVO> recuperarRelevants(OntologyVO o){
        return terms.loadRelevant(o.getName());
    }

    public ArrayList<TermVO> recuperar(String o){
        return terms.loadTerms(o);
    }

    public boolean checkControl(ArrayList<TermEvaluationVO> tevalu, OntologyVO ontology){
        ArrayList<TermVO> control = terms.loadControl(ontology.getName());
        Integer c=0;
        for(TermVO auxc: control){
            for(TermEvaluationVO auxt:tevalu){
                if(auxc.getWord().equals(auxt.getTerm())){
                    if(auxc.isRelevant()==auxt.isRelevant()){
                        c++;
                    }
                }
            }
        }
        if((c/control.size())<0.7){
            evalTerms.deleteTerms(tevalu.get(0).getUser());
            return false;
        }
        else
            return true;
    }

    public void rellenarTermsBD(ArrayList<TermVO> t, ArrayList<TermEvaluationVO> teval){
        ArrayList<TermEvaluationVO> tevalaux = new ArrayList<TermEvaluationVO>();
        while(teval.size()!=0){
            TermEvaluationVO aux = teval.get(0);
            for(int i=1; i<teval.size();i++){
                if(aux.getTerm().equals(teval.get(i).getTerm())){
                    tevalaux.add(teval.get(i));
                    teval.remove(teval.get(i));
                    i--;
                }
            }
            tevalaux.add(aux);
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
