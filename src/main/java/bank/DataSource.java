package bank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class DataSource {
  
  public static Connection connect()
  {
    String db_file="jdbc:sqlite:/workspaces/hands-on-java-3080245/resources/bank.db";
    Connection connection = null;
    
    try
    {
      connection = DriverManager.getConnection(db_file);
      //System.out.println("connected");
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return connection;
  }
  public static Customer getCustomer(String username)
  {
    String sql = "select * from customers where username = ?";
    //how does this work??? find out
    Customer customer = null;

    try(Connection connection = connect();
      PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery();)
      {
        customer = new Customer(
          resultSet.getInt("id"),
          resultSet.getString("name"),
          resultSet.getString("username"),
          resultSet.getString("password"),
          resultSet.getInt("account_id"));
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return customer;
  }
  public static Account getAccount(int id)
  {
    String sql = "select * from accounts where id = ?";
    Account account = null;
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      try (ResultSet resultSet = statement.executeQuery();) {
        account = new Account(
          resultSet.getInt("id"),
          resultSet.getString("type"),
          resultSet.getDouble("balance")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }
  public static void updateAccountBalance(int accountId, double balance)
  {
    String sql = "update accounts set balance = ? where id = ?";
    try
    ( Connection connection = connect();
      PreparedStatement statement=connection.prepareStatement(sql);
    )
    {
      statement.setDouble(1, balance);
      statement.setInt(2, accountId);
      statement.executeUpdate();
    }
    catch(SQLException e){
      e.printStackTrace();
    }
  }
  
}
