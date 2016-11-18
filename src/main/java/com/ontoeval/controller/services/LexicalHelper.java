package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.*;
import com.ontoeval.model.Access.Implement.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by dchavesf on 5/09/16.
 */
public class LexicalHelper {
    private final HttpServletRequest request;
    private final TermDAO terms;
    private final TermEvaluationDAO evalTerms;
    private final UserEvalDAO userEval;
    private final OntologyDAO ont;

    public LexicalHelper (HttpServletRequest request) throws SQLException, IOException {
        this.request = request;
        terms = new TermImpl(EncryptConnection.CrearConexion());
        evalTerms = new TermEvaluationImpl(EncryptConnection.CrearConexion());
        userEval = new UserEvalImpl(EncryptConnection.CrearConexion());
        ont = new OntologyImpl(EncryptConnection.CrearConexion());
    }

    public void close(){
        terms.close();
        evalTerms.close();
        userEval.close();
        ont.close();
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
            TermEvaluationVO t = new TermEvaluationVO(ontology,user,term,eval);
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
        this.sumAnswersOfEvalTerms(terms,t);
        if(termsForEval(t))
            return "./eval/lexical.jsp";
        else{
            //comprobar el usuario
            String p= comprobarLexical(ontology,user);
            if(p!=null && p.equals("relations")){
                ont.updateOntology(ontology);
                return null;
            }
            else{
                return p;
            }
        }

    }

    public String comprobarLexical(OntologyVO ontology, UserVO user){
        ArrayList<TermVO> t = terms.loadTerms(ontology.getName());
        ArrayList<TermEvaluationVO> tevalu = evalTerms.evaluatedTermsUser(ontology.getName(),user.getEmail());
        ArrayList<TermEvaluationVO> teval = evalTerms.evaluatedTerms(ontology.getName());
        UserEvalVO u = userEval.check(user.getEmail(), ontology.getName());
        if(t.size() > (teval.size()/5)) { //si no se ha  completado la evaluacion lexica
            if (t.size()!=tevalu.size() && u==null) { //aun le falta al usuario terminos por evaluar
                for(TermEvaluationVO taux: tevalu){
                    for (int i=0; i<t.size();i++){
                        if(taux.getTerm().equals(t.get(i).getWord())){
                            t.remove(i);
                            break;
                        }
                    }
                }
                this.sumAnswersOfEvalTerms(teval,t);
                termsForEval(t);
                return "./eval/lexical.jsp";
            }
            else{
                if(u==null){
                    u = new UserEvalVO(user,ontology,checkControl(tevalu,ontology));
                    userEval.insert(u);
                }
                if(u.isValid())
                    return null;
                else
                    return "notUser";
            }
        }
        else{
            if(u==null){
                u = new UserEvalVO(user,ontology,checkControl(tevalu,ontology));
                userEval.insert(u);
            }

            if(u.isValid()){
                rellenarTermsBD(t,teval);
                ontology.setState("Eval Taxonomic Layer");
                return "relations";
            }
            else{
                return "notUser";
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
        Integer c=0, ncontrol=0;
        for(TermVO auxc: control){
            for(TermEvaluationVO auxt:tevalu){
                if(auxc.getWord().equals(auxt.getTerm())){
                    if(auxc.getRelevant()==auxt.isRelevant()){
                        c++;
                    }
                    ncontrol++;
                }
            }
        }
        if((ncontrol!=control.size()) || (control.size()>0 && ((double)c/(double)control.size())<0.7)){
            evalTerms.deleteTerms(tevalu.get(0).getUser().getEmail());
            return false;
        }
        else
            return true;


    }

    public boolean checkUser (OntologyVO o,UserVO u){
        UserEvalVO ueval = userEval.check(u.getEmail(),o.getName());
        if(ueval==null){
            return false;
        }
        else{
            return ueval.isValid();
        }
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

    public boolean loadTerms(String text, OntologyVO o){
        ArrayList<TermVO> termsaux = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        tokenizer.nextToken();tokenizer.nextToken();tokenizer.nextToken();
        String term = tokenizer.nextToken();
        while(!term.equals("Taxonomic;;")){
            StringTokenizer tokenizer1 = new StringTokenizer(term,";");
            TermVO aux = new TermVO(o, tokenizer1.nextToken(),tokenizer1.nextToken(),tokenizer1.nextToken());
            termsaux.add(aux);
            term = tokenizer.nextToken();
        }
        if(termsaux.size()>0)
            return terms.InsertTerms(termsaux);
        else
            return false;
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

    private void sumAnswersOfEvalTerms(ArrayList<TermEvaluationVO> t, ArrayList<TermVO> terms){
        ArrayList<String> evalTerms = new ArrayList<>();
        for(int i=0; i<t.size();i++){
            int j=i+1; int count=0;
            while(j<t.size()){
                if(t.get(i).getTerm().equals(t.get(j).getTerm())){
                    count++;
                }
                j++;
            }
            if(count==4){
                evalTerms.add(t.get(i).getTerm());
            }
        }

        for(int i=0; i<terms.size();i++){
            for(String s: evalTerms){
                if(s.equals(terms.get(i).getWord())){
                    terms.remove(i);
                    i--;
                    break;
                }
            }
        }
    }


}
