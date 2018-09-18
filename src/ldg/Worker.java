package ldg;

import java.util.ArrayList;
import java.util.List;

public class Worker implements Runnable {

    WorkersManager workersManager;
    boolean changeEndings;
    boolean changeEndingsExportOriginals;
    boolean appendText;
    boolean appendTextExportOriginals;
    boolean uppercase;
    boolean lowercase;
    EndingPair[] endingPairsArray;
    String appendTextArray[];
    boolean updateUi;

    List<String> list1 = new ArrayList<>(); // Primary list
    List<String> list2 = new ArrayList<>(); // Secondary list

    StringBuilder sb = new StringBuilder(); // Build string here before writting it to file

    public Worker(
            WorkersManager workersManager,
            boolean changeEndings,
            boolean changeEndingsExportOriginals,
            boolean appendText,
            boolean appendTextExportOriginals,
            boolean uppercase,
            boolean lowercase,
            EndingPair endingPairsArray[],
            String[] appendTextArray,
            boolean updateUi) {
        this.workersManager = workersManager;
        this.changeEndings = changeEndings;
        this.changeEndingsExportOriginals = changeEndingsExportOriginals;
        this.appendText = appendText;
        this.appendTextExportOriginals = appendTextExportOriginals;
        this.uppercase = uppercase;
        this.lowercase = lowercase;
        this.endingPairsArray = endingPairsArray;
        this.appendTextArray = appendTextArray;
        this.updateUi = updateUi;
    }

    @Override
    public void run() {
        String word;
        String tmp;

        boolean changeAndAppend = (changeEndings && appendText);
        boolean lowercaseAndUppercase = (uppercase && lowercase);

        while ((word = workersManager.readWord(updateUi)) != null) {

            // Change case of first letter as per user request and put it into list1:
            if (lowercaseAndUppercase) {
                // lowercase and uppercase:
                String theFirst = word.substring(0, 1);
                String theRest = word.substring(1);
                list1.add((theFirst.toUpperCase()).concat(theRest));
                list1.add((theFirst.toLowerCase()).concat(theRest));
            } else if (uppercase) {
                // uppercase only:
                list1.add((word.substring(0, 1).toUpperCase()).concat(word.substring(1)));
            } else if (lowercase) {
                // lowercase only:
                list1.add((word.substring(0, 1).toLowerCase()).concat(word.substring(1)));
            } else {
                // Do not change first letter case:
                list1.add(word);
            }

            // list1 now contains the required words - all the same length:
            if (changeAndAppend) {
                //##############################################################
                //*** Change + append ***//

                // 'Change endings' part starts here:
                if (changeEndingsExportOriginals) {
                    // Put words from list1 to list2
                    list1.forEach((wordFromTheList) -> {
                        list2.add(wordFromTheList);
                    });
                }

                // Currently all words in list1 are equal length:
                int wordLength = word.length();

                // Change endings and add them to list2:
                list1.forEach((wordFromTheList) -> {
                    for (EndingPair ep : endingPairsArray) {
                        // If word ends with X
                        if (wordFromTheList.endsWith(ep.getFrom())) {
                            // Change ending with X
                            list2.add(
                                    (wordFromTheList.substring(0, wordLength - ep.getFromLength())).concat(ep.getTo())
                            );
                            break; // Go to the next word in the list1.
                        }
                    }
                });

                // 'Append text' part starts here:
                if (appendTextExportOriginals) {
                    writeToSB(list2);
                }

                list2.forEach((wordFromTheList) -> {
                    for (String a : appendTextArray) {
                        writeToSBRaw(wordFromTheList);
                        writeToSB(a);
                    }
                });

                // Because we used list2 - clear it:
                list2.clear();

                //##############################################################
            } else if (!changeEndings && appendText) {
                //##############################################################
                //*** Append only ***//

                if (appendTextExportOriginals) {
                    writeToSB(list1);
                }

                list1.forEach((wordFromTheList) -> {
                    for (String a : appendTextArray) {
                        writeToSBRaw(wordFromTheList);
                        writeToSB(a);
                    }
                });

                //##############################################################
            } else if (changeEndings && !appendText) {
                //##############################################################
                //*** Change only ***//

                if (changeEndingsExportOriginals) {
                    writeToSB(list1);
                }

                // Currently all words in list1 are equal length
                int wordLength = list1.get(0).length();

                // Change endings and write them to file:
                list1.forEach((wordFromTheList) -> {
                    for (EndingPair ep : endingPairsArray) {
                        // If word ends with X
                        if (wordFromTheList.endsWith(ep.getFrom())) {
                            // Change ending with X
                            writeToSBRaw(wordFromTheList.substring(0, wordLength - ep.getFromLength()));
                            writeToSB(ep.getTo());
                            break; // Go to the next word in the list1.
                        }
                    }
                });

                //##############################################################
            } else {
                //##############################################################
                //*** Nothing ***//

                writeToSB(list1);

                //##############################################################
            }

            // Write sb contents to file:
            flushSB();

            // Always clear list1 after using it:
            list1.clear();

        }

    }

    private void writeToSB(List<String> list) {
        list.forEach((wordFromTheList) -> {
            sb.append(wordFromTheList).append('\n');
        });
    }

    private void writeToSB(String word) {
        sb.append(word).append('\n');
    }

    private void writeToSBRaw(String word) {
        sb.append(word);
    }

    /**
     * Writes StringBuilder 'sb' object to file + resets it.
     */
    private void flushSB() {
        // Write to file:
        workersManager.writeWord(sb.toString());
        // Also reset it:
        sb.setLength(0);
    }

}
