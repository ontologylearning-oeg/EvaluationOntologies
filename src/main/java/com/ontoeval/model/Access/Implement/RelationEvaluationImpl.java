package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.RelationEvaluationDAO;
import com.ontoeval.model.RelationEvaluationVO;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by dchavesf on 5/09/16.
 */
public class RelationEvaluationImpl extends BaseDaoImpl<RelationEvaluationVO, Integer> implements RelationEvaluationDAO {
    private final Dao<RelationEvaluationVO, Integer> relationEvalDAO;
    private ConnectionSource connectionSource;

    public RelationEvaluationImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RelationEvaluationVO.class);
        this.connectionSource=connectionSource;
        relationEvalDAO = DaoManager.createDao(connectionSource, RelationEvaluationVO.class);
        TableUtils.createTableIfNotExists(connectionSource, RelationEvaluationVO.class);
    }

    public ArrayList<RelationEvaluationVO> getEvaluatedRelations(String ontology, String user) {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Ontology",ontology);
        m.put("User", user);
        try{
            return (ArrayList<RelationEvaluationVO>)relationEvalDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en getEvaluatedRelations/RelationEvaluationImpl "+e.getMessage());
            return null;
        }
    }

    public ArrayList<RelationEvaluationVO> getEvaluatedRelations(String ontology) {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Ontology",ontology);
        try{
            return (ArrayList<RelationEvaluationVO>)relationEvalDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en getEvaluatedRelations/RelationEvaluationImpl "+e.getMessage());
            return null;
        }
    }

    public boolean insertRelations(ArrayList<RelationEvaluationVO> re) {
        for(RelationEvaluationVO r: re){
            try {
                if (relationEvalDAO.create(r) == 0) {
                    return false;
                }
            }catch (SQLException e){
                System.out.println("Error en insertRelations/RelationEvaluationImpl "+e.getMessage());
                return false;
            }
        }
        return true;
    }


    public void close(){
        try {
            connectionSource.close();
        } catch (SQLException e) {
            System.out.println("Error en close "+e.getMessage());
        }
    }
}
