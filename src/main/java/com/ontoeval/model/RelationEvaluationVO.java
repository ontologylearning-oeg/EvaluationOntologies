package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 5/09/16.
 */
@DatabaseTable(tableName = "TaxonomicEvaluation")
public class RelationEvaluationVO extends OntologyVO {
    @DatabaseField(columnName="term1",canBeNull=false, uniqueCombo = true)
    private String term1;
    @DatabaseField(columnName="term2",canBeNull=false, uniqueCombo = true)
    private String term2;
    @DatabaseField(columnName="User",canBeNull=false, uniqueCombo = true)
    private String user;
    @DatabaseField(columnName="isRelevant")
    private boolean isRelevant;

}
