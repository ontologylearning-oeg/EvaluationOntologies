package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.StringTokenizer;

/**
 * Created by dchavesf on 1/09/16.
 */
@DatabaseTable(tableName = "Ontologies")
public class OntologyVO {
    @DatabaseField(columnName="Ontology",canBeNull=false)
    private String name;
    @DatabaseField(columnName="Domain", canBeNull = false)
    private String domain;

    public OntologyVO(String name, String domain) {
        StringTokenizer tokenizer = new StringTokenizer(name,".");
        this.name = tokenizer.nextToken();
        this.domain=domain;
    }

    public OntologyVO() {
        this.name = "example";
        this.domain = "example";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() { return domain; }

    public void setDomain(String domain) { this.domain = domain; }
}
