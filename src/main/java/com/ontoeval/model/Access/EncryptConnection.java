package com.ontoeval.model.Access;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchaves on 11/10/16.
 */
public class EncryptConnection {


    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        /*String envUser = System.getenv("EVAL_USER");
        String envPwd = System.getenv("EVAL_PWD");
        String envHost = System.getenv("EVAL_DB");
*/
        String envUser = "root";
        String envPwd = "david";
        String envHost = "localhost:3306";
        //mysql://localhost/DrOntoEval?useSSL=false
        String jdbcUrl = "jdbc:mysql://"+envHost+"/DrOntoEval?useSSL=false";

        ConnectionSource connectionSource = new JdbcConnectionSource(jdbcUrl,envUser,envPwd);
        return connectionSource;
    }
}
