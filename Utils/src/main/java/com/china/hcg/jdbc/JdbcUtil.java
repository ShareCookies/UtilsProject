package com.china.hcg.jdbc;

import java.sql.*;

/**
 * 纯java代码实现的Jdbc工具，简化jdbc操作。
 * <p>
 *
 * jdbc查询使用方式：
 *  1. query(String quetySql)方法中传入查询sql语句
 *  2. 重写queryOverride方法，在该方法中书写对结果集每行数据的处理代码。
 *      注：queryOverride能接收到查询结果集的每行数据，因为while(rs.next()){this.queryOverride(rs);}。
 *
 * jdbc增删改使用方式：
 *  1. update(String updateSql)方法中传入增删改类型的sql语句即可。
 * </p>
 * @author hecaigui
 * @date 2020-7-16
 * @description 纯java代码实现的Jdbc工具，简化jdbc操作。
 *
 */
public  class JdbcUtil {
     String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://ip:端口/zjgy-hsf?characterEncoding=utf8&useSSL=false";
    String user = "root";
    String password = "root";

    public JdbcUtil(String driver, String url, String user, String password){
        if (driver!=null){
            this.driver = driver;
        }
        if (url!=null){
            this.url = url;
        }
        if (user!=null){
            this.user = user;
        }
        if (password!=null){
            this.password = password;
        }
    }
    public  void query(String quetySql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.注册驱动
            Class.forName(this.driver);
            //2.获取数据库连接
            String url = this.url;
            String user = this.user;
            String password = this.password;
            conn = DriverManager.getConnection(url, user, password);
            //关闭事务的自动提交，开始事务
            //conn.setAutoCommit(false);
            //3.定义SQL语句构架
            String sql = quetySql;
            //4.进行SQL语句的预编译
            ps = conn.prepareStatement(sql);
            //5.进行赋值
            //ps.setString(1, "MANAGER");
            //6.执行SQL语句
            rs = ps.executeQuery();
            //7.处理查询结果集
            while(rs.next()){
                this.queryResultHandleOverride(rs);
            }
            //提交事务
            //conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                //事务的回滚
//                //conn.rollback();
//            } catch (SQLException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
        }finally{
            //关闭资源
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public void queryResultHandleOverride(ResultSet lastRowRS) throws SQLException{
        //lastRowRS.geta
        System.out.println(lastRowRS);
    }

    public  Integer update(String updateSql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer updateaSum = null;
        try {
            //1.注册驱动
            Class.forName(this.driver);
            //2.获取数据库连接
            String url = this.url;
            String user = this.user;
            String password = this.password;
            conn = DriverManager.getConnection(url, user, password);
            //关闭事务的自动提交，开始事务
            //conn.setAutoCommit(false);
            //3.定义SQL语句构架
            String sql = updateSql;
            //4.进行SQL语句的预编译
            ps = conn.prepareStatement(sql);
            //5.进行赋值
            //ps.setString(1, "MANAGER");
            //6.执行SQL语句
            //rs = ps.executeQuery();
            //insert,update delete语句
            updateaSum = ps.executeUpdate(sql);

            //7.处理查询结果集
//            while(rs.next()){
//
//
//
//            }
            //提交事务
            //conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                //事务的回滚
//                //conn.rollback();
//            } catch (SQLException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
        }finally{
            //关闭资源
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return updateaSum;
    }

    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.210.186:3306/zjgy_real?characterEncoding=utf8&useSSL=false";
        String user = "root";
        String password = "root";

        //todo:数据源方式


        JdbcUtil alterTableQuery = new JdbcUtil(driver,url,user,password);
        alterTableQuery.query("select * from EGOV_NOTICE limit 1");
    }

}
