import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser {
    public static void main(String[] args) {
        ArrayList<String> list_operators = new ArrayList<>();
        list_operators.add(" ");
        list_operators.add(",");
        list_operators.add(".");
        list_operators.add("!");
        list_operators.add("?");
        list_operators.add("(");
        list_operators.add(")");
        list_operators.add("[");
        list_operators.add("]");
        list_operators.add("-");
        list_operators.add(";");
        list_operators.add(":");
        list_operators.add("\n");
        list_operators.add("\r");
        list_operators.add("\t");
        //
        System.out.println("url:");
        Scanner scan = new Scanner(System.in);
        String url = scan.nextLine();
        //
        try {
            String pagetext = getPage(url).text();
            String separatorsString = String.join("|\\", list_operators);
            Map<String, Word> countMap = new HashMap<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(pagetext.getBytes(StandardCharsets.UTF_8))));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(separatorsString);
                for (String word : words) {
                    if ("".equals(word)) {
                        continue;
                    }
                    Word word_obj = countMap.get(word);
                    if (word_obj == null) {
                        word_obj = new Word();
                        word_obj.word = word;
                        word_obj.count = 0;
                        countMap.put(word, word_obj);
                    }
                    word_obj.count++;
                }
            }
            reader.close();
            //
            SortedSet<Word> sortedWords = new TreeSet<>(countMap.values());
            List<Word> wordsOut = new ArrayList(sortedWords);
            Collections.reverse(wordsOut);
            //
            for (Word word : wordsOut) {
                System.out.println(word.count + " " + word.word);
            }
        } catch(IOException e){
            System.out.println("invalid url");
        }
    }

    public static Document getPage(String url) throws  IOException{
        Document page = Jsoup.parse(new URL(url), 7000);
        return page;
    }

}
