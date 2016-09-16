package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        OntologyVO ontology = (OntologyVO) session.getAttribute("ontology");
        ArrayList<TermEvaluationVO> terms = new ArrayList<>();
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
        ArrayList<TermVO> t = (ArrayList<TermVO>)request.getSession().getAttribute("terms");
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
                    ontology.setState("Eval Taxonomic Layer");
                    return "relations";
                }
                else{
                    return null;
                }
        }


    }

    private boolean termsForEval(ArrayList<TermVO> t){
        ArrayList<TermVO> tforeval = new ArrayList<>(); int i=0;
        if(t.size()>0) {
            while (i<9  && i<t.size()) {
                tforeval.add(t.get(i));
                i++;
            }
            request.getSession().setAttribute("termsUser", tforeval);
            request.getSession().setAttribute("terms", t);
            return true;
        }
        return false;
    }

    public ArrayList<TermVO> recuperarGS(OntologyVO o){
        return terms.loadGS(o.getName());
    }

    public ArrayList<TermVO> recuperar(String o){
        return terms.loadNormal(o);
    }

    public boolean checkControl(ArrayList<TermEvaluationVO> tevalu, OntologyVO ontology){
        ArrayList<TermVO> control = terms.loadControl(ontology.getName());
        Integer c=0;
        for(TermVO auxc: control){
            for(TermEvaluationVO auxt:tevalu){
                if(auxc.getWord().equals(auxt.getTerm())){
                    if(auxc.getRelevant()==auxt.isRelevant()){
                        c++;
                    }
                }
            }
        }
        if(control.size()>0 && (c/control.size())<0.7){
            evalTerms.deleteTerms(tevalu.get(0).getUser());
            return false;
        }
        else
            return true;
    }

    public boolean checkUser (OntologyVO o,UserVO u){
        ArrayList<TermEvaluationVO> tevalu = evalTerms.evaluatedTermsUser(o.getName(),u.getEmail());
        return checkControl(tevalu,o);
    }

    public void rellenarTermsBD(ArrayList<TermVO> t, ArrayList<TermEvaluationVO> teval){
        ArrayList<TermEvaluationVO> tevalaux = new ArrayList<>();
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
            Integer rel = countForRelavant(tevalaux);
            for(TermVO taux: t){
                if(taux.getWord().equals(tevalaux.get(0).getTerm())){
                    if(rel>2 && !taux.getControl())
                        taux.setGoldStandad(true);
                    taux.setYes(rel);
                    taux.setNo(5-rel);
                }
            }

            tevalaux = new ArrayList<>();
        }
        terms.updateTerms(t);
    }

    public boolean loadTerms(String text, String filename, String domain, String user){
        ArrayList<TermVO> termsaux = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        tokenizer.nextToken();tokenizer.nextToken();
        String term = tokenizer.nextToken();
        while(!term.equals("Taxonomic;;")){
            StringTokenizer tokenizer1 = new StringTokenizer(term,";");
            TermVO aux = new TermVO(tokenizer1.nextToken(),filename, domain,tokenizer1.nextToken(),tokenizer1.nextToken(),user);
            termsaux.add(aux);
            term = tokenizer.nextToken();
        }
        return terms.InsertTerms(termsaux);
    }

    private Integer countForRelavant(ArrayList<TermEvaluationVO> t){
        Integer rel=0;
        for(TermEvaluationVO aux:t){
            if(aux.isRelevant()){
                rel++;
            }
        }
        return rel;
    }


}
