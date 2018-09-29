package com.liuqiang.dbcp.pool;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionPoolTest {
    public static void main(String[] args) throws Exception{
        System.out.println("初始化数据源");
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setUser("root");
        dataSource.setPassword("");

        System.out.println("创建数据库连接池");
        ConnectionPoolFactory factory = new ConnectionPoolFactory(dataSource);
        GenericObjectPool pool = new GenericObjectPool(factory);
        Connection conn = (Connection)pool.borrowObject();
        System.out.println("当前数据库连接池的容量" + pool.getNumActive());
        System.out.println("获取一个数据库连接");
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT name FROM USER");
        while (rs.next()){
            String skuName = rs.getString(1);
            System.out.println("从数据库中得到一条记录的值:" + skuName);
        }
        rs.close();
        statement.close();
        System.out.println("将对象返还给连接池");
        pool.returnObject(conn);
    }
}
