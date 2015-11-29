/**
 * Automaton was created by amir on 14.11.15.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class F {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = false;
    String filename = "continuous";

    class Pair {
        double t;
        int len;

        public Pair(double t, int len) {
            this.t = t;
            this.len = len;
        }
    }

    class Node {
        int zero;
        int one;

        public Node(int zero, int one) {
            this.zero = zero;
            this.one = one;
        }
    }

    public double[] gauss(double[][] matrix) {
        double[] x = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            x[i] = matrix[i][matrix.length];
        }
        double m;
        for (int k = 1; k < matrix.length; k++) {
            for (int j = k; j < matrix.length; j++) {
                m = matrix[j][k - 1] / matrix[k - 1][k - 1];
                for (int i = 0; i < matrix[j].length; i++) {
                    matrix[j][i] = matrix[j][i] - m * matrix[k - 1][i];
                }
                x[j] = x[j] - m * x[k - 1];
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = i + 1; j < matrix.length; j++) {
                x[i] -= matrix[i][j] * x[j];
            }
            x[i] = x[i] / matrix[i][i];
        }
        return x;
    }

    public void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(in.nextInt() - 1, in.nextInt() - 1);
        }
        String[] s = new String[m];
        List<Double>[] o = new List[m];
        for (int i = 0; i < m; i++) {
            int k = in.nextInt();
            o[i] = new ArrayList<>(k);
            s[i] = in.next();
            for (int j = 0; j < k; j++) {
                o[i].add(in.nextDouble());
            }
        }
        double[][] matrix = new double[2 * n][2 * n + 1];
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < m; j++) {
                int curNode = 0;
                int[] cur = new int[2 * n];
                for (int k = 0; k < s[j].length(); k++) {
                    if (s[j].charAt(k) == '0') {
                        cur[curNode * 2]++;
                        curNode = nodes[curNode].zero;
                    } else {
                        cur[curNode * 2 + 1]++;
                        curNode = nodes[curNode].one;
                    }
                    for (int t = 0; t < n * 2; t++) {
                        if (t != i) {
                            matrix[i][t] -= (double) (2 * cur[i] * cur[t]) / s[j].length();
                        } else {
                            matrix[i][t] -= (double) (2 * cur[i] * cur[i]) / s[j].length();
                        }
                    }
                    matrix[i][2 * n] -= (o[j].get(k) * 2 * cur[i]) / s[j].length();
                }
            }
        }
        List<Integer> zer = new ArrayList<>();
        for (int i = 0; i < 2 * n; i++) {
            boolean ok = false;
            for (int j = 0; j <= 2 * n; j++) {
                ok |= matrix[i][j] != 0;
            }
            if (!ok) {
                zer.add(i);
                matrix[i][i] = 1;
                matrix[i][2 * n] = 1;
            }
        }
        /*for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j <= 2 * n; j++) {
                out.print(matrix[i][j] + " ");
            }
            out.println();
        }*/
        double[] ans = gauss(matrix);
        for (int k : zer) {
            ans[k] = 0;
        }
        for (int i = 0;
             i < n; i++) {
            out.printf("%.10f %.10f\n", ans[2 * i], ans[2 * i + 1]);
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

        double nextDouble() {
            return Double.parseDouble(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

    }

    public static void main(String[] arg) {
        new F().run();
    }
}