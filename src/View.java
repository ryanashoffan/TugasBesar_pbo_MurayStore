import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class View {
  public static Scanner input = new Scanner(System.in);
  private static HashMap<String, String> users = new HashMap<>();
  private static HashMap<String, String> captchas = new HashMap<>();
  private static final int CAPTCHA_LENGTH = 6;

  public static void createTable() {
    // Memanggil method dari kelas Config untuk menciptakan tabel
    Config.createTransaksiTable();
    System.out.println("Transaction Table Has Been Successfully Created .");
    System.out.println("\nClick Enter to Continue...");
    try {
      System.in.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void tambahData() {
    Date HariSekarang = new Date();

    System.out.print("input invoiceNumber: ");
    String invoiceNumber = input.nextLine();

    System.out.print("imput customerName: ");
    String customerName = input.nextLine();

    System.out.print("imput NoHP: ");
    String NoHP = input.nextLine();

    System.out.print("input Address: ");
    String address = input.nextLine();

    System.out.print("input itemName: ");
    String itemName = input.nextLine();

    System.out.print("input price: ");
    long price = input.nextLong();
    input.nextLine();

    System.out.print("input quantity: ");
    int quantity = input.nextInt();
    input.nextLine();

    Faktur invoice = new Faktur(invoiceNumber, customerName, itemName, price, quantity, NoHP, address);
    SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

    System.out.println("=============================================");
    System.out.println("|               Toko Muray Store             |");
    System.out.println("=============================================");
    System.out.println("Tanggal\t\t: " + ft.format(HariSekarang));
    System.out.println("Waktu\t\t: " + format.format(HariSekarang));
    System.out.println("Nomor Invoice\t: " + invoice.getInvoiceNumber());
    System.out.println("================= Data Pelanggan ==============");
    System.out.println("Nama Pelanggan\t: " + invoice.getCustomerName());
    System.out.println("Nomor HP\t: " + invoice.getnoHP());
    System.out.println("Alamat\t\t: " + invoice.getaddress());
    System.out.println("================= Data Barang ================");
    System.out.println("Nama Barang\t: " + invoice.getItemName());
    System.out.println("Harga\t\t: " + invoice.getPrice());
    System.out.println("Jumlah\t\t: " + invoice.getQuantity());
    System.out.println("Total Pembayaran: " + invoice.calculateTotal());
    System.out.println("===================== Kasir ==================");
    String myString = "Ryan Ashofan";
    String uppercaseString = myString.toUpperCase();
    System.out.println("Kasir\t\t: " + uppercaseString);
    System.out.println("=============================================");

    if (Config.tambahData(invoiceNumber, customerName, itemName, price, quantity, NoHP, address)) {
      System.out.println("Transaksi berhasil disimpan!");
    } else {
      System.out.println("Gagal menyimpan transaksi!!");
    }

  }

  public static void getAllData() {
    // Pesan header

    System.out.println("=============================================");
    System.out.println("|              RIWAYAT TRANSAKSI            |");
    System.out.println("=============================================");
    // Tampilkan data semua barang
    System.out.println(Config.getAllData());
  }

  public static void deleteData() {

    System.out.println("=============================================");
    System.out.println("|          HAPUS RIWAYAT TRANSAKSI          |");
    System.out.println("=============================================");
    System.out.print("Masukkan Nomor Invoice: ");
    int invoiceNumber = input.nextInt();

    if (Config.deleteData(invoiceNumber)) {
      System.out.println("Transaksi berhasil dihapus!");
      getAllData();
    } else {
      System.out.println("Gagal menghapus transaksi!!");
    }
  }

  public static void updateData() {
    System.out.println("=============================================");
    System.out.println("|        PERBARUI RIWAYAT TRANSAKSI         |");
    System.out.println("=============================================");
    System.out.print("Masukkan Nomor Invoice : ");
    int invoiceNumber = input.nextInt();

    System.out.println("\nUbah data barang\n");
    System.out.print("Nama Barang (Biarkan kosong jika tidak ingin mengubah data) : ");
    input.nextLine(); // membersihkan newline yang tersisa
    String itemName = input.nextLine();

    System.out.print("Jumlah (masukkan 0 jika tidak ingin mengubah data) : ");
    int quantity = input.nextInt();

    System.out.print("Harga (masukkan 0 jika tidak ingin mengubah data) : ");
    int price = input.nextInt();

    if (Config.updateData(invoiceNumber, itemName, quantity, price)) {
      System.out.println("Perbarui Berhasil!!");
      View.getAllData();
    } else {
      System.out.println("Gagal Memperbarui");
    }
  }

  public static void register() {
    System.out.print("Masukkan username: ");
    String username = input.nextLine();
    System.out.print("Masukkan password: ");
    String password = input.nextLine();

    if (users.containsKey(username)) {
      System.out.println("Username sudah digunakan.");
    } else {
      users.put(username, password);
      System.out.println("Registrasi berhasil!!");
    }
  }

  public static void login() {
    System.out.print("Masukkan username: ");
    String username = input.nextLine();
    System.out.print("Masukkan password: ");
    String password = input.nextLine();

    if (users.containsKey(username) && users.get(username).equals(password)) {
      String captcha = generateCaptcha();
      captchas.put(username, captcha);
      System.out.println("CAPTCHA : " + captcha);
      System.out.print("Masukkan CAPTCHA untuk verifikasi: ");
      String inputCaptcha = input.nextLine();

      if (inputCaptcha.equalsIgnoreCase(captchas.get(username))) {
        System.out.println("Login berhasil!!");
      } else {
        System.out.println("Verifikasi CAPTCHA gagal!");
      }
    } else {
      System.out.println("Username atau password salah!");
    }
  }

  public static String generateCaptcha() {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder captcha = new StringBuilder();
    Random rnd = new Random();

    for (int i = 0; i < CAPTCHA_LENGTH; i++) {
      captcha.append(chars.charAt(rnd.nextInt(chars.length())));
    }
    return captcha.toString();
  }

}
