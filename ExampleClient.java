import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * ExampleClient.java
 * Provides example calls to WordLadderGame methods in an instance of
 * the Doublets class.
 *
 * The word list files must be extracted into the current directory
 * before running this class.
 *
 *      jar xf WordLists.jar
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2016-04-07
 */
public class ExampleClient {

   /** Drives execution. */
   public static void main(String[] args) throws FileNotFoundException {
      WordLadderGame doublets = new Doublets(new FileInputStream(new File("sowpods.txt")));
   
      System.out.println(doublets.getHammingDistance("tiger", "tiger"));
      System.out.println(doublets.getHammingDistance("tiger", "eagle"));
      System.out.println(doublets.getHammingDistance("war", "eagle"));
      System.out.println(doublets.getHammingDistance("barner", "bammer"));
   
      System.out.println(doublets.isWord("tiger"));
      System.out.println(doublets.isWord("eagle"));
      System.out.println(doublets.isWord("aubie"));
   
      System.out.println(doublets.getWordCount());
   
      System.out.println(doublets.isWordLadder(Arrays.asList("cat", "cot", "dot", "zot")));
      System.out.println(doublets.isWordLadder(Arrays.asList("cat", "cot", "dot", "bot")));      
      System.out.println(doublets.getNeighbors("riel"));
   
      System.out.println(doublets.getLadder("open", "shut"));
   
      System.out.println(doublets.getMinLadder("cat", "dog"));
   }
}

/*

RUNTIME OUTPUT

0
4
-1
2
true
true
false
267751
false
true
[liger, niger, tiler, timer, titer, tiges]
[cat, bat, eat, fat, gat, hat]
[cat, hat]

 */
