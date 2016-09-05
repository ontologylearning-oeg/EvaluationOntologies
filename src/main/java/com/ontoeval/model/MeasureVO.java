package com.ontoeval.model;

/**
 * Created by dchavesf on 1/09/16.
 */
public class MeasureVO extends OntologyVO {
    private double recall;
    private double precision;
    private double fmeasure;
    private double trecall;
    private double tprecision;
    private double tfmeasure;

    public MeasureVO(String name, String domain, double recall, double precision, double fmeasure, double trecall, double tprecision, double tfmeasure) {
        super(name,domain);
        this.recall = recall;
        this.precision = precision;
        this.fmeasure = fmeasure;
        this.trecall = trecall;
        this.tprecision = tprecision;
        this.tfmeasure = tfmeasure;
    }

    public MeasureVO() {
        super("example","example");
        this.recall = 0.0;
        this.precision = 0.0;
        this.fmeasure = 0.0;
        this.trecall = 0.0;
        this.tprecision = 0.0;
        this.tfmeasure = 0.0;
    }

    public double getRecall() {
        return recall;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getFmeasure() {
        return fmeasure;
    }

    public void setFmeasure(double fmeasure) {
        this.fmeasure = fmeasure;
    }

    public double getTrecall() {
        return trecall;
    }

    public void setTrecall(double trecall) {
        this.trecall = trecall;
    }

    public double getTprecision() {
        return tprecision;
    }

    public void setTprecision(double tprecision) {
        this.tprecision = tprecision;
    }

    public double getTfmeasure() {
        return tfmeasure;
    }

    public void setTfmeasure(double tfmeasure) {
        this.tfmeasure = tfmeasure;
    }
}
