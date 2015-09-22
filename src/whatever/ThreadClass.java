/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatever;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erikas
 */

public class ThreadClass extends Thread{
    
    private final boolean replaceLtLetters;
    
    private final boolean useUppercaseLetters;
    private final boolean useLowercaseLetters;
    
    private final boolean convertEndings;
    private final boolean addNumbersAtTheEnd;
    private final File originalDictionary;
    private final MainFrame accessBridge;
    private final BufferedReader bufferedReader;
    private final Writer bufferedWriter;
    
    private final int inputCount;
    private final String inputList[];

    public ThreadClass(
                boolean addNumbersAtTheEnd,
                boolean replaceLtLetters,
                boolean useUppercaseLetters,
                boolean useLowercaseLetters,
                boolean convertEndings,
                File originalDictionary,
                String input,
                MainFrame accessBridge,
                BufferedReader bufferedReader,
                Writer bufferedWriter,
                int inputCount,
                String inputList[]) throws IOException {
        
        this.addNumbersAtTheEnd = addNumbersAtTheEnd;
        this.replaceLtLetters = replaceLtLetters;
        this.useUppercaseLetters = useUppercaseLetters;
        this.useLowercaseLetters = useLowercaseLetters;
        this.convertEndings = convertEndings;
        this.originalDictionary = originalDictionary;
        this.accessBridge = accessBridge;
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
        this.inputCount = inputCount;
        this.inputList = inputList;
        
    }
    
    @Override
    public void run(){
        try {
            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> temporaryList = new ArrayList<>();
            
            String line;
            int progressCounter = 0;
            
            while((line = bufferedReader.readLine()) != null) {
                list.clear();
                
                // Usually we don't do anything with words in less than 2 letters.
                if(line.length() >= 2){
                    
                    // Replace LTU letters
                    if(replaceLtLetters){
                        line = replaceLtLetters(line);
                    }
                    
                    // Replace first letters (uppercase, lowercase or both)
                    if(useLowercaseLetters || useUppercaseLetters){
                        if(useLowercaseLetters && useUppercaseLetters){
                            char c[] = line.toCharArray();
                            c[0] = Character.toUpperCase(c[0]);
                            list.add(new String(c));
                            
                            char a[] = line.toCharArray();
                            a[0] = Character.toLowerCase(a[0]);
                            list.add(new String(a));
                        }else{
                            if(useUppercaseLetters){
                                char c[] = line.toCharArray();
                                c[0] = Character.toUpperCase(c[0]);
                                line = new String(c);
                            }else {
                                char c[] = line.toCharArray();
                                c[0] = Character.toLowerCase(c[0]);
                                line = new String(c);
                            }
                            list.add(line);
                        }
                    }else{
                        list.add(line);
                    }
                    
                    // At this point we are using line from the list.
                    
                    // Convert endings (like Erikas --> Eriko)
                    if(convertEndings){
                        for(String s : list){
                            // must use string below, otherwise error
                            String tmp = s;
                            temporaryList.add(convertEnding(tmp));
                        }
                        for(String s : temporaryList){
                            list.add(s);
                        }
                        temporaryList.clear();
                    }
                    
                    // Add numbers at the end
                    if(addNumbersAtTheEnd){
                        for(String a : list){
                            for(int x = 0; x < inputCount; x++){
                                temporaryList.add(String.format("%s%s", a, inputList[x]));
                            }
                        }
                        for(String s : temporaryList){
                            list.add(s);
                        }
                        temporaryList.clear();
                    }
                    
                    // To avoid "Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException"
                    // error, use String tmp = x;
                    for(String x : list){
                        String tmp = x;
                        bufferedWriter.write(tmp + "\n");
                    }
                }
                accessBridge.progressCounterPlusPlus();
            }
            accessBridge.threadFinished();
        } catch (IOException ex) {
            Logger.getLogger(ThreadClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String replaceLtLetters(String line) {
        String tmp = line;
        
        tmp = tmp.replace('Ą', 'A');
        tmp = tmp.replace('Č', 'C');
        tmp = tmp.replace('Ę', 'E');
        tmp = tmp.replace('Ė', 'E');
        tmp = tmp.replace('Į', 'I');
        tmp = tmp.replace('Š', 'S');
        tmp = tmp.replace('Ų', 'U');
        tmp = tmp.replace('Ū', 'U');
        tmp = tmp.replace('Ž', 'Z');
        
        tmp = tmp.replace('ą', 'a');
        tmp = tmp.replace('č', 'c');
        tmp = tmp.replace('ę', 'e');
        tmp = tmp.replace('ė', 'e');
        tmp = tmp.replace('į', 'i');
        tmp = tmp.replace('š', 's');
        tmp = tmp.replace('ų', 'u');
        tmp = tmp.replace('ū', 'u');
        tmp = tmp.replace('ž', 'z');
        
        return tmp;
    }
    
    private String convertEnding(String line) {
        if(line.length()>=3){
            // 3 letters at the end
            if(line.substring(line.length()-3, line.length()).equals("tis")){
                line = String.format("%s%s", line.substring(0, line.length()-3), "cio");
                return line;
            }
        }
        
        if(line.length()>=2){
            // 2 letters at the end
            switch (line.substring(line.length()-2, line.length())) {
                case "as":
                    line = String.format("%s%s", line.substring(0, line.length()-2), "o");
                    return line;
                case "us":
                    line = String.format("%s%s", line.substring(0, line.length()-2), "aus");
                    return line;
                case "ys":
                    line = String.format("%s%s", line.substring(0, line.length()-2), "io");
                    return line;
                case "is":
                    line = String.format("%s%s", line.substring(0, line.length()-2), "io");
                    return line;
            }
        }
        
        if(line.length()>=1){
            // 1 letter at the end
            switch (line.substring(line.length()-1, line.length())) {
                case "a":
                    line = String.format("%s%s", line.substring(0, line.length()-1), "os");
                    return line;
                case "e":
                    line = String.format("%s%s", line.substring(0, line.length()-1), "es");
                    return line;
            }
        }
        return line;
    }
    
}
