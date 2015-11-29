/**
 * Automaton was created by amir on 13.11.15.
 */

import java.io.*;
import java.util.StringTokenizer;

public class A {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = false;
    String filename = "mutation";


    public void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            String s1 = in.next();
            String s2 = in.next();
            double ans = 1;
            for (int j = 0; j < n; j++) {
                if (s1.charAt(j) == s2.charAt(j)) {
                    ans /= n;
                    ans *= (n - 1);
                } else {
                    ans /= n;
                }
            }
            out.printf("%.10f\n", ans);
        }

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
        new A().run();
    }
}