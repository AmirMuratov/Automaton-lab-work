/**
 * Automaton was created by amir on 15.11.15.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class G {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = false;
    String filename = "artificial";

    char[] actionToChar = {'M', 'L', 'R'};
    Random r;

    class Automaton {
        int n;
        int[][] edges;
        int[][] actions;//M - 0, L - 1, R - 2
        int quality;

        public Automaton(int n) {
            this.n = n;
            edges = new int[2][n];
            actions = new int[2][n];
        }

        public void generateEdgesAndCountQuality() {
            for (int i = 0; i < n; i++) {
                edges[0][i] = r.nextInt(n);
                edges[1][i] = r.nextInt(n);
            }
            for (int i = 0; i < n; i++) {
                actions[0][i] = r.nextInt(3);
                actions[1][i] = 0;
            }
            countQuality();
        }

        public void getEdgesFromInput() {
            for (int i = 0; i < n; i++) {
                edges[0][i] = in.nextInt() - 1;
                edges[1][i] = in.nextInt() - 1;
                char c = in.next().charAt(0);
                if (c == 'M') {
                    actions[0][i] = 0;
                }
                if (c == 'L') {
                    actions[0][i] = 1;
                }
                if (c == 'R') {
                    actions[0][i] = 2;
                }
                c = in.next().charAt(0);
                if (c == 'M') {
                    actions[1][i] = 0;
                }
                if (c == 'L') {
                    actions[1][i] = 1;
                }
                if (c == 'R') {
                    actions[1][i] = 2;
                }
            }
            countQuality();
        }

        public void countQuality() {
            quality = checkAutomaton();
        }

        public int checkAutomaton() {
            int size = field.length;
            int res = 0;
            int curX = 0;
            int curY = 0;
            int directionX = 0;
            int directionY = 1;
            int curNode = 0;
            for (int i = 0; i < steps; i++) {
                int x = 0;
                if (field[(curX + directionX + size) % size][(curY + directionY + size) % size] == 1) {
                    x = 1;
                }
                if (actions[x][curNode] == 0) {
                    curX = (curX + directionX + size) % size;
                    curY = (curY + directionY + size) % size;
                    if (field[curX][curY] == 1) {
                        field[curX][curY] = -1;
                        res++;
                    }
                }
                if (actions[x][curNode] == 1) {
                    int t = directionX;
                    directionX = -directionY;
                    directionY = t;
                }
                if (actions[x][curNode] == 2) {
                    int t = directionY;
                    directionY = -directionX;
                    directionX = t;
                }
                curNode = edges[x][curNode];
            }
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    field[i][j] = Math.abs(field[i][j]);
                }
            }
            return res;
        }

        public void printAutomaton() {
            for (int i = 0; i < n; i++) {
                out.print((edges[0][i] + 1) + " " + (edges[1][i] + 1) + " ");
                out.print(actionToChar[actions[0][i]] + " " + actionToChar[actions[1][i]]);
                out.println();
            }
            out.println();
        }

        public void visualize() {
            int size = field.length;
            int curX = 0;
            int curY = 0;
            int directionX = 0;
            int directionY = 1;
            int curNode = 0;
            for (int i = 0; i < steps; i++) {
                field[curX][curY] = -2;
                int x = 0;
                if (field[(curX + directionX + size) % size][(curY + directionY + size) % size] == 1) {
                    x = 1;
                }
                if (actions[x][curNode] == 0) {
                    curX = (curX + directionX + size) % size;
                    curY = (curY + directionY + size) % size;
                }
                if (actions[x][curNode] == 1) {
                    int t = directionX;
                    directionX = -directionY;
                    directionY = t;
                }
                if (actions[x][curNode] == 2) {
                    int t = directionY;
                    directionY = -directionX;
                    directionX = t;
                }
                curNode = edges[x][curNode];
            }
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    switch (field[i][j]) {
                        case 0:
                            out.print('.');
                            break;
                        case 1:
                            out.print('*');
                            break;
                        case -2:
                            out.print('+');
                            break;
                    }
                }
                out.println();
            }
        }
    }

    class Generation {
        List<Automaton> automatons;
        int sumQ;

        public void countQuality() {
            sumQ = 0;
            for (Automaton a : automatons) {
                sumQ += a.quality;
            }
        }

        public Generation(int generationSize, int automatonSize) {
            automatons = new ArrayList<>();
            for (int i = 0; i < generationSize; i++) {
                Automaton aut = new Automaton(automatonSize);
                aut.generateEdgesAndCountQuality();
                automatons.add(aut);
            }
            countQuality();
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

        public Generation(Generation previousGeneration) {
            automatons = new ArrayList<>();
            for (int i = 0; i < previousGeneration.automatons.size(); i++) {
                CAB cab = new CAB(i, previousGeneration.automatons.size());
                Automaton mutation = mutate(previousGeneration.automatons.get(cab.a),
                        previousGeneration.automatons.get(cab.b),
                        previousGeneration.automatons.get(cab.c));
                mutation = crossOver(previousGeneration.automatons.get(i), mutation);
                mutation.countQuality();
                if (mutation.quality > previousGeneration.automatons.get(i).quality) {
                    automatons.add(mutation);
                } else {
                    automatons.add(previousGeneration.automatons.get(i));
                }
            }
            countQuality();
        }

        public Automaton crossOver(Automaton automaton, Automaton mutation) {
            Automaton res = new Automaton(automaton.n);
            for (int i = 0; i < res.n; i++) {
                if (r.nextDouble() > MUTATION_PROBABILITY) {
                    res.edges[0][i] = automaton.edges[0][i];
                    res.actions[0][i] = automaton.actions[0][i];
                } else {
                    res.edges[0][i] = mutation.edges[0][i];
                    res.actions[0][i] = mutation.actions[0][i];
                }
                if (r.nextDouble() > MUTATION_PROBABILITY) {
                    res.edges[1][i] = automaton.edges[1][i];
                    res.actions[1][i] = automaton.actions[1][i];
                } else {
                    res.edges[1][i] = mutation.edges[1][i];
                    res.actions[1][i] = mutation.actions[1][i];
                }
            }
            return res;
        }

        public Automaton mutate(Automaton a, Automaton b, Automaton c) {
            Automaton res = new Automaton(a.n);
            res.generateEdgesAndCountQuality();
            return res;
        }

        public Automaton getGoodAutomaton() {
            int p = 0;
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    p += field[i][j];
                }
            }
            for (Automaton a : automatons) {
                if (a.checkAutomaton() == p) {
                    return a;
                }
            }
            return null;
        }

        public void printGeneration() {
            out.println("**********************");
            for (Automaton a : automatons) {
                a.printAutomaton();
                out.println(a.quality);
            }
            out.println("**********************");
        }
    }

    final int GENERATION_SIZE = 120;
    final double MUTATION_PROBABILITY = 0.15;
    final int RAND_SEED = 148;

    int[][] field;
    int steps;

    public void solve() {
        r = new Random(RAND_SEED);
        int n = in.nextInt();
        int k = in.nextInt();
        steps = in.nextInt();
        field = new int[n][n];
        int ast = 0;
        for (int i = 0; i < n; i++) {
            String s = in.next();
            for (int j = 0; j < n; j++) {
                field[i][j] = s.charAt(j) == '.' ? 0 : 1;
                ast += field[i][j];
            }
        }
        System.err.println(ast);
        Generation gen = new Generation(GENERATION_SIZE, k);
        Automaton t = new Automaton(k);
        //t.getEdgesFromInput();
        //gen.automatons.add(t);
        int p = 0;
        while (gen.getGoodAutomaton() == null) {
            //System.err.println(gen.automatons.size());
            gen = new Generation(gen);
            if (p % 300 == 0) {
                gen.printGeneration();
                int mx = 0;
                for (Automaton a : gen.automatons) {
                    mx = Math.max(a.quality, mx);
                }
                System.err.println(mx);
            }
            p++;
        }
        Automaton ans = gen.getGoodAutomaton();
        ans.printAutomaton();
        ans.visualize();
        /*int test = in.nextInt();
        if (test == 1) {
            out.print("2 1 R M\n3 3 M M\n3 4 R M\n2 2 L M\n");
        }
        if (test == 2) {
            out.print("1 1 R M\n");
        }
        if (test == 3) {
            out.print("3 2 L M\n1 2 M M\n2 3 R M\n");
        }
        if (test == 4) {
            out.print("3 1 M M\n1 2 L M\n4 2 M M\n1 4 R M\n");
        }
        if (test == 5) {
            out.print("2 3 M M\n3 2 M L\n1 1 R M\n");
        }
        if (test == 6) {
            out.print("3 5 M M\n4 1 M M\n5 5 R M\n2 1 L M\n4 2 M M\n");
        }
        if (test == 7) {
            out.print("4 3 M M\n2 1 R M\n2 1 M M\n2 2 L M\n");
        }
        if (test == 8) {
            out.print("5 3 R M\n1 2 M M\n2 2 L M\n3 2 M M\n3 4 L M\n");
        }
        if (test == 9) {
            out.print("3 5 M M\n4 1 L M\n6 5 R M\n1 3 R M\n4 2 M M\n2 2 L M");
        }
        if (test == 10) {
            out.print("5 1 R M\n5 5 L M\n4 4 M M\n2 1 R M\n3 6 L M\n2 5 L R");
        }*/


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
        new G().run();
    }
}