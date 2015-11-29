/**
 * Automaton was created by amir on 13.11.15.
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class D {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = false;
    String filename = "start";

    class Node {
        int left;
        int right;
        char c;

        public Node(int left, int right, char c) {
            this.left = left;
            this.right = right;
            this.c = c;
        }
    }


    public void solve() {
        int m = in.nextInt();
        int n = in.nextInt();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(in.nextInt() - 1, in.nextInt() - 1, in.next().charAt(0));
        }
        String s = in.next();

        boolean[] dp1 = new boolean[n];
        boolean[] dp2 = new boolean[n];
        for (int i = 0; i < n; i++) {
            dp1[i] = s.charAt(m - 1) == nodes[i].c;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (s.charAt(i) == nodes[j].c) {
                    dp2[j] = dp1[nodes[j].left] | dp1[nodes[j].right];
                }
            }
            System.arraycopy(dp2, 0, dp1, 0, n);
            Arrays.fill(dp2, false);
        }

        /*for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                out.print(dp[i][j] ? 1 : 0);
            }
            out.println();
        }*/
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (dp1[nodes[i].left] || dp1[nodes[i].right]) {
                k++;
            }
        }
        out.print(k + " ");
        for (int i = 0; i < n; i++) {
            if (dp1[nodes[i].left] || dp1[nodes[i].right]) {
                out.print((i + 1) + " ");
            }
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
        new D().run();
    }
}