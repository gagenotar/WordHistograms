// Gage Notargiacomo, Fall 2023
// This was a class project for Object Oriented in Java
// This program demonstrates how to use ArrayList and implement HashMaps. 
// It also includes splitting strings via characters and size groups.

import java.io.File; 
import java.io.BufferedReader; 
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Histogram{
    
    private HashMap<String, Integer> mHistogram;  
    
    public Histogram(){
        this.mHistogram = new HashMap<>();
    }
    
    /*
    This function counts the number of occurence of each letter group
    input: letter groups
    output: histogram
    
    example: input: ["an","an","or","or","or"]
             output: [<"an",2>, <"or",3>]
    */
    public void generateHistogram(ArrayList<String> letterGroups){
        int reps;
        ArrayList<String> temp = new ArrayList<>();

        // The temp list will hold each unique string within letterGroups
        for (String s : letterGroups)
        {
            if (!temp.contains(s))
            {
                temp.add(s);
            }
        }

        // For each time these keys appear in temp, a tally will be added and
        // stored into the HashMap Histogram
        for (String key : temp)
        {
            reps = 0;
            for (String s : letterGroups)
            {
                if (s.equals(key))
                    reps++;
            }
            mHistogram.put(key, reps);
        }
    }
    
    /*
    This function prints the histogram
    input: histogram
    output: histogram printed on the display
    
    example: input: [<"an",2>, <"or",3>]
             output:
    
    an: **
    or: ***
    
    */
    public void printHistogram(){

        // For each pair within the Histogram, print its key and value
        for (Map.Entry<String, Integer> pair : mHistogram.entrySet())
        {
            System.out.print(pair.getKey() + ": ");
            for (int i=0; i<pair.getValue(); i++)
            {
                System.out.print("*");
            }
        System.out.print("\n");
        } 
    }   
}





public class WordHistograms {  
    private ArrayList<String> mLetterGroups;
    private int mLetterGroupLen;
    
    public WordHistograms(int letterGroupLen){
        this.mLetterGroups = new ArrayList<>();
        this.mLetterGroupLen = letterGroupLen;
    }
    public void addWords(String line){
        
        String[] tokens = this.getTokens(line);
        this.splitTokenstoLetterGroups(tokens);
    }
    public ArrayList<String> getLetterGroups(){
        return (this.mLetterGroups);
    }
      
    /*
    This function splits a string into array of strings separated by space
    character
    input: a string
    output: array of words seperated by space character
    
    example: input: "object oriented"
             output: ["object", "oriented"]
    */
    private String[] getTokens(String line){ 
        String[] tokens = line.split("\\s");
     
        return tokens;
    }
    
    /*
    This function splits the tokens into letter groups according to 
    this.mLetterGroupLen
    input: array of tokens
    output: letterGroups of size this.mLetterGroupLen appended to 
    this.mLetterGroups
    
    example: input: "object" "oriented"
             output: this.mLetterGroups (of size 4) = this.mLetterGroups + 
                  ["obje","bjec", "ject","orie", "rien", "ient", "ente", "nted"]
    */
    private void splitTokenstoLetterGroups(String[] tokens){

        // For each word, it will be split into substrings of size Len
        for (String s : tokens)
        {
            for (int i=0; i<=s.length()-this.mLetterGroupLen; i++)
            {
                String sub = s.substring(i, i + this.mLetterGroupLen);
                this.mLetterGroups.add(sub);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        int length = Integer.parseInt(args[1]);

        // This temp file will be used to check if the filename exists
        // This is done to avoid throwing any errors
        File check = new File(filename);
        Scanner input = new Scanner(System.in);
        while (!check.exists())
        {
            System.out.println("Error opening file. Enter a new filename: ");
            filename = input.next();
            check = new File(filename);
        }
        input.close();

        FileReader inFile = new FileReader(filename);
        BufferedReader br = new BufferedReader(inFile);
        WordHistograms fileContents = new WordHistograms(length);

        // Contents will hold the entire file in one string and add it to
        // The newly made SentenceUtil
        String line;
        String contents = "";
        while ((line = br.readLine()) != null)
        {
            contents = contents.concat(line);
        }
        fileContents.addWords(contents);

        ArrayList<String> letterGroups = fileContents.getLetterGroups();
        Histogram newHistogram = new Histogram();
        newHistogram.generateHistogram(letterGroups);
        newHistogram.printHistogram();

        br.close();
    }
}
