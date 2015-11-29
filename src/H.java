/**
 * Automaton was created by amir on 13.11.15.
 */

import java.io.*;
import java.util.StringTokenizer;

public class H {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = true;
    String filename = "";

    public double countF(double[] a) {
        for (int i = 0; i < a.length; i++) {
            out.printf("%.10f ", a[i]);
        }
        out.println();
        out.flush();
        return in.nextDouble();
    }

    public double ternarySearch(double l, double r, int iter, int k, int n) {
        double[] a = new double[n];
        for (int i = 0; i < iter; i++) {
            double m1 = (r + 2 * l) / 3;
            double m2 = (2 * r + l) / 3;
            a[k] = m1;
            double f1 = countF(a);
            a[k] = m2;
            double f2 = countF(a);
            if (f1 < f2) {
                r = m2;
            } else {
                l = m1;
            }
        }
        return l;
    }

    final int NUMBER_OF_STEPS = 400;
    final int NUMBER_OF_ITERATIONS = 100;

    public void solve() {
        int n = in.nextInt();
        double[] ans = new double[n];
        for (int i = 0; i < n; i++) {
            double[] steps = new double[NUMBER_OF_STEPS + 1];
            for (int j = 0; j <= NUMBER_OF_STEPS; j++) {
                ans[i] = (double) j / NUMBER_OF_STEPS;
                steps[j] = countF(ans);
            }
            double minx = 0;
            double miny = steps[0];
            if (miny > steps[NUMBER_OF_STEPS]) {
                minx = 1;
                miny = steps[NUMBER_OF_STEPS];
            }

            int k = 0;
            while (k < NUMBER_OF_STEPS && steps[k] <= steps[k + 1]) {
                k++;
            }
            while (k < NUMBER_OF_STEPS && steps[k] > steps[k + 1]) {
                k++;
            }

            if (k != NUMBER_OF_STEPS) {
                double x1 = ternarySearch((double) (k - 1) / NUMBER_OF_STEPS, (double) (k + 1) / NUMBER_OF_STEPS,
                        NUMBER_OF_ITERATIONS, i, n);
                ans[i] = x1;
                double y1 = countF(ans);

                if (miny > y1) {
                    minx = x1;
                    miny = y1;
                }
            }


            k = NUMBER_OF_STEPS;
            while (k > 0 && steps[k] <= steps[k - 1]) {
                k--;
            }
            while (k > 0 && steps[k] > steps[k - 1]) {
                k--;
            }

            if (k != 0) {
                double x2 = ternarySearch((double) (k - 1) / NUMBER_OF_STEPS, (double) (k + 1) / NUMBER_OF_STEPS,
                        NUMBER_OF_ITERATIONS, i, n);
                ans[i] = x2;
                double y2 = countF(ans);

                if (miny > y2) {
                    minx = x2;
                    miny = y2;
                }
            }
            ans[i] = minx;
        }
        out.println("minimum " + countF(ans));
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
        new H().run();
    }
}