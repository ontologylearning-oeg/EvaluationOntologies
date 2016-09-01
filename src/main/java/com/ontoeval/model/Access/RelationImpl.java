package com.ontoeval.model.Access;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.RelationVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dchavesf on 1/09/16.
 */
public class RelationImpl extends BaseDaoImpl<RelationVO, Integer> implements RelationDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval";
    private final Dao<RelationVO, Integer> relationDAO;


    public RelationImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RelationVO.class);
        relationDAO = DaoManager.createDao(connectionSource, RelationVO.class);
        TableUtils.createTableIfNotExists(connectionSource, RelationVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }

    public boolean insertRelation(RelationVO r){
        try{
            if(relationDAO.create(r)==0){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error en importar relaciones "+ex.getMessage());
            return false;
        }
        return true;
    }
    public boolean insertRelations(ArrayList<RelationVO> rs){
        for(RelationVO relation : rs){
            if(this.insertRelation(relation) == false){
                return false;
            }
        }
        return true;
    }
}
