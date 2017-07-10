package com.ontoeval.model;


import java.util.ArrayList;

/**
 * Created by dchavesf on 16/09/16.
 */
public class AdminVO {
    private String ontology;
    private Integer nevaluators;
    private Integer nterms;
    private Integer ncontrol;
    private Integer nevaluations;
    private Double complete;
    private Integer tevaluators;
    private ArrayList<TermVO> terms;
    private ArrayList<RelationVO> relations;

    public AdminVO(Integer nevaluators, Integer nterms, Integer ncontrol, Integer nevaluations, Double complete, ArrayList<TermVO> terms) {
        this.nevaluators = nevaluators;
        this.nterms = nterms;
        this.ncontrol = ncontrol;
        this.nevaluations = nevaluations;
        this.complete = complete;
        this.terms = terms;
    }

    public AdminVO() {
        terms= new ArrayList<>();
    }

    public Integer getNevaluators() {
        return nevaluators;
    }

    public void setNevaluators(Integer nevaluators) {
        this.nevaluators = nevaluators;
    }

    public Integer getNterms() {
        return nterms;
    }

    public void setNterms(Integer nterms) {
        this.nterms = nterms;
    }

    public Integer getNcontrol() {
        return ncontrol;
    }

    public void setNcontrol(Integer ncontrol) {
        this.ncontrol = ncontrol;
    }

    public Integer getNevaluations() {
        return nevaluations;
    }

    public void setNevaluations(Integer nevaluations) {
        this.nevaluations = nevaluations;
    }

    public Double getComplete() {
        return complete;
    }

    public void setComplete(Double complete) {
        this.complete = complete;
    }

    public ArrayList<TermVO> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<TermVO> terms) {
        for(TermVO t: terms){
            this.terms.add(t);
        }
    }

    public String getOntology() {
        return ontology;
    }

    public void setOntology(String ontology) {
        this.ontology = ontology;
    }

    public Integer getTevaluators() {
        return tevaluators;
    }

    public void setTevaluators(Integer tevaluators) {
        this.tevaluators = tevaluators;
    }

    public ArrayList<RelationVO> getRelations() {
        return relations;
    }

    public void setRelations(ArrayList<RelationVO> relations) {
        this.relations = relations;
    }
}
