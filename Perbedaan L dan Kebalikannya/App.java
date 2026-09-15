import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            if (!sc.hasNextInt()) {
                return;
            }
            
            int n = sc.nextInt();
            int[][] matrix = readMatrix(sc, n);
            
            processMatrixAnalysis(n, matrix);
        } finally {
            sc.close(); // Best practice: Scanner selalu ditutup apa pun yang terjadi
        }
    }

    private static int[][] readMatrix(Scanner sc, int n) {
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = sc.nextInt();
            }
        }
        return m;
    }

    private static void processMatrixAnalysis(int n, int[][] m) {
        long totalSum = calculateTotalSum(n, m);

        if (n == 1 || n == 2) {
            printSmallMatrixResult(n, m, totalSum);
        } else {
            printLargeMatrixResult(n, m);
        }
    }

    private static long calculateTotalSum(int n, int[][] m) {
        long sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum += m[i][j];
            }
        }
        return sum;
    }

    private static void printSmallMatrixResult(int n, int[][] m, long totalSum) {
        System.out.println("Nilai L: Tidak Ada");
        System.out.println("Nilai Kebalikan L: Tidak Ada");
        long middle = (n == 1) ? m[0][0] : totalSum;
        System.out.println("Nilai Tengah: " + middle);
        System.out.println("Perbedaan: Tidak Ada");
        System.out.println("Dominan: " + middle);
    }

    private static void printLargeMatrixResult(int n, int[][] m) {
        long lVal = calculateLValue(n, m);
        long revLVal = calculateReverseLValue(n, m);
        long tengah = calculateMiddleValue(n, m);
        long beda = Math.abs(lVal - revLVal);
        long dominan = (beda == 0) ? tengah : Math.max(lVal, revLVal);

        System.out.println("Nilai L: " + lVal);
        System.out.println("Nilai Kebalikan L: " + revLVal);
        System.out.println("Nilai Tengah: " + tengah);
        System.out.println("Perbedaan: " + beda);
        System.out.println("Dominan: " + dominan);
    }

    private static long calculateLValue(int n, int[][] m) {
        long sum = 0;
        for (int i = 0; i < n; i++) sum += m[i][0]; // Kolom paling kiri
        for (int j = 1; j < n - 1; j++) sum += m[n - 1][j]; // Baris bawah (kecuali ujung)
        return sum;
    }

    private static long calculateReverseLValue(int n, int[][] m) {
        long sum = 0;
        for (int i = 0; i < n; i++) sum += m[i][n - 1]; // Kolom paling kanan
        for (int j = 1; j < n - 1; j++) sum += m[0][j]; // Baris atas (kecuali ujung)
        return sum;
    }

    private static long calculateMiddleValue(int n, int[][] m) {
        if (n % 2 != 0) {
            return m[n / 2][n / 2]; // Ambil 1 nilai tengah jika ganjil
        } else {
            int mid = n / 2;
            // Jumlahkan 4 nilai tengah jika genap
            return (long)m[mid - 1][mid - 1] + m[mid - 1][mid] + m[mid][mid - 1] + m[mid][mid];
        }
    }
}