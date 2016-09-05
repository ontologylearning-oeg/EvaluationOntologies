package com.ontoeval.model.Access;

import com.ontoeval.model.RelationVO;

import java.util.ArrayList;

/**
 * Created by dchavesf on 1/09/16.
 */
public interface RelationDAO {

    boolean insertRelation(RelationVO r);
    boolean insertRelations(ArrayList<RelationVO> rs);
    boolean checkRandomRelations();
    ArrayList<RelationVO> getRandomRelations(String ontology);
    ArrayList<RelationVO> getNormalRelations(String ontology);
    boolean updateRelations(ArrayList<RelationVO> r);

}
