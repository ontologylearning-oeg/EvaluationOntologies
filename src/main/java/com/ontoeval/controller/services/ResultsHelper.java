package com.ontoeval.controller.services;

import com.ontoeval.model.Access.MeasureDAO;
import com.ontoeval.model.Access.MeasureImpl;
import com.ontoeval.model.MeasureVO;
import com.ontoeval.model.RelationVO;
import com.ontoeval.model.TermVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dchavesf on 5/09/16.
 */
public class ResultsHelper {
    private HttpServletRequest request;
    private MeasureDAO measure;
    private ArrayList<TermVO> terms;
    private ArrayList<RelationVO> relations;

    public ResultsHelper(HttpServletRequest request) throws SQLException, IOException{
        this.request=request;
        terms= null;
        relations=null;
        measure = new MeasureImpl(MeasureImpl.CrearConexion());
    }



    public void setTerms(ArrayList<TermVO> terms) {
        this.terms = terms;
    }

    public void setRelations(ArrayList<RelationVO> relations) {
        this.relations = relations;
    }

    public String calcularResultados(){
        MeasureVO measure = calculoLexico();
        calculoTaxonomico(measure);
        request.getSession().getServletContext().setAttribute("measure",measure);
        return "./eval/results.jsp";
    }

    private MeasureVO calculoLexico(){
        MeasureVO m = new MeasureVO();
        Integer totalSize = terms.size();
        Integer relevant=0;
        for(TermVO t: terms){
            if(t.isRelevant()){
                relevant++;
            }
        }
        m.setRecall(relevant/relevant);
        m.setPrecision(relevant/totalSize);
        m.setFmeasure((2*(m.getRecall()*m.getPrecision()))/(m.getRecall()+m.getPrecision()));
        return m;
    }

    private void calculoTaxonomico(MeasureVO m){
        ArrayList<RelationVO> learned = new ArrayList<RelationVO>();
        ArrayList<RelationVO> goldstandard = new ArrayList<RelationVO>();
        HashMap<String, ArrayList<String>> lhiterms = new HashMap<String, ArrayList<String>>();
        HashMap<String, ArrayList<String>> gshiterms = new HashMap<String, ArrayList<String>>();
        for(RelationVO r: relations){
            if(r.isGS()) {
                goldstandard.add(r);
            }
            if(!r.isRandom()){
                learned.add(r);
            }
        }
        ArrayList<String> termsgold=constructHierachy(goldstandard,gshiterms);
        ArrayList<String> termsl=constructHierachy(learned,lhiterms);


    }

    private ArrayList<String> constructHierachy(ArrayList<RelationVO> relation, HashMap<String, ArrayList<String>> terms){
        ArrayList<String> nterms = new ArrayList<String>();
        String t=""; boolean flag=false;
        for(RelationVO r : relation){
            for(String n : nterms) {
                if (!n.equals(r.getTerm1())) {
                    nterms.add(r.getTerm1());
                }
                if (!n.equals(r.getTerm2())) {
                    nterms.add(r.getTerm2());
                }
            }
        }

        for(RelationVO r: relation){
            for(String n : nterms){
                if (n.equals(r.getTerm1())) {
                    t = r.getTerm2();
                    flag=true;
                }
                if (n.equals(r.getTerm2())) {
                    t = r.getTerm1();
                    flag=true;
                }
                if(flag==true) {
                    if (terms.containsKey(n)) {
                        terms.get(n).add(t);
                    } else {
                        terms.put(n, new ArrayList<String>());
                        terms.get(n).add(t);
                    }
                    flag=false;
                }
            }
        }
        return nterms;

    }





}
