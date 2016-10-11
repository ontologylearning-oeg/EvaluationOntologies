package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.InstructionsDAO;
import com.ontoeval.model.InstructionsVO;
import com.ontoeval.model.OntologyVO;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dchaves on 4/10/16.
 */
public class InstructionsImpl extends BaseDaoImpl<InstructionsVO, Integer> implements InstructionsDAO {
    private final Dao<InstructionsVO, Integer> instructionDAO;
    private ConnectionSource connectionSource;

    public InstructionsImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, InstructionsVO.class);
        this.connectionSource=connectionSource;
        instructionDAO = DaoManager.createDao(connectionSource, InstructionsVO.class);
        TableUtils.createTableIfNotExists(connectionSource, InstructionsVO.class);
    }

    @Override
    public boolean insertIntruccion(InstructionsVO v) {
        try{
            if(instructionDAO.create(v)==0)
                return false;
        }catch (SQLException e){
            System.out.println("Error en insertInstructions "+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public InstructionsVO getMeasure(OntologyVO o) {
        try{
            return instructionDAO.queryForEq("Ontology",o.getName()).get(0);
        }catch (SQLException e){
            System.out.println("Error en InstructionsImpl/getMeasure "+e.getMessage());
            return new InstructionsVO();
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
