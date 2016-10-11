package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.MeasureDAO;
import com.ontoeval.model.MeasureVO;
import com.ontoeval.model.OntologyVO;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dchavesf on 5/09/16.
 */
public class MeasureImpl extends BaseDaoImpl<MeasureVO, Integer> implements MeasureDAO {
    private final Dao<MeasureVO, Integer> measureDAO;
    private ConnectionSource connectionSource;

    public MeasureImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, MeasureVO.class);
        this.connectionSource=connectionSource;
        measureDAO = DaoManager.createDao(connectionSource, MeasureVO.class);
        TableUtils.createTableIfNotExists(connectionSource, MeasureVO.class);
    }

    @Override
    public boolean insertMeasure(MeasureVO v) {
        try{
            if(measureDAO.create(v)==0)
                return false;
        }catch (SQLException e){
            System.out.println("Error en MeasureImpl/insertMeasure "+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public MeasureVO getMeasure(OntologyVO o) {
        try{
            return measureDAO.queryForEq("Ontology",o.getName()).get(0);
        }catch (SQLException e){
            System.out.println("Error en MeasureImpl/getMeasure "+e.getMessage());
            return null;
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
