package com.ontoeval.controller.services;

import com.ontoeval.model.Access.Implement.EncryptConnection;
import com.ontoeval.model.Access.UserDAO;
import com.ontoeval.model.Access.Implement.UserImpl;
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
        users = new UserImpl(EncryptConnection.CrearConexion());
    }

    public void close(){
        users.close();
    }

    public boolean insertUser(String email, String pass, String signup){
        UserVO user = new UserVO(email, pass);
        if(signup!=null){
            if(!users.insertUser(user)) {
                return false;
            }
            else{
                request.getSession().setAttribute("user",user);
                return true;
            }
        }
        else{
            if(!users.checkUser(user)){
                return false;
            }
            else{
                request.getSession().setAttribute("user",user);
                return true;
            }

        }


    }
}
