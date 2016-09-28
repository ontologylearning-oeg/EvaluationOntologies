package com.ontoeval.model.Access;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.UserEvalVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dchavesf on 27/09/16.
 */
public class UserEvalImpl extends BaseDaoImpl<UserEvalVO, Integer> implements UserEvalDAO {

    private static final String url = "jdbc:mysql://localhost/DrOntoEval?useSSL=false";
    private final Dao<UserEvalVO, Integer> userEvalDAO;

    public UserEvalImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, UserEvalVO.class);
        userEvalDAO = DaoManager.createDao(connectionSource, UserEvalVO.class);
        TableUtils.createTableIfNotExists(connectionSource, UserEvalVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }

    public boolean insert(UserEvalVO u){
        try{
            if(userEvalDAO.create(u)==0){
                return false;
            }
        }catch (SQLException e){
            System.out.println("Error en create "+e.getMessage());
        }
        return true;
    }


    public UserEvalVO check(String name, String ontology) {
        ArrayList<UserEvalVO> arrayu;
        HashMap<String, Object> map= new HashMap<>();
        map.put("user",name);
        map.put("ontology", ontology);
        try {
            arrayu = (ArrayList<UserEvalVO>) userEvalDAO.queryForFieldValuesArgs(map);
        }catch (SQLException e){
            System.out.println("Error en importar usuario "+e.getMessage());
            return null;
        }
        if(arrayu.size()==0){
            return null;
        }
        else{
            return arrayu.get(0);
        }
    }
}