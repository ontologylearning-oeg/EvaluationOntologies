package com.ontoeval.model;

import java.util.HashMap;

/**
 * Created by dachafra on 11/05/16.
 */

public class Measure {
    private HashMap<String,Double> measures;
    private GoldStandardVO gs;
    private OntologyVO ont;

    public HashMap<String, Double> getMeasures() {
        return measures;
    }

    public void setMeasures(HashMap<String, Double> measures) {
        this.measures = measures;
    }

    public double TaxonomicRecall(){
        return 0.0;
    }

    public double TaxonomicPrecision(){
        return 0.0;
    }

    public double TaxonomicF(){
        return 0.0;
    }

    public double TaxonomicF2(){
        return 0.0;
    }

    public double LexicalPrecision(){
        return 0.0;
    }

    public double LexicalRecall(){
        return 0.0;
    }






}
