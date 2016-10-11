package com.ontoeval.model.Access;

import com.ontoeval.model.RelationEvaluationVO;

import java.util.ArrayList;

/**
 * Created by dchavesf on 5/09/16.
 */
public interface RelationEvaluationDAO {


    ArrayList<RelationEvaluationVO> getEvaluatedRelations(String ontology, String user);
    ArrayList<RelationEvaluationVO> getEvaluatedRelations(String ontology);
    boolean insertRelations (ArrayList<RelationEvaluationVO> re);
    void close();
}
