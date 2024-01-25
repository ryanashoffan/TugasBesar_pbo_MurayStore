import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Config {

  private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/muray_store";
  private static final String USER = "root";
  private static final String PASS = "";

  private static Connection connect;
  private static Statement statement;
  private static ResultSet resultData;

  // ini adalah method static connection
  public static void connection()
  
  {
    // method untuk melakukan koneksi ke database
    try {
      // registrasi driver yang akan dipakai
      Class.forName(JDBC_DRIVER);

      // buat koneksi ke database
      connect = DriverManager.getConnection(DB_URL, USER, PASS);

    } catch (Exception e) {
      // kalo ada error saat koneksi
      // maka tampilkan errornya
      e.printStackTrace();
    }

  }

  public static String getAllData()
  {
    Config.connection();

    // isi nilai default dari variabel data
    String data = "Maaf data tidak ada";

    try {

      // buat object statement yang ambil dari koneksi
      statement = connect.createStatement();

      // query select all data from database
      String query = "SELECT invoiceNumber, customerName, Contact, Address,itemName, price, quantity FROM transaksi";

      // eksekusi query-nya
      resultData = statement.executeQuery(query);

      // set variabel data jadi null
      data = "";

      // looping pengisian variabel data
      while( resultData.next() ){
        data += "invoiceNumber : " + resultData.getInt("invoiceNumber") + "| customerName : " + resultData.getString("customerName")+ "| Contact : " + resultData.getString("Contact") + "| Address : " + resultData.getString("Address") + "| itemName : " + resultData.getString("itemName") + "| price : " + resultData.getDouble("price") + "| quantity : " + resultData.getInt("quantity") + "\n";
      }
      // close statement dan connection
      statement.close();
      connect.close();


    } catch (Exception e) {
      e.printStackTrace();
    }

    return data;

  }
public static void createTransaksiTable() {
    Config.connection();

    try {
        statement = connect.createStatement();

        // Membuat query untuk menciptakan tabel transaksi
        String query = "CREATE TABLE IF NOT EXISTS transaksi (" +
                       "invoiceNumber VARCHAR(50) PRIMARY KEY," + // Misalnya invoiceNumber sebagai primary key
                       "customerName VARCHAR(100) NOT NULL," + 
                       "Contact VARCHAR(15)," + 
                       "Address VARCHAR(255)," + 
                       "itemName VARCHAR(100) NOT NULL," + 
                       "price DOUBLE," + 
                       "quantity INT" + 
                       ")";

        // Eksekusi query untuk menciptakan tabel
        statement.execute(query);

    } catch (SQLException e) {
        System.out.println("Error saat menciptakan tabel: " + e.getMessage());
    } finally {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            System.out.println("Error saat menutup statement atau koneksi: " + e.getMessage());
        }
    }
}


public static boolean tambahData( String invoiceNumber, String customerName, String itemName, double price, int quantity, String Contact, String Address )
  {
    Config.connection();
    boolean data = false;

    try {

      statement = connect.createStatement();

      String query = "INSERT INTO transaksi VALUES ('" + invoiceNumber + "', '" + customerName + "', '" + Contact + "', '" + Address + "', '" + itemName + "', " + price + ", " + quantity + ")";


      if( !statement.execute(query) ){
        data = true;
      }


      // close statement dan koneksi
      statement.close();
      connect.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }

    return data;
  }

public static boolean deleteData( int invoiceNumber )
  {
    connection();
    boolean data = false;

    try {
      
      statement = connect.createStatement();

      String query = "DELETE FROM transaksi WHERE invoiceNumber = " + invoiceNumber;
      //# String query = "UPDATE transaksi SET isActive = '0' WHERE no_faktur = " + invoiceNumber;

      if( !statement.execute(query) ){
        data = true;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return data;
  }



public static boolean updateData( int invoiceNumber, String itemName, int quantity, int price )
  {

    Config.connection();
    boolean data = false;

    try {

      statement = connect.createStatement();

      String queryCek = "SELECT * FROM transaksi WHERE invoiceNumber = " + invoiceNumber;

      resultData = statement.executeQuery(queryCek);
      // siapin variabel untuk menampung data pada fild satu row
      String itemCheck = "";
      int stokCek = 0, hargaCek = 0;

      while( resultData.next() ){
        itemCheck = resultData.getString("itemName");
        stokCek = resultData.getInt("quantity");
        hargaCek = resultData.getInt("price");
      }

      // validasi jika yang diisi diconsole kosong
      if( !itemName.equalsIgnoreCase("") ){
        itemCheck = itemName;
      }
      if( quantity != 0 ){
        stokCek = quantity;
      }
      if( price != 0 ){
        hargaCek = price;
      }

      String queryUpdate = "UPDATE transaksi SET itemName = '" + itemCheck + "', quantity = " + stokCek + ", price = " + hargaCek + " WHERE invoiceNumber = " + invoiceNumber ;
      
      if( !statement.execute(queryUpdate) ){
        data = true;
      }else{
        data = false;
      }

      // close statement dan close koneksi
      statement.close();
      connect.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }


    return data;
  }
}



