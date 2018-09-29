package com.liuqiang.dbcp.pool;

import org.apache.commons.pool.PoolableObjectFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPoolFactory implements PoolableObjectFactory{
    private DataSource dataSource;
    public ConnectionPoolFactory(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public Object makeObject() throws Exception {
        return dataSource.getConnection();
    }

    public void destroyObject(Object o) throws Exception {
        ((Connection)o).close();
    }

    public boolean validateObject(Object o) {
        try{
            Statement statement = ((Connection)o).createStatement();
            ResultSet rs = statement.executeQuery(" select 1 from DUAL");
            while(rs.next())
                return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void activateObject(Object o) throws Exception {
        try{
            Statement statement = ((Connection)o).createStatement();
            statement.execute(" select 1 from DUAL ");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void passivateObject(Object o) throws Exception {

    }
}
