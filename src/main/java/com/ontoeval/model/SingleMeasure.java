package com.ontoeval.model;

/**
 * Created by dchavesf on 8/09/16.
 */
public class SingleMeasure {
    private String name;
    private Double measure;

    public SingleMeasure (String name, Double measure){
        this.name=name;
        this.measure=measure;
    }

    public SingleMeasure (){
        this.name="example";
        this.measure=0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMeasure() {
        return measure;
    }

    public void setMeasure(Double measure) {
        this.measure = measure;
    }
}
