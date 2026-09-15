import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String nim = scanner.nextLine().trim();
            processNim(nim);
        }
        scanner.close();
    }

    private static void processNim(String nim) {
        if (!isValidNimLength(nim)) {
            System.out.println("NIM harus 8 karakter");
            return;
        }
        
        String prefix = nim.substring(0, 3);
        String prodi = mapPrefixToProdi(prefix);
        
        if (prodi.equals("Kode tidak tersedia")) {
            System.out.println("Kode tidak tersedia");
            return;
        }
        
        int angkatan = extractAngkatan(nim);
        int urutan = extractUrutan(nim);
        
        printResult(nim, prodi, angkatan, urutan);
    }

    private static boolean isValidNimLength(String nim) {
        return nim.length() == 8;
    }

    private static String mapPrefixToProdi(String prefix) {
        switch (prefix) {
            case "11S": return "Sarjana Informatika";
            case "12S": return "Sarjana Sistem Informasi";
            case "13S": return "Sarjana Teknik Elektro";
            case "21S": return "Sarjana Manajemen Rekayasa";
            case "22S": return "Sarjana Teknik Metalurgi";
            case "31S": return "Sarjana Teknik Bioproses";
            case "32S": return "Sarjana Bioteknologi";
            case "114": return "Diploma 4 Teknologi Rekasaya Perangkat Lunak";
            case "113": return "Diploma 3 Teknologi Informasi";
            case "133": return "Diploma 3 Teknologi Komputer";
            default: return "Kode tidak tersedia";
        }
    }

    private static int extractAngkatan(String nim) {
        return Integer.parseInt("20" + nim.substring(3, 5));
    }

    private static int extractUrutan(String nim) {
        return Integer.parseInt(nim.substring(5, 8));
    }

    private static void printResult(String nim, String prodi, int angkatan, int urutan) {
        System.out.println("Inforamsi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + prodi);
        System.out.println(">> Angkatan: " + angkatan);
        System.out.println(">> Urutan: " + urutan);
    }
}