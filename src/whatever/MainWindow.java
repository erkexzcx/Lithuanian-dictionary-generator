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
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.mozilla.universalchardet.UniversalDetector;

/**
 *
 * @author Erikas
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainWindow() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle(String.format("%s - Version %s", appTitle, appVersion));
        jtextArea_numbersAtTheEnd.setLineWrap(true);
        jtextArea_numbersAtTheEnd.setText(defaultNumbersCommon);
        this.jfield_existingDictionaryPath.setText("");
        jprogressBar_progress.setStringPainted(true);

        //Create an information window just in RAM, but do not make it visible:
        informationWindow = new InformationWindow();
        informationWindow.setLocationRelativeTo(this);
        informationWindow.setTitle("Information");

        this.setVisible(true);
    }

    private InformationWindow informationWindow;
    public MainWindow mainFrame = this;

    private final String appTitle = "Lithuanian words dictionary generator";
    private final String appVersion = "1.09"; //will be used for releases only

    private final String defaultNumbersMost = "1, 12, 21, 23, 32, 123, 321, 122, "
            + "322, 223, 112, 01, 09, 08, 07, 06, 05, 04, 03, 02, 0, 001, 007, "
            + "10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 91, 92, 93, 94, 95, "
            + "96, 97, 98, 99, 00, 69, 96, 963, 369, 34, 45, 56, 67, 78, 89, 90, "
            + "98, 87, 76, 65, 54, 43, 951, 159, 49";
    private final String defaultNumbersCommon = "1, 0, 01, 12, 21, 23, 32, 123, 321, "
            + "122, 322, 223, 112, 911, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 91, "
            + "92, 93, 94, 95, 96, 97, 98, 99, 00, 49";
    private final String defaultNumbersBasic = "1, 0, 01, 123, 321, 12, 21, 23, 32";

    private File currentDictionaryFile = null;

    public static int threadsFinished;

    private Writer bufferedWriter = null;
    private BufferedReader bufferedReader = null;

    private final int cores = Runtime.getRuntime().availableProcessors();

    //For benchmarking
    public static long startTime;
    public static long stopTime;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbutton_chooseExistingDictionaryPath = new javax.swing.JButton();
        jfield_existingDictionaryPath = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtextArea_numbersAtTheEnd = new javax.swing.JTextArea();
        jcheckBox_useNumbersAtTheEnd = new javax.swing.JCheckBox();
        jcheckBox_convertLTUtoSimpleLetters = new javax.swing.JCheckBox();
        jcheckBox_useCapitalLetters = new javax.swing.JCheckBox();
        jcheckBox_useLowercaseLetters = new javax.swing.JCheckBox();
        jcheckBox_convertEndings = new javax.swing.JCheckBox();
        jbutton_generate = new javax.swing.JButton();
        jprogressBar_progress = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textField_newOutputName = new javax.swing.JTextField();
        button_defaultNumbersMost = new javax.swing.JButton();
        button_defaultNumbersCommon = new javax.swing.JButton();
        button_defaultNumbersBasic = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jbutton_chooseExistingDictionaryPath.setText("Choose dictionary");
        jbutton_chooseExistingDictionaryPath.setToolTipText("Choose the existing dictionary file, which you want to re-generate.");
        jbutton_chooseExistingDictionaryPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbutton_chooseExistingDictionaryPathActionPerformed(evt);
            }
        });

        jfield_existingDictionaryPath.setEditable(false);
        jfield_existingDictionaryPath.setToolTipText("Click the button to choose the existing dictionary file you want to modify.");

        jtextArea_numbersAtTheEnd.setColumns(20);
        jtextArea_numbersAtTheEnd.setRows(5);
        jtextArea_numbersAtTheEnd.setToolTipText("Numbers must be separated by a commas only. No other symbols, just only numbers, commas and spaces!");
        jtextArea_numbersAtTheEnd.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jtextArea_numbersAtTheEnd);

        jcheckBox_useNumbersAtTheEnd.setSelected(true);
        jcheckBox_useNumbersAtTheEnd.setText("Use given numbers at the end:");
        jcheckBox_useNumbersAtTheEnd.setToolTipText("This will add the given numbers from the field to the end.");
        jcheckBox_useNumbersAtTheEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcheckBox_useNumbersAtTheEndActionPerformed(evt);
            }
        });

        jcheckBox_convertLTUtoSimpleLetters.setSelected(true);
        jcheckBox_convertLTUtoSimpleLetters.setText("Replace LTU symbols.");
        jcheckBox_convertLTUtoSimpleLetters.setToolTipText("This will replace lithuanian symbols. Example: ą -> a, ž -> z...");

        jcheckBox_useCapitalLetters.setSelected(true);
        jcheckBox_useCapitalLetters.setText("Starts with UPPERCASE letter");
        jcheckBox_useCapitalLetters.setToolTipText("If none of these 2 are selected, the beginning letters won't be changed!");

        jcheckBox_useLowercaseLetters.setSelected(true);
        jcheckBox_useLowercaseLetters.setText("Starts with lowercase letter");
        jcheckBox_useLowercaseLetters.setToolTipText("If none of these 2 are selected, the beginning letters won't be changed!");

        jcheckBox_convertEndings.setText("Convert endings.");
        jcheckBox_convertEndings.setToolTipText("example: Erikas -> Eriko, Gabija -> Gabijos, Paulius -> Pauliaus, Sofa -> Sofos...");

        jbutton_generate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jbutton_generate.setText("GENERATE!");
        jbutton_generate.setToolTipText("Let's mess up with the systems!");
        jbutton_generate.setEnabled(false);
        jbutton_generate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbutton_generateActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jButton1.setText("Help / Information");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("New name:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        textField_newOutputName.setText("Output.txt");

        button_defaultNumbersMost.setText("Most");
        button_defaultNumbersMost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_defaultNumbersMostActionPerformed(evt);
            }
        });

        button_defaultNumbersCommon.setText("Common");
        button_defaultNumbersCommon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_defaultNumbersCommonActionPerformed(evt);
            }
        });

        button_defaultNumbersBasic.setText("Basic");
        button_defaultNumbersBasic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_defaultNumbersBasicActionPerformed(evt);
            }
        });

        jLabel2.setText("Author: Erikas Rudinskas (erikmnkl@gmail.com)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jprogressBar_progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbutton_generate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcheckBox_convertLTUtoSimpleLetters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcheckBox_convertEndings, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcheckBox_useLowercaseLetters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcheckBox_useCapitalLetters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbutton_chooseExistingDictionaryPath))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jfield_existingDictionaryPath)
                            .addComponent(textField_newOutputName)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcheckBox_useNumbersAtTheEnd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_defaultNumbersBasic, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_defaultNumbersCommon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_defaultNumbersMost, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbutton_chooseExistingDictionaryPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jfield_existingDictionaryPath, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textField_newOutputName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcheckBox_useCapitalLetters, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcheckBox_convertEndings, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcheckBox_useLowercaseLetters, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcheckBox_convertLTUtoSimpleLetters, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_defaultNumbersBasic)
                    .addComponent(button_defaultNumbersCommon)
                    .addComponent(button_defaultNumbersMost)
                    .addComponent(jcheckBox_useNumbersAtTheEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jprogressBar_progress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbutton_generate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //All done with this method. Checked twice, so far so good.
    //It stores it's file with its information on private File named "currentDictionaryFile".
    private void jbutton_chooseExistingDictionaryPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbutton_chooseExistingDictionaryPathActionPerformed
        JFileChooser jfileChooser_existingFile = new JFileChooser();
        File directory = new File(System.getProperty("user.home"));
        jfileChooser_existingFile.setCurrentDirectory(directory);
        int returnVal = jfileChooser_existingFile.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentDictionaryFile = jfileChooser_existingFile.getSelectedFile();
            jfield_existingDictionaryPath.setText(jfileChooser_existingFile.getSelectedFile().getAbsolutePath());
            jbutton_generate.setEnabled(true);
        }
    }//GEN-LAST:event_jbutton_chooseExistingDictionaryPathActionPerformed

    private boolean isInputGood(String input) {
        boolean good = true;

        // IF the first character is digit - OK.
        if (!Character.isDigit(input.charAt(0))) {
            good = false;
        }

        for (int x = 0; x < input.length(); x++) {
            if (!Character.isDigit(input.charAt(x)) && input.charAt(x) != ',') {
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

    // if nothing is detected, then it's UTF-8
    private String getEncoding(File file) throws FileNotFoundException, IOException {
        byte[] buf = new byte[4096];
        java.io.FileInputStream fis = new java.io.FileInputStream(file);
        UniversalDetector detector = new UniversalDetector(null);
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        String encodingToReturn;
        if (encoding != null) {
            encodingToReturn = encoding;
        } else {
            encodingToReturn = "UTF-8";
        }
        detector.reset();
        return encodingToReturn;
    }

    private void jbutton_generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbutton_generateActionPerformed

        startTime = System.currentTimeMillis();

        jbutton_generate.setEnabled(false);

        resetProgressBarStatus();

        threadsFinished = 0;

        boolean numberAtTheEnd = jcheckBox_useNumbersAtTheEnd.isSelected();
        boolean replaceLtLetters = jcheckBox_convertLTUtoSimpleLetters.isSelected();
        boolean useUppercaseLetters = jcheckBox_useCapitalLetters.isSelected();
        boolean useLowerCaseLetters = jcheckBox_useLowercaseLetters.isSelected();
        boolean convertEndings = jcheckBox_convertEndings.isSelected();
        String inputFromTheUser = jtextArea_numbersAtTheEnd.getText();
        String newOutputName = textField_newOutputName.getText();

        //set default output name if nothing is enterred:
        if ("".equals(newOutputName)) {
            newOutputName = "Output.txt";
            textField_newOutputName.setText(newOutputName);
        }
        if (newOutputName.charAt(newOutputName.length() - 1) == '.') {
            newOutputName = String.format("%s%s", newOutputName, "txt");
            textField_newOutputName.setText(newOutputName);
        }
        if (newOutputName.contains(".") == false) {
            newOutputName = String.format("%s%s", newOutputName, ".txt");
            textField_newOutputName.setText(newOutputName);
        }

        // Remove all spaces if any.
        String newInput = inputFromTheUser.replaceAll("\\s", "");

        // Check whether the input is OK.
        if (!isInputGood(newInput)) {
            return;
        }

        // Split user input into array
        String inputList[] = newInput.split(",");
        int countertmp = 0;
        for (int j = 0; j < inputList.length; j++) {
            if (inputList[j] != null) {
                countertmp++;
            }
        }
        int inputCount = countertmp;

        try {
            setProgressBarMaxValue(countLines(currentDictionaryFile));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (replaceLtLetters) {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentDictionaryFile.getAbsolutePath().substring(0, currentDictionaryFile.getAbsolutePath().lastIndexOf(File.separator)) + File.separator + newOutputName), "UTF-8"));
            } else {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentDictionaryFile.getAbsolutePath().substring(0, currentDictionaryFile.getAbsolutePath().lastIndexOf(File.separator)) + File.separator + newOutputName), getEncoding(currentDictionaryFile)));
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(currentDictionaryFile), getEncoding(currentDictionaryFile)));
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Thread> threadList = new ArrayList<>();

        for (int x = 0; x < cores; x++) {
            try {
                Thread thread = new ThreadClass(
                        numberAtTheEnd,
                        replaceLtLetters,
                        useUppercaseLetters,
                        useLowerCaseLetters,
                        convertEndings,
                        currentDictionaryFile,
                        inputFromTheUser,
                        mainFrame,
                        bufferedReader,
                        bufferedWriter,
                        inputCount,
                        inputList);
                threadList.add(thread);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Thread x : threadList) {
            x.start();
        }

    }//GEN-LAST:event_jbutton_generateActionPerformed

    private void jcheckBox_useNumbersAtTheEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcheckBox_useNumbersAtTheEndActionPerformed
        if (jcheckBox_useNumbersAtTheEnd.isSelected()) {
            jtextArea_numbersAtTheEnd.setEnabled(true);
            button_defaultNumbersBasic.setEnabled(true);
            button_defaultNumbersCommon.setEnabled(true);
            button_defaultNumbersMost.setEnabled(true);

        } else {
            jtextArea_numbersAtTheEnd.setEnabled(false);
            button_defaultNumbersBasic.setEnabled(false);
            button_defaultNumbersCommon.setEnabled(false);
            button_defaultNumbersMost.setEnabled(false);
        }
    }//GEN-LAST:event_jcheckBox_useNumbersAtTheEndActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        informationWindow.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void button_defaultNumbersBasicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_defaultNumbersBasicActionPerformed
        jtextArea_numbersAtTheEnd.setText(defaultNumbersBasic);
    }//GEN-LAST:event_button_defaultNumbersBasicActionPerformed

    private void button_defaultNumbersCommonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_defaultNumbersCommonActionPerformed
        jtextArea_numbersAtTheEnd.setText(defaultNumbersCommon);
    }//GEN-LAST:event_button_defaultNumbersCommonActionPerformed

    private void button_defaultNumbersMostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_defaultNumbersMostActionPerformed
        jtextArea_numbersAtTheEnd.setText(defaultNumbersMost);
    }//GEN-LAST:event_button_defaultNumbersMostActionPerformed

    public void progressCounterPlusPlus() {
        int value = jprogressBar_progress.getValue() + 1;
        jprogressBar_progress.setValue(value);
        double progress = (double) value * 100 / jprogressBar_progress.getMaximum();
        jprogressBar_progress.setString(String.format("%.2f%c", progress, '%'));
    }

    public void resetProgressBarStatus() {
        jprogressBar_progress.setValue(0);
        jprogressBar_progress.setString(String.format("%s", "Ready"));
    }

    public void setProgressBarMaxValue(int number) {
        jprogressBar_progress.setMaximum(number);
    }

    public void threadFinished() throws IOException {
        stopTime = System.currentTimeMillis();
        jprogressBar_progress.setValue(jprogressBar_progress.getMaximum());
        jprogressBar_progress.setString(String.format("%s", "100%"));
        long time = stopTime - startTime;
        JOptionPane.showMessageDialog(null, "Done in " + time + "ms! " + textField_newOutputName.getText() + " is stored at the same location.");
        jbutton_generate.setEnabled(true);
        bufferedReader.close();
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_defaultNumbersBasic;
    private javax.swing.JButton button_defaultNumbersCommon;
    private javax.swing.JButton button_defaultNumbersMost;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbutton_chooseExistingDictionaryPath;
    private javax.swing.JButton jbutton_generate;
    private javax.swing.JCheckBox jcheckBox_convertEndings;
    private javax.swing.JCheckBox jcheckBox_convertLTUtoSimpleLetters;
    private javax.swing.JCheckBox jcheckBox_useCapitalLetters;
    private javax.swing.JCheckBox jcheckBox_useLowercaseLetters;
    private javax.swing.JCheckBox jcheckBox_useNumbersAtTheEnd;
    private javax.swing.JTextField jfield_existingDictionaryPath;
    private javax.swing.JProgressBar jprogressBar_progress;
    private javax.swing.JTextArea jtextArea_numbersAtTheEnd;
    private javax.swing.JTextField textField_newOutputName;
    // End of variables declaration//GEN-END:variables

}
