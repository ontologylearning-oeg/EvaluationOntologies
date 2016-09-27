package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.MeasureDAO;
import com.ontoeval.model.Access.MeasureImpl;
import com.ontoeval.model.Access.OntologyImpl;

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
    private final static Integer evaluators=5;
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

    public String calcularResultados(OntologyVO o){
        MeasureVO measure = calculoLexico();
        measure.setName(o.getName());
        measure.setDomain(o.getDomain());
        measure.setUser(o.getUser());
        calculoTaxonomico(measure);
        measure.setFkappa(FleissKappa(true));
        measure.setTfkappa(FleissKappa(false));
        this.measure.insertMeasure(measure);
        return "./eval/results.jsp";
    }

    public void insertMeasures(OntologyVO o, MeasureVO m2){
        MeasureVO m;
        if(m2==null)
            m = measure.getMeasure(o);
        else
            m= m2;
        ArrayList<SingleMeasure> measures = new ArrayList<>();
        SingleMeasure aux = new SingleMeasure("Recall",m.getRecall());
        measures.add(aux);
        aux = new SingleMeasure("Precision",m.getPrecision());
        measures.add(aux);
        aux = new SingleMeasure("F-Measure",m.getFmeasure());
        measures.add(aux);
        aux = new SingleMeasure("Taxonomic Precision",m.getTprecision());
        measures.add(aux);
        aux = new SingleMeasure("Taxonomic Recall",m.getTrecall());
        measures.add(aux);
        aux = new SingleMeasure("Taxonomic F-Measure",m.getTfmeasure());
        measures.add(aux);
        aux = new SingleMeasure("FK",m.getFkappa());
        measures.add(aux);
        aux = new SingleMeasure("TFK",m.getTfkappa());
        measures.add(aux);
        request.getSession().setAttribute("measure",measures);
    }

    private MeasureVO calculoLexico(){
        MeasureVO m = new MeasureVO();
        Double relevant=0.0,gs=0.0,both=0.0;
        for(TermVO t: terms){
            if(t.isGoldStandad()){
                gs++;
            }
            if(t.getRelevant())
                relevant++;
            if(t.getRelevant() && t.isGoldStandad())
                both++;
        }
        m.setRecall(both/gs);
        m.setPrecision(both/relevant);
        m.setFmeasure((2*(m.getRecall()*m.getPrecision()))/(m.getRecall()+m.getPrecision()));
        return m;
    }

    private void calculoTaxonomico(MeasureVO m){
        ArrayList<RelationVO> learned = new ArrayList<>();
        ArrayList<RelationVO> goldstandard = new ArrayList<>();
        HashMap<String, ArrayList<String>> lhiterms = new HashMap<>();
        HashMap<String, ArrayList<String>> gshiterms = new HashMap<>();
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
        Double tp = taxonomicPrecision(termsgold, termsl, gshiterms, lhiterms);
        m.setTprecision(tp);
        Double tr = taxonomicPrecision(termsl,termsgold, lhiterms, gshiterms);
        m.setTrecall(tr);
        if(tp!=0 && tr!=0){
            Double tf = (2*tp*tr)/(tp+tr);
            m.setTfmeasure((2*m.getRecall()*tf)/(tf+m.getRecall()));
        }
        else
            m.setTfmeasure(0);
    }

    private ArrayList<String> constructHierachy(ArrayList<RelationVO> relation, HashMap<String, ArrayList<String>> terms){
        ArrayList<String> nterms = new ArrayList<>();
        String t=""; boolean flag=false,term1=true,term2=true;
        nterms.add(relation.get(0).getTerm1());
        nterms.add(relation.get(0).getTerm2());
        for(RelationVO r : relation){
            for(String n : nterms) {
                if (n.equals(r.getTerm1())) {
                    term1=false;
                }
                if (n.equals(r.getTerm2())) {
                    term2=false;
                }
            }
            if(term1==true){
                nterms.add(r.getTerm1());
            }
            if(term2==true){
                nterms.add(r.getTerm2());
            }
            term1=true;term2=true;
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

    private Double taxonomicPrecision(ArrayList<String> gs, ArrayList<String> learned, HashMap<String, ArrayList<String>> gsterms, HashMap<String, ArrayList<String>> lterms){
        ArrayList<String> commonTerms = new ArrayList<>();
        ArrayList<String> subsuperLearned, subsuperGS;
        Integer n=0; Double tp=0.0;

        for(String aux: gs) {
            if (learned.contains(aux)) {
                commonTerms.add(aux);
            }
        }
        for (String aux : commonTerms){
            subsuperGS = gsterms.get(aux);
            subsuperLearned = lterms.get(aux);
            for(int i=0;i<subsuperGS.size();i++){
                if(!learned.contains(subsuperGS.get(i))){
                    subsuperGS.remove(i);
                    i--;
                }
            }
            for(int i=0; i<subsuperLearned.size();i++){
                if(!gs.contains(subsuperLearned.get(i))){
                    subsuperLearned.remove(i);
                    i--;
                }
            }

            for(String auxGS: subsuperGS){
                if(subsuperLearned.contains(auxGS)){
                    n++;
                }
            }
            if(subsuperLearned.size()>0)
                tp=tp+(double)(n/subsuperLearned.size());
            n=0;
        }

        return tp/commonTerms.size();

    }

    private Double FleissKappa(boolean flag){
        Double pi = 0.0;
        Double pjYes =0.0, pjNo=0.0; Integer nrandomRel=0;
        if(flag){
            for(TermVO t : terms){
                pi += ((Math.pow(t.getYes(),2)+Math.pow(t.getNo(),2)-this.evaluators)/(this.evaluators*(this.evaluators-1)));
                pjNo +=t.getNo();
                pjYes +=t.getYes();
            }
            pjYes = pjYes / (this.evaluators*terms.size());
            pjNo = pjNo / (this.evaluators*terms.size());
            pi = pi / terms.size();
        }
        else{
            for(RelationVO t : relations){
                if(t.isRandom()){
                    nrandomRel++;
                    pi += ((Math.pow(t.getYes(),2)+Math.pow(t.getNo(),2)-this.evaluators)/(this.evaluators*(this.evaluators-1)));
                    pjNo +=t.getNo();
                    pjYes +=t.getYes();
                }
            }
            pjYes = pjYes / (this.evaluators*nrandomRel);
            pjNo = pjNo / (this.evaluators*nrandomRel);
            pi = pi / nrandomRel;
        }

        Double pj = Math.pow(pjNo,2)+Math.pow(pjYes,2);
        return  (pi-pj)/(1-pj);
    }

    public void changeRelevevance(Integer value, LexicalHelper h){
        OntologyVO o = (OntologyVO)request.getSession().getAttribute("ontology");
        this.setTerms(h.recuperar(o.getName()));
        MeasureVO m = measure.getMeasure(o);
        caluloLexico(m,value);
        insertMeasures(o,m);
    }

    private void caluloLexico(MeasureVO m, Integer value){
        Double gs=0.0,both=0.0;
        for(int i=0; i<terms.size();i++){
            if(i>=value)
                terms.get(i).setRelevant(false);

            if(terms.get(i).isGoldStandad()){
                gs++;
            }
            //falta el index aquí en el comprobación
            if(terms.get(i).getRelevant() && terms.get(i).isGoldStandad())
                both++;
        }
        m.setRecall(both/gs);
        m.setPrecision(both/value);
        m.setFmeasure((2*(m.getRecall()*m.getPrecision()))/(m.getRecall()+m.getPrecision()));

    }

}
