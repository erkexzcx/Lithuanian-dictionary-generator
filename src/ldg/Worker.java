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

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> tmpList = new ArrayList<>();

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
        while ((word = workersManager.readWord(updateUi)) != null) {

            list1.clear();
            list2.clear();
            //tmpList.clear(); // Not needed, since it is never used to manipulate data. Only for switching lists.

            // Populate list1 with words
            //==================================================================
            if (!uppercase && !lowercase) {
                list1.add(word); // neither uppercase neither lowercase, so let's just use original word
            } else {
                if (uppercase) {
                    list1.add((word.substring(0, 1).toUpperCase()).concat(word.substring(1)));
                }
                if (lowercase) {
                    list1.add((word.substring(0, 1).toLowerCase()).concat(word.substring(1)));
                }
            }
            //==================================================================

            // Populate list2 with words from list1:
            //==================================================================
            if (changeEndings) {

                // Copy list1 words to list2
                if (changeEndingsExportOriginals) {
                    list1.forEach((wordFromTheList) -> {
                        list2.add(wordFromTheList);
                    });
                }

                // What's cool about list1 at this stage? All words in it have equal length!
                int wLength = list1.get(0).length();

                // Use words from list1 to generate new words to list2
                list1.forEach((wordFromTheList) -> {
                    for (EndingPair ep : endingPairsArray) {
                        // Before we check if words ends with X, we then check if word is longer than X:
                        if (wLength > ep.getFromLength()) {
                            // Check if word ends with X:
                            if (wordFromTheList.endsWith(ep.getFrom())) {
                                // Change ending and add to list1
                                list2.add(
                                        (wordFromTheList.substring(0, wLength - ep.getFromLength())).concat(ep.getTo())
                                );
                            }
                        }
                    }
                });

                // Move everything from list1 and list2:
                list1.clear();
                tmpList = list1;
                list1 = list2;
                list2 = tmpList;

            }
            //==================================================================

            // Populate list2 with words from list1:
            //
            // Because this is a last step - optimise it a bit by directly writting
            // to a file instead of a list2.
            //==================================================================
            if (appendText) {

                // Copy list1 words to list2
                if (appendTextExportOriginals) {
                    writeToFile(list1);
                }

                // Use words from list1 and write them directly to file.
                list1.forEach((wordFromTheList) -> {
                    for (String a : appendTextArray) {
                        writeToFile(wordFromTheList.concat(a));
                    }
                });

            } else {
                // User does not want to append words - write everything form list1 to file:
                writeToFile(list1);
            }
            //==================================================================

        }

        // Clear lists at the end:
        list1.clear();
        list2.clear();
        tmpList.clear();
    }

    private void writeToFile(List<String> list) {
        list1.forEach((wordFromTheList) -> {
            workersManager.writeWord(wordFromTheList);
        });
    }

    private void writeToFile(String word) {
        workersManager.writeWord(word);
    }

}
