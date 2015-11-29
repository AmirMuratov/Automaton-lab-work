/**
 * Automaton was created by amir on 13.11.15.
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class I {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = true;
    String filename = "";


    public void solve() {
        int n = in.nextInt();
        char[] ans = new char[n];
        Arrays.fill(ans, '0');
        out.println(new String(ans));
        out.flush();
        int res = in.nextInt();
        int i = 0;
        while (res != n) {
            ans[i] = '1';
            out.println(new String(ans));
            out.flush();
            int cur = in.nextInt();
            if (cur < res) {
                ans[i] = '0';
            } else {
                res = cur;
            }
            i++;
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
        new I().run();
    }
}