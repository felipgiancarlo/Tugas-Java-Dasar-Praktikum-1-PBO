import java.util.Scanner;

public class App {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        try {
            if (!sc.hasNextLine()) return;
            
            // 1. Membaca dan Memvalidasi Jam Awal
            String timeStr = sc.nextLine().trim();
            if (!isValidTimeFormat(timeStr)) {
                System.out.println("Jam tidak valid");
                return;
            }
            
            String[] parts = timeStr.split(":");
            int startH = Integer.parseInt(parts[0]);
            int startM = Integer.parseInt(parts[1]);
            
            if (!isValidTimeValues(startH, startM)) {
                System.out.println("Jam tidak valid");
                return;
            }
            
            // 2. Mengkonversi jam awal ke total menit
            int baseMin = convertToMinutes(startH, startM);
            
            // 3. Memproses semua perintah penambahan/pengurangan waktu
            int deltaMin = processTimeShifts(sc);
            
            // 4. Menghitung dan mencetak hasil akhir
            calculateAndPrintResult(startH, startM, baseMin, deltaMin);
            
        } finally {
            sc.close(); // Best Practice: Mencegah kebocoran memori (memory leak)
        }
    }

    // ==============================================================================
    // SEPARATION OF CONCERN / MODULARISASI
    // ==============================================================================

    /**
     * Memvalidasi format teks harus sesuai pola Angka:Angka
     */
    private static boolean isValidTimeFormat(String timeStr) {
        return timeStr.matches("\\d+:\\d+");
    }

    /**
     * Memvalidasi rentang angka jam (0-23) dan menit (0-59)
     */
    private static boolean isValidTimeValues(int hours, int minutes) {
        return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
    }

    /**
     * Mengkonversi jam dan menit ke dalam total akumulasi menit dari tengah malam
     */
    private static int convertToMinutes(int hours, int minutes) {
        return (hours * 60) + minutes;
    }

    /**
     * Membaca perintah +N atau -N dan mengakumulasikan total pergeseran menit
     */
    private static int processTimeShifts(Scanner sc) {
        int deltaMin = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---")) break;
            
            if (line.matches("[+-]\\d+")) {
                deltaMin += Integer.parseInt(line);
            } else {
                System.out.println("Perintah tidak valid");
            }
        }
        return deltaMin;
    }

    /**
     * Menghitung kalkulasi matematika waktu dan mencetaknya ke layar
     */
    private static void calculateAndPrintResult(int startH, int startM, int baseMin, int deltaMin) {
        int finalTotalMin = baseMin + deltaMin;
        
        // Menggunakan Math.floorDiv untuk menghitung jumlah hari terlewati secara aman
        int days = Math.abs(Math.floorDiv(finalTotalMin, 1440));
        
        // Menggunakan Math.floorMod agar sisa menit selalu positif meskipun finalTotalMin negatif
        int finalModMin = Math.floorMod(finalTotalMin, 1440);
        
        int finalH = finalModMin / 60;
        int finalM = finalModMin % 60;
        
        // Memformat pergeseran waktu agar menampilkan tanda '+' jika lebih dari 0
        String formattedDelta = (deltaMin == 0) ? "0" : String.format("%+d", deltaMin);
        
        System.out.printf("Jam Awal: %02d:%02d\n", startH, startM);
        System.out.printf("Jam Akhir: %02d:%02d\n", finalH, finalM);
        System.out.println("Total Menit: " + formattedDelta);
        System.out.println("Pergantian Hari: " + days);
    }
}