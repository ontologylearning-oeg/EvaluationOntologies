package com.ontoeval.model.Access;

import com.ontoeval.model.UserEvalVO;

/**
 * Created by dchavesf on 27/09/16.
 */
public interface UserEvalDAO {

    boolean insert(UserEvalVO u);
    UserEvalVO check(String name, String ontology);
    Integer nUsers(String ontology);
}
