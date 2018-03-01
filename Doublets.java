import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Collections;


/**
 * Doublets.java
 * Provides an implementation of the WordLadderGame interface. The lexicon
 * is stored as a TreeSet of Strings.
 *
 * @author Your Name (you@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2016-04-07
 */
public class Doublets implements WordLadderGame {

   ////////////////////////////////////////////
   // DON'T CHANGE THE FOLLOWING TWO FIELDS. //
   ////////////////////////////////////////////

   // A word ladder with no words. Used as the return value for the ladder methods
   // below when no ladder exists.
   
   List<String> EMPTY_LADDER = new ArrayList<>();

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   
   TreeSet<String> lexicon;
   
   private TreeSet<String> visited = new TreeSet<>();

   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
    
   public Doublets(InputStream in) {
      try {
         lexicon = new TreeSet<String>();
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            
            lexicon.add(str.toLowerCase());
            
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

   ///////////////////////////////////////////////////////////////////////////////
   // Fill in implementations of all the WordLadderGame interface methods here. //
   ///////////////////////////////////////////////////////////////////////////////

   public int getHammingDistance(String str1, String str2) {
   
      if (str1.length() != str2.length()) {
         return -1;
      }
      
      int count = 0;
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            count++;
         }
      }
      return count;
   }
   
   public List<String> getLadder(String start, String end) {
      if (!isWord(start) || !isWord(end)) {
         return EMPTY_LADDER;
      }
      
      if (start.length() != end.length()) {
         return EMPTY_LADDER;
      } 
       
      List<String> ladder = new ArrayList<>(); 
      if (start.equals(end)) {
         ladder.add(start);
         return ladder;
      } 
       
      Deque<String> stack = new ArrayDeque<>();  
      visit(start);
      ladder.add(start);
      stack.addLast(start);
      while (!stack.isEmpty()) {
         String position = stack.peekLast();
         String neighbor = getFirstUnvisited(getNeighbors(position));  
         if (neighbor != null) {
            stack.addLast(neighbor);
            visit(neighbor); 
         } 
           
         else {     
            stack.removeLast();
         } 
         
         if (stack.contains(end)) {
            return Arrays.<String>asList(stack.toArray(new String[]{}));
         }    
      } 
      return EMPTY_LADDER;  
   }
   
   public List<String> getMinLadder(String start, String end) {
      visited.clear();
      List<String> ladder = new ArrayList<String>();
   
      if (start.equals(end)) {
         ladder.add(start);
         return ladder;
      }
      
      if (!isWord(start) || !isWord(end)) {
         return EMPTY_LADDER;
      }
      
      if (start.length() != end.length()) {
         return EMPTY_LADDER;
      }  
       
      Deque<Node> queue = new ArrayDeque<Node>();
      visit(start);
      queue.addLast(new Node(start, null));
      while (!queue.isEmpty()) {
         Node n = queue.removeFirst();
         String position = n.word;
         for (String neighbor : getNeighbors(position)) {
            if (!isVisited(neighbor)) {
               visit(neighbor);
               queue.addLast(new Node(neighbor, n));   
            } 
         }
         
         if (n.word.equals(end)) {
            while (!ladder.contains(start)) {
               ladder.add(n.word);
               n = n.parent;
            }
            
            Collections.reverse(ladder);
            return ladder;
         }
      }
      
      return EMPTY_LADDER;
   }
   
   private class Node {
      String word;
      Node parent;
   
      public Node(String w, Node p) {
         word = w;
         parent = p;
      }
   }
 
   private boolean isVisited(String s) {
      return visited.contains(s);
   }
   
   private void visit(String str) {
      visited.add(str);
   }
   
   private String getFirstUnvisited(List<String> tree) {
      for (String word : tree) {
         if (!isVisited(word)) {
            return word;
         }
      }
      return null;
   }
          
   public boolean isWordLadder(List<String> sequence) {
   
      if (sequence == null || sequence.size() < 1) {
         return false;
      }
      
      int i = 0;
   
      while (i < sequence.size() - 1)  {
         if (!lexicon.contains(sequence.get(i)) || 
             getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1) {
            return false;  
         }
         i++;  
      } 
      
      if (!lexicon.contains(sequence.get(i))) {
         return false;
      }
    
      return true;
   }
   
   public boolean isWord(String str) {
      return (lexicon.contains(str));       
   }
   
   public int getWordCount() {
      return lexicon.size();
   }
   
   public List<String> getNeighbors(String word) {
      List<String> list = new ArrayList<String>();
      char ch[] = {'a','b','c','d','e','f','g','h','i','j','k'
                  ,'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
      char array[] = word.toCharArray();
      
      int j = 0;
      while (j < array.length) {
         for (int i = 0; i < ch.length; i++) {
            array[j] = ch[i];
            String newWord = new String(array);
            if (lexicon.contains(newWord) && !newWord.equals(word)) {
               list.add(newWord);
            } 
         }  
         j++;
         array = word.toCharArray();
      } 
                    
      return list;
   }
                 
}

