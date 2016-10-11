package com.ontoeval.model.Access;

import com.ontoeval.model.UserVO;

import java.util.ArrayList;

/**
 * Created by dchavesf on 1/09/16.
 */
public interface UserDAO {

    boolean insertUser(UserVO u);
    boolean checkUser(UserVO u);
    void close();
}
