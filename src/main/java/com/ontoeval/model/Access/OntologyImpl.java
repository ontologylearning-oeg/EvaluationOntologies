package com.ontoeval.model.Access;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.OntologyVO;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 1/09/16.
 */
public class OntologyImpl extends BaseDaoImpl<OntologyVO, Integer> implements OntologyDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval";
    private final Dao<OntologyVO, Integer> ontoDAO;


    public OntologyImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, OntologyVO.class);
        ontoDAO = DaoManager.createDao(connectionSource, OntologyVO.class);
        TableUtils.createTableIfNotExists(connectionSource, OntologyVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }


    public boolean insertOntology(OntologyVO ontology){
        try{
            if(ontoDAO.create(ontology)==0){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error en importar ontologia "+ex.getMessage());
            return false;
        }
        return true;

    }
}
