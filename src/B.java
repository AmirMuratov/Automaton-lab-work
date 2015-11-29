/**
 * Automaton was created by amir on 13.11.15.
 */

import java.io.*;
import java.util.StringTokenizer;

public class B {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = false;
    String filename = "crossover";


    public void solve() {
        int m = in.nextInt();
        int n = in.nextInt();
        String[] str = new String[m];
        for (int i = 0; i < m; i++) {
            str[i] = in.next();
        }
        String s = in.next();

        boolean[][][] a = new boolean[m][n + 1][n + 1];
        for (int k = 0; k < m; k++) {
            for (int i = 0; i <= n; i++) {
                a[k][i][i] = true;
                for (int j = i + 1; j <= n; j++) {
                    a[k][i][j] = a[k][i][j - 1] && (str[k].charAt(j - 1) == s.charAt(j - 1));
                }
            }
        }

        boolean onePoint = false;
        for (int i = 0; i < m; i++) {
            for (int j = i; j < m; j++) {
                for (int k = 0; k <= n; k++) {
                    onePoint |= a[i][0][k] && a[j][k][n];
                    onePoint |= a[j][0][k] && a[i][k][n];
                }
            }
        }

        boolean twoPoint = false;
        for (int i = 0; i < m; i++) {
            for (int j = i; j < m; j++) {
                for (int k1 = 0; k1 <= n; k1++) {
                    for (int k2 = k1; k2 <= n; k2++) {
                        twoPoint |= a[i][0][k1] && a[j][k1][k2] && a[i][k2][n];
                        twoPoint |= a[j][0][k1] && a[i][k1][k2] && a[j][k2][n];
                    }
                }
            }
        }

        boolean smooth = false;
        for (int i = 0; i < m; i++) {
            for (int j = i; j < m; j++) {
                boolean ok = true;
                for (int k = 0; k < n; k++) {
                    if (!(str[i].charAt(k) == s.charAt(k) || str[j].charAt(k) == s.charAt(k))) {
                        ok = false;
                        break;
                    }
                }
                smooth |= ok;
            }
        }

        out.println(onePoint ? "YES" : "NO");
        out.println(twoPoint ? "YES" : "NO");
        out.println(smooth ? "YES" : "NO");
    }

    public void run() {
        try {
            if (systemIO) {
                in = new FastScanner(System.in);
                out = new PrintWriter(System.out);
            } else {
                in = new FastScanner(new File(filename + ".in"));
                out = new PrintWriter(new File(filename + ".out"));
            }
            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;


        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String nextLine() {
            try {
                return br.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

    }

    public static void main(String[] arg) {
        new B().run();
    }
}