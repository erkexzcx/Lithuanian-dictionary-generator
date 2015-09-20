/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatever;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Erikas
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle(String.format("%s - Version %s", appTitle, appVersion));
        jtextArea_numbersAtTheEnd.setLineWrap(true);
        jtextArea_numbersAtTheEnd.setText(defaultNumbers);
        this.jfield_existingDictionaryPath.setText("");
        jprogressBar_progress.setStringPainted(true);
        
        this.setVisible(true);
    }
    
    public MainFrame mainFrame = this;
    
    private final String appTitle = "Lithuanian words dictionary generator";
    private final String appVersion = "1.01";
    
    // Well, some of the numbers is what my GF told me that her friends use :D
    private final String defaultNumbers = 
            "1, 12, 21, 23, 32, 123, 321, 122, 322, 223, 112, 01, 09, 08, 07, 06, 05,"
            + " 04, 03, 02, 0, 001, 007, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 69,"
            + " 96, 963, 369, 34, 45, 56, 67, 78, 89, 90, 98, 87, 76, 65, 54, 43, 951, 159";
    
    // Shares between 2 buttons - current dictionary and it's new directory
    private File currentDictionaryFile = null;
    
    // Being set when the method isInputCorrect() is executed.
    // This is the Final string if the isInputCorrect() returned true.
    
    public void setProgressBarMaxValue(int number){
        jprogressBar_progress.setMaximum(number);
    }
    
    public void setProgressBarStatus(int number){
        jprogressBar_progress.setValue(number);
        double progress = number * 100/jprogressBar_progress.getMaximum();
        jprogressBar_progress.setString(String.format("%.0f%c", progress, '%'));
    }
    
    public void setProgressBarCompleted(){
        jprogressBar_progress.setValue(jprogressBar_progress.getMaximum());
        jprogressBar_progress.setString(String.format("%s", "100%"));
    }
    
    public void unblockGenerateButton(){
        jbutton_generate.setEnabled(true);
    }

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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtextArea_numbersAtTheEnd = new javax.swing.JTextArea();
        jbutton_resetToDefaultNumbers = new javax.swing.JButton();
        jcheckBox_useNumbersAtTheEnd = new javax.swing.JCheckBox();
        jcheckBox_convertLTUtoSimpleLetters = new javax.swing.JCheckBox();
        jcheckBox_useCapitalLetters = new javax.swing.JCheckBox();
        jcheckBox_useLowercaseLetters = new javax.swing.JCheckBox();
        jcheckBox_convertEndings = new javax.swing.JCheckBox();
        jbutton_generate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jprogressBar_progress = new javax.swing.JProgressBar();

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

        jLabel1.setText("Numbers to add at the end:");

        jtextArea_numbersAtTheEnd.setColumns(20);
        jtextArea_numbersAtTheEnd.setRows(5);
        jtextArea_numbersAtTheEnd.setToolTipText("Numbers must be separated by a commas only. No other symbols, just only numbers, commas and spaces!");
        jScrollPane1.setViewportView(jtextArea_numbersAtTheEnd);

        jbutton_resetToDefaultNumbers.setText("Reset to default");
        jbutton_resetToDefaultNumbers.setToolTipText("Overwrite what was entered to the field by the user to default numbers.");
        jbutton_resetToDefaultNumbers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbutton_resetToDefaultNumbersActionPerformed(evt);
            }
        });

        jcheckBox_useNumbersAtTheEnd.setSelected(true);
        jcheckBox_useNumbersAtTheEnd.setText("Use given numbers at the end.");
        jcheckBox_useNumbersAtTheEnd.setToolTipText("This will add the given numbers from the field to the end.");

        jcheckBox_convertLTUtoSimpleLetters.setSelected(true);
        jcheckBox_convertLTUtoSimpleLetters.setText("Replace LTU symbols.");
        jcheckBox_convertLTUtoSimpleLetters.setToolTipText("This will replace lithuanian symbols. Example: ą -> a, ž -> z...");

        jcheckBox_useCapitalLetters.setSelected(true);
        jcheckBox_useCapitalLetters.setText("Use capital only at the beginning.");
        jcheckBox_useCapitalLetters.setToolTipText("If none of these 2 are selected, the beginning letters won't be changed!");

        jcheckBox_useLowercaseLetters.setSelected(true);
        jcheckBox_useLowercaseLetters.setText("Use lowecase only at the beginning.");
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("New generated dictionary will be at the same directory! named \"output.txt\"");
        jLabel2.setFocusable(false);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jprogressBar_progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbutton_chooseExistingDictionaryPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfield_existingDictionaryPath))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcheckBox_convertLTUtoSimpleLetters, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcheckBox_useNumbersAtTheEnd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jcheckBox_useLowercaseLetters, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                .addGap(249, 249, 249))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jcheckBox_useCapitalLetters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcheckBox_convertEndings)
                                .addGap(134, 134, 134))))
                    .addComponent(jbutton_generate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbutton_resetToDefaultNumbers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbutton_chooseExistingDictionaryPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jfield_existingDictionaryPath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcheckBox_useNumbersAtTheEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcheckBox_useCapitalLetters, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcheckBox_convertEndings, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcheckBox_convertLTUtoSimpleLetters, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcheckBox_useLowercaseLetters, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbutton_resetToDefaultNumbers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jprogressBar_progress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbutton_generate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            if(returnVal == JFileChooser.APPROVE_OPTION){
                currentDictionaryFile = jfileChooser_existingFile.getSelectedFile();
                jfield_existingDictionaryPath.setText(jfileChooser_existingFile.getSelectedFile().getAbsolutePath());
                jbutton_generate.setEnabled(true);
            }
    }//GEN-LAST:event_jbutton_chooseExistingDictionaryPathActionPerformed

    // All done with this method. Checked twice.
    private void jbutton_resetToDefaultNumbersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbutton_resetToDefaultNumbersActionPerformed
        jtextArea_numbersAtTheEnd.setText(defaultNumbers);
    }//GEN-LAST:event_jbutton_resetToDefaultNumbersActionPerformed

    private void jbutton_generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbutton_generateActionPerformed
        
        jbutton_generate.setEnabled(false);
        
        boolean numberAtTheEnd = jcheckBox_useNumbersAtTheEnd.isSelected();
        boolean replaceLtLetters = jcheckBox_convertLTUtoSimpleLetters.isSelected();
        boolean useUppercaseLetters = jcheckBox_useCapitalLetters.isSelected();
        boolean useLowerCaseLetters = jcheckBox_useLowercaseLetters.isSelected();
        boolean convertEndings = jcheckBox_convertEndings.isSelected();
        String inputFromTheUser = jtextArea_numbersAtTheEnd.getText();
        
        
        
        try {
            Thread thread = new FileEditor(
                    numberAtTheEnd,
                    replaceLtLetters,
                    useUppercaseLetters,
                    useLowerCaseLetters,
                    convertEndings,
                    currentDictionaryFile,
                    inputFromTheUser,
                    mainFrame);
            thread.start();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jbutton_generateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbutton_chooseExistingDictionaryPath;
    private javax.swing.JButton jbutton_generate;
    private javax.swing.JButton jbutton_resetToDefaultNumbers;
    private javax.swing.JCheckBox jcheckBox_convertEndings;
    private javax.swing.JCheckBox jcheckBox_convertLTUtoSimpleLetters;
    private javax.swing.JCheckBox jcheckBox_useCapitalLetters;
    private javax.swing.JCheckBox jcheckBox_useLowercaseLetters;
    private javax.swing.JCheckBox jcheckBox_useNumbersAtTheEnd;
    private javax.swing.JTextField jfield_existingDictionaryPath;
    private javax.swing.JProgressBar jprogressBar_progress;
    private javax.swing.JTextArea jtextArea_numbersAtTheEnd;
    // End of variables declaration//GEN-END:variables
}
