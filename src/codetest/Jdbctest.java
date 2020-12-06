package codetest;

import java.sql.*;

/**
 * Created by lengzefu on 2019/3/10.
 */
public class Jdbctest {
    public static void main(String[] args) {

        ResultSet resultSet = null;
        try {
            //1、加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //DriverManager.registerDriver(new com.mysq);
            //2、DriverManager获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1?characterEncoding=utf-8","root","root");
            //3、书写sql语句---string类型
            String sql = "select * from users where name = ?";
            //4、获取预处理statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //5、设置参数
            preparedStatement.setString(1, "Jack");
            //6、执行sql，结果放入结果集
            resultSet = preparedStatement.executeQuery();
            //7、遍历结果集
            while(resultSet.next()) {
                System.out.println(resultSet.getString("id") + " " + resultSet.getString("time"));
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //8、关闭数据库连接
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
