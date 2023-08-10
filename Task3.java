package Internship;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Task3 {

    public static void main(String[] args) throws ClassNotFoundException {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String Url = "jdbc:mysql://localhost:3306/";
            String userName = "root";
            String password = "Iteg@123";
            Connection con = DriverManager.getConnection(Url, userName, password);
            if (con.isClosed()) {
                System.out.println("connection is closed");
            } else {
                System.out.println("connection is created");
            }
            Statement stmt = con.createStatement();

            // database creation
            DatabaseCreation(con, stmt);

            // Table Creation
            tableCreation(con, stmt);

            Scanner sc = new Scanner(System.in);
            System.out.println("1. Create New Account\n2. Credit\n3. Debit\n4. Check Balance");
            System.out.print("Enter number :");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    CreateNewAccount(con);
                    break;
                case 2:
                    credit(con);
                    break;
                case 3:
                    debit(con);
                    break;
                case 4:
                    Balance(con);
                    break;
                default:
                    System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DatabaseCreation(Connection conn, Statement stmtt) throws SQLException {
        String dbName = "banking";
        String sql1 = "Create Database If Not Exists " + dbName;
        stmtt = conn.createStatement();
        stmtt.execute(sql1);
        System.out.println("Database is created");
    }

    public static void tableCreation(Connection conn, Statement stmtt) {
        try {
            String tableName = "Account_data";
            String sql2 = "use banking";
            stmtt = conn.createStatement();
            stmtt.execute(sql2);
            String sql3 = "Create Table If not exists " + tableName +

                    "(ID INT AUTO_INCREMENT," +

                    "First_Name VARCHAR(100) NOT NULL," +

                    "Last_Name VARCHAR(100) NOT NULL," +

                    "Mobile_No VARCHAR(20) NOT NULL CHECK(Mobile_No<=100000000000), " +

                    "Email_ID VARCHAR(100) NOT NULL UNIQUE," +

                    "Age INT(100) CHECK(Age>=18)," +

                    "Addres VARCHAR(1000) NOT NULL," +

                    "City VARCHAR(1000) NOT NULL," +

                    "Pincode INT(6) NOT NULL," +

                    "Account_No Bigint(20) NOT NULL UNIQUE," +

                    "Account_Pin INT(4) NOT NULL UNIQUE," +

                    "Balance Bigint(20) NOT NULL  CHECK(Balance>=500), " +

                    "PRIMARY KEY(ID))";
            stmtt = conn.createStatement();
            stmtt.execute(sql3);
            System.out.println("table is created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CreateNewAccount(Connection conn) {
        try {
            Statement stmtt = conn.createStatement();
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Your First Name : ");
            String s1 = sc.nextLine();

            System.out.print("\nEnter Your Last Name : ");
            String s3 = sc.nextLine();

            System.out.print("\nEnter your Mobile NO. : ");
            String s4 = sc.nextLine();

            System.out.print("\nEnter your Email ID : ");
            String s5 = sc.nextLine();

            System.out.print("\nEnter your Age : ");
            int s7 = sc.nextInt();

            System.out.println();
            String s9 = sc.nextLine();

            System.out.print("Enter your Address : ");
            String s10 = sc.nextLine();

            System.out.print("\nEnter your City : ");
            String s11 = sc.nextLine();

            System.out.print("\nEnter your PIN Code : ");
            int s12 = sc.nextInt();

            long min = 10000000;
            long max = 999999999;

            long num1 = (int) (Math.random() * (max - min + 1) + min);

            System.out.print("\nEnter your Account PIN : ");
            int s13 = sc.nextInt();

            System.out.print("\nEnter your Balance : ");
            long s14 = sc.nextLong();

            String sql4 = "use banking";
            stmtt = conn.createStatement();
            stmtt.execute(sql4);
            String sql5 = "INSERT INTO Account_data(First_Name,Last_Name,Mobile_No,Email_ID,Age,Addres,City,Pincode,Account_No,Account_Pin,Balance) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql5);
            ps.setString(1, s1);
            ps.setString(2, s3);
            ps.setString(3, s4);
            ps.setString(4, s5);
            ps.setInt(5, s7);
            ps.setString(6, s10);
            ps.setString(7, s11);
            ps.setInt(8, s12);
            ps.setLong(9, num1);
            ps.setInt(10, s13);
            ps.setLong(11, s14);
            int i = ps.executeUpdate();

            if (i != 0) {
                System.out.print("\n**Congratulation Account is get Created**");
            } else {
                System.out.println("Can not create account");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public static void credit(Connection conn) {
    try {

      Statement stmtt = conn.createStatement();

      Scanner sc = new Scanner(System.in);

      System.out.print("Enter your First Name : ");
      String s1 = sc.nextLine();

      System.out.print("Enter your Last Name : ");
      String s3 = sc.nextLine();

      System.out.print("Enter your Account No. : ");
      long s4 = sc.nextLong();

      System.out.print("Enter your PIN : ");
      int s5 = sc.nextInt();

      String sql2 = "Select * From Account_data Where First_Name = ?  And Last_Name = ? And Account_No = ? and Account_Pin = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql2);
      pstmt.setString(1, s1);
      pstmt.setString(2, s3);
      pstmt.setLong(3, s4);
      pstmt.setInt(4, s5);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        System.out.print(" Your Current Balance : " + rs.getInt(12));
      }
      System.out.print("How much u want to transfer : ");
      long s6 = sc.nextLong();

      System.out.print("Enter reciver Account No. : ");
      long s10 = sc.nextLong();

      if (s10 != s4) {
        System.out.print("Now Current your Balance is :");
        long bal;
        long update_bal;
        ResultSet rs2 = pstmt.executeQuery();
        while (rs2.next()) {
          bal = rs2.getInt(12);
          update_bal = bal - s6;
          String sql3 = "use banking";
          stmtt = conn.createStatement();
          stmtt.execute(sql3);
          String sql4 = "Update Account_data Set Balance = " + update_bal + " Where Account_No =" + s4;
          stmtt = conn.createStatement();
          stmtt.execute(sql4);
        }
        String sql5 = "Select * From Account_data where Account_No=?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql5);
        pstmt2.setLong(1, s4);
        ResultSet rs3 = pstmt2.executeQuery();
        while (rs3.next()) {
          System.out.print(rs3.getLong(12));
        }
    } 
}catch (SQLException e) {
      e.printStackTrace();
    }
}
  

  public static void debit(Connection conn) {
    try {
      Statement stmtt = conn.createStatement();
      Scanner sc = new Scanner(System.in);

      System.out.println("Enter your First Name: ");
      String s1 = sc.nextLine();

      System.out.println("Enter your Last Name: ");
      String s3 = sc.nextLine();

      System.out.println("Enter your Account No. : ");
      Long s4 = sc.nextLong();

      System.out.println("Enter your PIN : ");
      int s5 = sc.nextInt();

      System.out.println("How much u want to transfer : ");
      long s6 = sc.nextLong();

      String sql1 = "use banking";
      stmtt = conn.createStatement();
      stmtt.execute(sql1);
      String sql13 = "Select * From Account_data Where Account_No = ? ";
      PreparedStatement pstmt13 = conn.prepareStatement(sql13);
      pstmt13.setLong(1, s4);
      long bal2;
      long update_bal2;
      ResultSet rs14 = pstmt13.executeQuery();
      while (rs14.next()) {
        bal2 = rs14.getInt(12);
        update_bal2 = bal2 + s6;
        String sql3 = "use banking";
        stmtt = conn.createStatement();
        stmtt.execute(sql3);
        String sql4 = "Update Account_data Set Balance = " + update_bal2 + " Where Account_No =" + s4;
        stmtt = conn.createStatement();
        stmtt.execute(sql4);
      }
      String sql2 = "Select * From Account_data Where Account_No = ? and Account_Pin = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql2);
      pstmt.setLong(1, s4);
      pstmt.setInt(2, s5);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        System.out.print("\nNow your current Balance is : " + rs.getLong(12));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void Balance(Connection conn) {
    try {
      Statement stmtt = conn.createStatement();
      Scanner sc = new Scanner(System.in);

      System.out.println("Enter your Fist Name : ");
      String s1 = sc.nextLine();

      System.out.println("Enter your Last Name : ");
      String s3 = sc.nextLine();

      System.out.println("Enter your Account No. : ");
      Long s4 = sc.nextLong();

      System.out.println("Enter your PIN : ");
      int s5 = sc.nextInt();

      String sql1 = "use banking";
      stmtt = conn.createStatement();
      stmtt.execute(sql1);

      String sql2 = "Select * From Account_data Where First_Name = ?  And Last_Name=? And Account_No = ? and Account_Pin = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql2);
      pstmt.setString(1, s1);
      pstmt.setString(2, s3);
      pstmt.setLong(3, s4);
      pstmt.setInt(4, s5);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        // Display values
        System.out.print("ID : " + rs.getInt(1));
        System.out.print("\nFirst_Name : " + rs.getString(2));
        System.out.print("\nLast_Name : " + rs.getString(3));
        System.out.print("\nAccount_NO : " + rs.getLong(10));
        System.out.print("\nAccount_Pin : " + rs.getInt(11));
        System.out.print("\nBalance : " + rs.getLong(12));
      }
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

