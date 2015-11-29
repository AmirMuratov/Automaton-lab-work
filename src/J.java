/**
 * Automaton was created by amir on 17.11.15.
 */

import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;

public class J {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = true;
    String filename = "";

    public double countF(Point a) {
        for (int i = 0; i < dimensions; i++) {
            out.printf("%.10f ", a.x[i]);
        }
        out.println();
        out.flush();
        String s = in.next();
        if (s.equals("Bingo")) {
            System.exit(0);
        } else {
            return Double.valueOf(s);
        }
        return 0;
    }


    class Point {
        double[] x;
        double value;

        public Point() {
            x = new double[dimensions];
        }

        public void GenerateCoordinates() {
            for (int i = 0; i < x.length; i++) {
                x[i] = r.nextDouble() * 20 - 10;
            }
        }

        public double countValue() {
            value = countF(this);
            return value;
        }
    }

    public Point mutate(Point x, Point c, Point a, Point b) {
        Point result = new Point();
        for (int i = 0; i < dimensions; i++) {
            result.x[i] = Math.max(-10, Math.min(10, c.x[i] + POWER_OF_MUTATION * (a.x[i] - b.x[i])));
        }
        int rand = r.nextInt(dimensions);
        for (int i = 0; i < dimensions; i++) {
            if (r.nextDouble() > MUTATION_PROBABILITY && i != rand) {
                result.x[i] = x.x[i];
            }
        }
        return result;
    }

    class Population {
        Point[] individs;
        int size;

        public Population(int size) {
            this.size = size;
            individs = new Point[size];
            for (int i = 0; i < size; i++) {
                individs[i] = new Point();
                individs[i].GenerateCoordinates();
                individs[i].countValue();
            }
        }

        class CAB {
            int c;
            int a;
            int b;

            public CAB(int required, int length) {
                c = r.nextInt(length);
                while (c == required) {
                    c = r.nextInt(length);
                }
                a = r.nextInt(length);
                while (a == required || a == c) {
                    a = r.nextInt(length);
                }
                b = r.nextInt(length);
                while (b == required || b == c || b == a) {
                    b = r.nextInt(length);
                }

            }
        }

        public Population(Population previous) {
            size = previous.size;
            individs = new Point[size];
            for (int i = 0; i < size; i++) {
                individs[i] = new Point();
                CAB cab = new CAB(i, size);
                individs[i] = mutate(previous.individs[i], previous.individs[cab.c],
                        previous.individs[cab.a], previous.individs[cab.b]);
                individs[i].countValue();
                if (individs[i].value > previous.individs[i].value) {
                    individs[i] = previous.individs[i];
                }
            }
        }
    }

    final int RAND_SEED = 2323;
    final int POPULATION_SIZE_FACTOR = 8;
    final double POWER_OF_MUTATION = 1;//0.5;//0..2
    final double MUTATION_PROBABILITY = 0.1;//0.8

    int dimensions;
    Random r;

    public void solve() {
        //Differential Evolution
        dimensions = in.nextInt();
        r = new Random(RAND_SEED);
        Population p = new Population(POPULATION_SIZE_FACTOR * dimensions);
        int t = 0;
        while (true) {
            //t++;
            //if (t > 5000) {
            //    p = new Population(POPULATION_SIZE_FACTOR * dimensions);
            //}
            p = new Population(p);
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
        new J().run();
    }
}