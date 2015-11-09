package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fillinger on 11/9/15.
 */
public class FastaParser {

   public static Map<String, String> parseFASTA(File fastaFile)  throws IOException{
       HashMap<String, String> fastaSequences = new HashMap<>();

       FileReader fileReader = new FileReader(fastaFile);
       BufferedReader bufferedReader = new BufferedReader(fileReader);

       String currentLine = "";

       while((currentLine = bufferedReader.readLine()) != null){
           if (currentLine.contains(">")){
               String id = "";
               String strain = "";
               id = currentLine.split("\\.")[0].replace(">", "");
               strain = currentLine.substring(currentLine.lastIndexOf(";") + 1);

               fastaSequences.put(id, strain);
               //System.out.println(id +  " " + strain);
           }
       }
       return fastaSequences;
    }
}
