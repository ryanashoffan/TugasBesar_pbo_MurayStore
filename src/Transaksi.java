import java.util.InputMismatchException;
import java.util.Scanner;

public class Transaksi {

    public static void main(String[] args) {
        Config.connection();
        System.out.println("=============================================");
        System.out.println("|       SELAMAT DATANG DI MURAY STORE       |");
        System.out.println("=============================================");
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("\n=================== MENU ====================\n"
                        + "1. Registrasi\n"
                        + "2. Login\n"
                        + "3. Buat Tabel\n"
                        + "4. Tambah Transaksi\n"
                        + "5. Lihat RIWAYAT Transaksi\n"
                        + "6. Hapus RIWAYAT Transaksi\n"
                        + "7. Perbarui RIWAYAT Transaksi\n"
                        + "0. Keluar\n"
                        + "Pilih[1/2/3/4/5/0] : ");

                String pilihan = scanner.next();

                if (pilihan.equalsIgnoreCase("0")) {
                    System.out.println("Terima kasih!!");
                    break;
                }

                switch (pilihan) {
                    case "1":
                        View.register();
                        break;
                    case "2":
                        View.login();
                        break;
                    case "3":
                        View.createTable();
                        break;
                    case "4":
                        View.tambahData();
                        break;
                    case "5":
                        View.getAllData();
                        break;
                    case "6":
                        View.deleteData();
                        break;
                    case "7":
                        View.updateData();
                        break;
                    default:
                        System.out.println("Pilihan Salah!!");
                        break;
                }

            }
        } catch (InputMismatchException e) {
            System.out.println("Kesalahan Input, Tipe Data tidak sesuai!");
        }
        scanner.close();
    }

}
