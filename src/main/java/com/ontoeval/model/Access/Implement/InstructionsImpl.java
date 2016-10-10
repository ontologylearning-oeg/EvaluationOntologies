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

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchaves on 4/10/16.
 */
public class InstructionsImpl extends BaseDaoImpl<InstructionsVO, Integer> implements InstructionsDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval?useSSL=false";
    private final Dao<InstructionsVO, Integer> instructionDAO;


    public InstructionsImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, InstructionsVO.class);
        instructionDAO = DaoManager.createDao(connectionSource, InstructionsVO.class);
        TableUtils.createTableIfNotExists(connectionSource, InstructionsVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
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

}
