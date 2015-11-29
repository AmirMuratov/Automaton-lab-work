/**
 * Automaton was created by amir on 13.11.15.
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class C {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = false;
    String filename = "trees";

    class Node {
        int left;
        int right;
        int pred;
        int newId;
        boolean isLeaf;

        public Node(boolean isLeaf, int pred) {
            this.isLeaf = isLeaf;
            this.pred = pred;
        }
    }

    Map<Integer, Boolean> curPos;
    Node[] nodes;


    void dfs(int x) {
        nodes[x].newId = k;
        k++;
        if (nodes[x].isLeaf) {
            ans[nodes[x].newId] = "leaf " + nodes[x].pred;
            return;
        }
        curPos.put(nodes[x].pred, false);
        if (nodes[nodes[x].left].isLeaf) {
            dfs(nodes[x].left);
        } else {
            int curNode = nodes[x].left;
            while (!nodes[curNode].isLeaf && curPos.containsKey(nodes[curNode].pred)) {
                if (curPos.get(nodes[curNode].pred)) {
                    curNode = nodes[curNode].right;
                } else {
                    curNode = nodes[curNode].left;
                }
            }
            nodes[x].left = curNode;
            dfs(nodes[x].left);
        }

        curPos.put(nodes[x].pred, true);
        if (nodes[nodes[x].right].isLeaf) {
            dfs(nodes[x].right);
        } else {
            int curNode = nodes[x].right;
            while (!nodes[curNode].isLeaf && curPos.containsKey(nodes[curNode].pred)) {
                if (curPos.get(nodes[curNode].pred)) {
                    curNode = nodes[curNode].right;
                } else {
                    curNode = nodes[curNode].left;
                }
            }
            nodes[x].right = curNode;
            dfs(nodes[x].right);
        }

        curPos.remove(nodes[x].pred);
        ans[nodes[x].newId] = "choice " + nodes[x].pred + " " +
                (nodes[nodes[x].left].newId + 1) + " " + (nodes[nodes[x].right].newId + 1);
    }

    int k;
    String[] ans;

    public void solve() {
        int n = in.nextInt();
        curPos = new HashMap<>();
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            String s = in.next();
            if (s.equals("choice")) {
                nodes[i] = new Node(false, in.nextInt());
                nodes[i].left = in.nextInt() - 1;
                nodes[i].right = in.nextInt() - 1;
            }
            if (s.equals("leaf")) {
                nodes[i] = new Node(true, in.nextInt());
            }
        }
        k = 0;
        ans = new String[n];
        dfs(0);
        out.println(k);
        for (int i = 0; i < k; i++) {
            out.println(ans[i]);
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
        new C().run();
    }
}