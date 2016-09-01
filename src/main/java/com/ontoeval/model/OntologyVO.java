package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.StringTokenizer;

/**
 * Created by dchavesf on 1/09/16.
 */
@DatabaseTable(tableName = "Ontologies")
public class OntologyVO {
    @DatabaseField(columnName="noun",canBeNull=false, unique = true)
    private String name;

    public OntologyVO(String name) {
        StringTokenizer tokenizer = new StringTokenizer(name,".");
        this.name = tokenizer.nextToken();
    }

    public OntologyVO() {
        this.name = "example";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
