package com.ontoeval.model.Access;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.UserVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dchavesf on 1/09/16.
 */
public class UserImpl extends BaseDaoImpl<UserVO, Integer> implements UserDAO  {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval?autoReconnect=true&useSSL=false";
    private final Dao<UserVO, Integer> userDAO;


    public UserImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, UserVO.class);
        userDAO = DaoManager.createDao(connectionSource, UserVO.class);
        TableUtils.createTableIfNotExists(connectionSource, UserVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }

    public boolean insertUser(UserVO u){
        try{
            if(userDAO.create(u)==0){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error en importar usuario "+ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean checkUser(UserVO u) {
        ArrayList<UserVO> arrayu = new ArrayList<UserVO>();
        try {
            arrayu = (ArrayList<UserVO>) userDAO.queryForEq("email", u.getEmail());
        }catch (SQLException e){
            System.out.println("Error en importar usuario "+e.getMessage());
            return false;
        }
        if(arrayu.size()==0){
            return false;
        }
        else{
            return true;
        }
    }
}
