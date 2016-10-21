package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.UserEvalDAO;
import com.ontoeval.model.UserEvalVO;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by dchavesf on 27/09/16.
 */
public class UserEvalImpl extends BaseDaoImpl<UserEvalVO, Integer> implements UserEvalDAO {

    private static final String url = "jdbc:mysql://localhost/DrOntoEval?useSSL=false";
    private final Dao<UserEvalVO, Integer> userEvalDAO;
    private ConnectionSource connectionSource;

    public UserEvalImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, UserEvalVO.class);
        this.connectionSource=connectionSource;
        userEvalDAO = DaoManager.createDao(connectionSource, UserEvalVO.class);
        TableUtils.createTableIfNotExists(connectionSource, UserEvalVO.class);
    }

    public boolean insert(UserEvalVO u){
        try{
            if(userEvalDAO.create(u)==0){
                return false;
            }
        }catch (SQLException e){
            System.out.println("Error en insert/UserEvalImpl "+e.getMessage());
        }
        return true;
    }


    public UserEvalVO check(String name, String ontology) {
        ArrayList<UserEvalVO> arrayu;
        HashMap<String, Object> map= new HashMap<>();
        map.put("User",name);
        map.put("Ontology", ontology);
        try {
            arrayu = (ArrayList<UserEvalVO>) userEvalDAO.queryForFieldValuesArgs(map);
        }catch (SQLException e){
            System.out.println("Error en check/UserEvalImpl "+e.getMessage());
            return null;
        }
        if(arrayu.size()==0){
            return null;
        }
        else{
            return arrayu.get(0);
        }
    }

    @Override
    public Integer nUsers(String ontology) {
        HashMap<String, Object> map= new HashMap<>();
        map.put("Ontology",ontology);
        map.put("Valid", false);
        try {
            return (userEvalDAO.queryForFieldValues(map)).size();
        }catch (SQLException e){
            System.out.println("Error en nUsers/UserEvalImpl "+e.getMessage());
            return 0;
        }
    }

    public void close(){
        try {
            connectionSource.close();
        } catch (SQLException e) {
            System.out.println("Error en close "+e.getMessage());
        }
    }
}
