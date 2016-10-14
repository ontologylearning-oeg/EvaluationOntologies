package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 1/09/16.
 */
@DatabaseTable(tableName = "Measure")
public class MeasureVO{
    @DatabaseField(columnName="Recall")
    private double recall;
    @DatabaseField(columnName="Precision")
    private double precision;
    @DatabaseField(columnName="F-Measure")
    private double fmeasure;
    @DatabaseField(columnName="Fleiss Kappa")
    private double fkappa;
    @DatabaseField(columnName="TR")
    private double trecall;
    @DatabaseField(columnName="TP")
    private double tprecision;
    @DatabaseField(columnName="TF")
    private double tfmeasure;
    @DatabaseField(columnName="TFleiss Kappa")
    private double tfkappa;
    @DatabaseField(columnName = "Ontology" , foreign = true)
    private OntologyVO ontology;

   public MeasureVO() {
        this.recall = 0.0;
        this.precision = 0.0;
        this.fmeasure = 0.0;
        this.fkappa=0.0;
        this.trecall = 0.0;
        this.tprecision = 0.0;
        this.tfmeasure = 0.0;
        this.tfkappa=0.0;
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

    public double getFkappa() { return fkappa; }

    public void setFkappa(double fkappa) { this.fkappa = fkappa; }

    public double getTfkappa() { return tfkappa; }

    public void setTfkappa(double tfkappa) { this.tfkappa = tfkappa; }

    public OntologyVO getOntology() {
        return ontology;
    }

    public void setOntology(OntologyVO ontology) {
        this.ontology = ontology;
    }
}
