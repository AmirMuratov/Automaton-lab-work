/**
 * Automaton was created by amir on 13.11.15.
 */

import java.io.*;
import java.util.StringTokenizer;

public class E {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = false;
    String filename = "discrete";

    class Pair {
        char c;
        int len;

        public Pair(char c, int len) {
            this.c = c;
            this.len = len;
        }
    }

    class Node {
        int zero;
        int one;
        double[] zeroErr;
        double[] oneErr;
        double zeroSum;
        double oneSum;

        public Node(int zero, int one) {
            this.zero = zero;
            this.one = one;
            zeroErr = new double[26];
            oneErr = new double[26];
        }

        public void addZero(char c, int len) {
            zeroSum -= (double) 1 / len;
            zeroErr[c - 'a'] += (double) 1 / len;
        }

        public void addOne(char c, int len) {
            oneSum -= (double) 1 / len;
            oneErr[c - 'a'] += (double) 1 / len;
        }
    }


    public void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(in.nextInt() - 1, in.nextInt() - 1);
        }
        for (int i = 0; i < m; i++) {
            int k = in.nextInt();
            String s1 = in.next();
            String s2 = in.next();
            int curNode = 0;
            for (int j = 0; j < k; j++) {
                if (s1.charAt(j) == '0') {
                    nodes[curNode].addZero(s2.charAt(j), k);
                    curNode = nodes[curNode].zero;
                } else {
                    nodes[curNode].addOne(s2.charAt(j), k);
                    curNode = nodes[curNode].one;
                }
            }
        }
        /*for (Pair p : nodes[0].o) {
            out.println(p.c + " " + p.len);
        }*/
        for (int i = 0; i < n; i++) {
            char ans = 'a';
            double max = -1e+10;
            for (int j = 0; j < 26; j++) {
                if (max < nodes[i].zeroSum + nodes[i].zeroErr[j]) {
                    max = nodes[i].zeroSum + nodes[i].zeroErr[j];
                    ans = (char) (j + 'a');
                }
            }
            out.print(ans + " ");

            ans = 'a';
            max = -1e+10;
            for (int j = 0; j < 26; j++) {
                if (max < nodes[i].oneSum + nodes[i].oneErr[j]) {
                    max = nodes[i].oneSum + nodes[i].oneErr[j];
                    ans = (char) (j + 'a');
                }
            }
            out.println(ans);
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
        new E().run();
    }
}