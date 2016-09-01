package com.ontoeval.controller.services;

import com.ontoeval.model.Access.UserDAO;
import com.ontoeval.model.Access.UserImpl;
import com.ontoeval.model.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 1/09/16.
 */
public class UserHelper {
    private final HttpServletRequest request;
    private final UserDAO users;

    public UserHelper (HttpServletRequest request) throws SQLException, IOException{
        this.request = request;
        users = new UserImpl(UserImpl.CrearConexion());
    }

    public boolean insertUser(String email, String pass) throws  SQLException{
        UserVO user = new UserVO(email, pass);
        if(!users.insertUser(user))
            throw new SQLException("Error en UserHerlper");
        else
            return true;
    }
}
