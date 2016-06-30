package com.ontoeval.model;

import java.util.ArrayList;

/**
 * Created by dachafra on 11/05/16.
 */

public class ModelVO {

    private String name;
    private String type;
    private ArrayList<TermVO> terms;
    private ArrayList<String> relations;
    //private ArrayList<Double> evalExperts;


    public ModelVO(String name, ArrayList<TermVO> terms, ArrayList<String> relations) {
        this.name = name+"GS";
        this.terms = terms;
        this.relations = relations;
    }

    public ModelVO(String name) {
        this.name = name+"GS";
        this.terms = new ArrayList<TermVO>();
        this.relations = new ArrayList<String>();
    }

    public ModelVO() {
        this.name = "ExampleGS";
        this.terms = new ArrayList<TermVO>();
        this.relations = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TermVO> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<TermVO> terms) {
        this.terms = terms;
    }

    public ArrayList<String> getRelations() {
        return relations;
    }

    public void setRelations(ArrayList<String> relations) {
        this.relations = relations;
    }
}
