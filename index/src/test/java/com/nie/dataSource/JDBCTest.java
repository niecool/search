package com.nie.dataSource;

import java.sql.*;

public class JDBCTest {


    public static void main(String[] args) throws ClassNotFoundException,SQLException {
        //jdbc:mysql://主机名或IP抵制：端口号/数据库名?useUnicode=true&characterEncoding=UTF-8&useSSL=true
        String URL = "jdbc:mysql://152.136.70.24:3306/search";
        String USER = "root";
        String PASSWORD = "@Chengyea";
        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获得数据库链接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select count(*) as num from product");
        //4.处理数据库的返回结果(使用ResultSet类)
        while (rs.next()) {
            System.out.println(rs.getString("num"));
        }
        //关闭资源
        rs.close();
        st.close();
        conn.close();
    }
}
