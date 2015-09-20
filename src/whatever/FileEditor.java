/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatever;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Erikas
 */
public class FileEditor extends Thread{
    
    private boolean replaceLtLetters;
    
    private boolean useUppercaseLetters;
    private boolean useLowercaseLetters;
    
    private boolean convertEndings;
    private boolean addNumbersAtTheEnd;
    private File originalDictionary;
    private MainFrame accessBridge;
    
    private int inputCount;
    private String inputList[];

    public FileEditor(
            boolean addNumbersAtTheEnd,
            boolean replaceLtLetters,
            boolean useUppercaseLetters,
            boolean useLowercaseLetters,
            boolean convertEndings,
            File originalDictionary,
            String input,
            MainFrame accessBridge) throws IOException {
        
        this.addNumbersAtTheEnd = addNumbersAtTheEnd;
        this.replaceLtLetters = replaceLtLetters;
        this.useUppercaseLetters = useUppercaseLetters;
        this.useLowercaseLetters = useLowercaseLetters;
        this.convertEndings = convertEndings;
        this.originalDictionary = originalDictionary;
        this.accessBridge = accessBridge;
        
        // Remove all spaces if any.
        String newInput = input.replaceAll("\\s","");
        
        // Check whether the input is OK.
        if(!isInputGood(newInput)){
            return;
        }
        
        String inputListTMP[] = newInput.split(",");
        this.inputList = inputListTMP;
        int countertmp = 0;
        for(int j=0; j<inputList.length; j++) {
            if(inputList[j] != null) {
                countertmp++;
            }
        }
        inputCount = countertmp;
        
        accessBridge.setProgressBarMaxValue(countLines(originalDictionary));
        accessBridge.setProgressBarStatus(0);
        
    }
    
    @Override
    public void run(){
        try {
            doTheStuff();
        } catch (IOException ex) {
            Logger.getLogger(FileEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean isInputGood(String input){
        boolean good = true;
        
        // IF the first character is digit - OK.
        if(!Character.isDigit(input.charAt(0))){
            good = false;
        }
        
        for(int x = 0; x<input.length(); x++){
            if(!Character.isDigit(input.charAt(x)) && input.charAt(x)!=','){
                // there is no true value
                good = false;
                break;
            }
        }
        return good;
    }
    
    private static int countLines(File filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
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

    private void doTheStuff() throws FileNotFoundException, IOException {
        
        Writer bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(originalDictionary.
                                getAbsolutePath().substring(0, originalDictionary.getAbsolutePath().lastIndexOf(File.separator)) + 
                                File.separator + "output.txt"), "Windows-1257"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(originalDictionary), "Windows-1257"));
        
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> temporaryList = new ArrayList<>();
        
        String line = null;
        
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
            accessBridge.setProgressBarStatus(progressCounter);
            progressCounter++;
        }
        
        bufferedReader.close();
        bufferedWriter.flush();
        bufferedWriter.close();
        
        accessBridge.setProgressBarCompleted();
        JOptionPane.showMessageDialog(null, "Done!");
        accessBridge.unblockGenerateButton();
    }
}
