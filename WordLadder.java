import java.util.*;
import java.io.*;

public class WordLadder {
    private static LinkedList<String> dict;
    private static LinkedList<String> visited;
    private static String start, end;

    public static void main(String[] args) throws IOException {
        readDictFile();
        readInfile();
    }

    private static void printQueue(Queue<Stack<String>> queue) {
        Queue<Stack<String>> t = new LinkedList<>(queue);
        for (Stack<String> strings : t) {
            Stack<String> s = new Stack<>();
            s.addAll(strings);
            while (!s.empty()){
                System.out.print(s.pop()+" ");
            }
            System.out.println();
        }
    }

    public static void findLadder(String start, String end) {
        visited = new LinkedList<>();
        visited.add(start);

        Queue<Stack<String>> queue = new LinkedList<>();
        Stack<String> oldStack = new Stack<>();
        oldStack.add(start);
        queue.add(oldStack);
        while (true) {
            for (Stack<String> strings : queue) {
                if(strings.peek().equals(end))
                {
                    System.out.println(strings);
                    return;
                }
            }
            if(queue.isEmpty()) {
                System.out.println("There is no word ladder between " + start + " and " + end);
                return;
            }
            oldStack = queue.remove();
            String w = oldStack.peek();

            for (String s : dict) {
                if (isAnEdge(s, w) && !visited.contains(s)) {
                    Stack<String> copiedStack = new Stack<>();
                    copiedStack.addAll(oldStack);
                    copiedStack.push(s);
                    visited.add(s);
                    queue.add(copiedStack);
                }
            }
        }
    }


    public static boolean isAnEdge(String w1, String w2) {
        if(w1.length()!=w2.length())
            return false;
        int dif=0;
        for (int i = 0; i < w1.length(); i++) {
            if (w1.charAt(i) != w2.charAt(i)) {
                dif++;
            }
        }
        return dif==1;
    }

    private static void readInfile() throws FileNotFoundException {
        File infile = new File("infile.txt");
        try (Scanner in = new Scanner(infile)) {
            while (in.hasNext()) {
                start = in.next();
                end = in.next();
                if (start.length() != end.length() || !dict.contains(start) || !dict.contains(end)) {
                    System.out.println("There is no word ladder between " + start + " and " + end);
                    continue;
                }
                findLadder(start, end);
            }
        }

    }

    private static void readDictFile() throws FileNotFoundException {
        File dictfile = new File("dictionary.txt");
        dict = new LinkedList<>();
        try (
                Scanner in = new Scanner(dictfile);) {
            while (in.hasNext()) {
                dict.add(in.next());
            }
        }

    }


}



