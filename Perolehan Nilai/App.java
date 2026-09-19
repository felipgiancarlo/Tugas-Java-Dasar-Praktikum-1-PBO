import java.util.Scanner;
import java.util.Locale;

public class App {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            int bobotPA = Integer.parseInt(scanner.nextLine().trim());
            int bobotT = Integer.parseInt(scanner.nextLine().trim());
            int bobotK = Integer.parseInt(scanner.nextLine().trim());
            int bobotP = Integer.parseInt(scanner.nextLine().trim());
            int bobotUTS = Integer.parseInt(scanner.nextLine().trim());
            int bobotUAS = Integer.parseInt(scanner.nextLine().trim());
            
            if (bobotPA + bobotT + bobotK + bobotP + bobotUTS + bobotUAS != 100) {
                System.out.println("Total bobot harus 100");
                return;
            }
            
            int totBobotPA = 0, totBobotT = 0, totBobotK = 0, totBobotP = 0, totBobotUTS = 0, totBobotUAS = 0;
            int totSkorPA = 0, totSkorT = 0, totSkorK = 0, totSkorP = 0, totSkorUTS = 0, totSkorUAS = 0;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equals("---")) break;
                
                String[] parts = line.split("\\|");
                if (parts.length != 3) {
                    System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                    continue;
                }
                
                try {
                    String simbol = parts[0].trim();
                    int bobotItem = Integer.parseInt(parts[1].trim());
                    int skorItem = Integer.parseInt(parts[2].trim());
                    
                    skorItem = Math.max(0, Math.min(skorItem, bobotItem));
                    
                    switch (simbol) {
                        case "PA": totBobotPA += bobotItem; totSkorPA += skorItem; break;
                        case "T": totBobotT += bobotItem; totSkorT += skorItem; break;
                        case "K": totBobotK += bobotItem; totSkorK += skorItem; break;
                        case "P": totBobotP += bobotItem; totSkorP += skorItem; break;
                        case "UTS": totBobotUTS += bobotItem; totSkorUTS += skorItem; break;
                        case "UAS": totBobotUAS += bobotItem; totSkorUAS += skorItem; break;
                        default: System.out.println("Simbol tidak dikenal"); break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                }
            }
            
            double kPA = hitungKontribusi(totSkorPA, totBobotPA, bobotPA);
            double kT = hitungKontribusi(totSkorT, totBobotT, bobotT);
            double kK = hitungKontribusi(totSkorK, totBobotK, bobotK);
            double kP = hitungKontribusi(totSkorP, totBobotP, bobotP);
            double kUTS = hitungKontribusi(totSkorUTS, totBobotUTS, bobotUTS);
            double kUAS = hitungKontribusi(totSkorUAS, totBobotUAS, bobotUAS);
            
            int pPA = hitungPersentase(totSkorPA, totBobotPA);
            int pT = hitungPersentase(totSkorT, totBobotT);
            int pK = hitungPersentase(totSkorK, totBobotK);
            int pP = hitungPersentase(totSkorP, totBobotP);
            int pUTS = hitungPersentase(totSkorUTS, totBobotUTS);
            int pUAS = hitungPersentase(totSkorUAS, totBobotUAS);

            double nilaiAkhir = kPA + kT + kK + kP + kUTS + kUAS;
            
            // Pembulatan ke 2 angka desimal untuk menghindari bug floating point
            nilaiAkhir = Math.round(nilaiAkhir * 100.0) / 100.0;
            
            String grade = tentukanGrade(nilaiAkhir);
            
            System.out.println("Perolehan Nilai:");
            cetakBarisNilai("Partisipatif", pPA, kPA, bobotPA);
            cetakBarisNilai("Tugas", pT, kT, bobotT);
            cetakBarisNilai("Kuis", pK, kK, bobotK);
            cetakBarisNilai("Proyek", pP, kP, bobotP);
            cetakBarisNilai("UTS", pUTS, kUTS, bobotUTS);
            cetakBarisNilai("UAS", pUAS, kUAS, bobotUAS);
            System.out.println();
            System.out.printf(Locale.US, ">> Nilai Akhir: %.2f\n", nilaiAkhir);
            System.out.println(">> Grade: " + grade);
            
        } finally {
            // Memastikan resource memori ditutup dengan benar apa pun yang terjadi
            scanner.close();
        }
    }
    
    private static int hitungPersentase(int totalSkor, int totalBobot) {
        if (totalBobot == 0) return 0;
        return (totalSkor * 100) / totalBobot;
    }

    private static double hitungKontribusi(int totalSkor, int totalBobot, int bobotMaksimal) {
        int persentase = hitungPersentase(totalSkor, totalBobot);
        return (persentase / 100.0) * bobotMaksimal;
    }
    
    private static String tentukanGrade(double nilaiAkhir) {
        if (nilaiAkhir >= 79.5) return "A";
        if (nilaiAkhir >= 72) return "AB";
        if (nilaiAkhir >= 64.5) return "B";
        if (nilaiAkhir >= 57) return "BC";
        if (nilaiAkhir >= 49.5) return "C";
        if (nilaiAkhir >= 34) return "D";
        return "E";
    }
    
    private static void cetakBarisNilai(String nama, int persentase, double kontribusi, int bobotMaksimal) {
        System.out.printf(Locale.US, ">> %s: %d/100 (%.2f/%d)\n", nama, persentase, kontribusi, bobotMaksimal);
    }
}