package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.UserDAO;
import com.ontoeval.model.UserVO;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dchavesf on 1/09/16.
 */
public class UserImpl extends BaseDaoImpl<UserVO, Integer> implements UserDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval?useSSL=false";
    private final Dao<UserVO, Integer> userDAO;
    private ConnectionSource connectionSource;


    public UserImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, UserVO.class);
        this.connectionSource=connectionSource;
        userDAO = DaoManager.createDao(connectionSource, UserVO.class);
        TableUtils.createTableIfNotExists(connectionSource, UserVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }

    public boolean insertUser(UserVO u){
        UserVO v = new UserVO(u.getEmail(),DigestUtils.md5Hex(u.getPassword()));
        try{
            if(userDAO.create(v)==0){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error en insertUser/UserImpl "+ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean checkUser(UserVO u) {
        ArrayList<UserVO> arrayu;
        String encrypt = DigestUtils.md5Hex(u.getPassword());
        HashMap<String, Object> map= new HashMap<>();
        map.put("email",u.getEmail());
        map.put("pass", encrypt);
        try {
            arrayu = (ArrayList<UserVO>) userDAO.queryForFieldValuesArgs(map);
        }catch (SQLException e){
            System.out.println("Error en checkuser/UserImpl "+e.getMessage());
            return false;
        }
        if(arrayu.size()==0){
            return false;
        }
        else{
            return true;
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
