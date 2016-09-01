package com.ontoeval.model.Access;

import com.ontoeval.model.RelationVO;

import java.util.ArrayList;

/**
 * Created by dchavesf on 1/09/16.
 */
public interface RelationDAO {

    public boolean insertRelation(RelationVO r);
    public boolean insertRelations(ArrayList<RelationVO> rs);
}
