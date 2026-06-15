import com.uinar.simnilai.dao.NilaiDAOImpl;
import com.uinar.simnilai.service.NilaiService;
import com.uinar.simnilai.ui.MenuUtama;
import com.uinar.simnilai.util.DatabaseConnection;

/**
 * Entry point aplikasi SIMNILAI.
 * Merakitkan semua komponen (Dependency Injection manual).
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Menghubungkan ke database...");
        try {
            // Verifikasi koneksi awal
            DatabaseConnection.getInstance();

            // Rakit komponen (manual Dependency Injection)
            NilaiDAOImpl dao = new NilaiDAOImpl();
            NilaiService service = new NilaiService(dao);
            MenuUtama menu = new MenuUtama(service);

            // Jalankan aplikasi utama
            menu.jalankan();

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            System.err.println("Pastikan MySQL berjalan dan konfigurasi DB benar.");
        }
    }
}