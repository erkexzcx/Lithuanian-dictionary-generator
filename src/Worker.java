

import java.util.ArrayList;
import java.util.List;

public class Worker extends Thread {

    private static WorkersManager workersManager;
    private static boolean changeEndings;
    private static boolean changeEndingsExportOriginals;
    private static boolean appendText;
    private static boolean appendTextExportOriginals;
    private static boolean uppercase;
    private static boolean lowercase;
    private static EndingPair[] endingPairsArray;
    private static String appendTextArray[];
	
    private static boolean changeAndAppend;
    private static boolean lowercaseAndUppercase;

    /*
    Reading words from input dictionary as well as writting words to output
    dictionary requires syncrhonization. However, synchronization is expensive,
    so we use 'sb' object to build a local "buffer", which we later write to
    output dictionary (file).
     */
    private final StringBuilder sb = new StringBuilder(); // Build string here before writting it to file
	
    private List<String> list1; // Primary list
    private List<String> list2; // Secondary list
	
	private final boolean updateUi;

    public Worker(boolean updateUi) {
        this.updateUi = updateUi;
    }
	
    public static void setConfiguration(
            WorkersManager workersManager,
            boolean changeEndings,
            boolean changeEndingsExportOriginals,
            boolean appendText,
            boolean appendTextExportOriginals,
            boolean uppercase,
            boolean lowercase,
            EndingPair endingPairsArray[],
            String[] appendTextArray){
        Worker.workersManager = workersManager;
        Worker.changeEndings = changeEndings;
        Worker.changeEndingsExportOriginals = changeEndingsExportOriginals;
        Worker.appendText = appendText;
        Worker.appendTextExportOriginals = appendTextExportOriginals;
        Worker.uppercase = uppercase;
        Worker.lowercase = lowercase;
        Worker.endingPairsArray = endingPairsArray;
        Worker.appendTextArray = appendTextArray;
		
		// Additional variables:
		changeAndAppend = (changeEndings && appendText);
		lowercaseAndUppercase = (uppercase && lowercase);
    }

    @Override
    public void run() {
		
		// Since we already know the max required capacity of the list - optimise then:
		int maxListSize = 4 + (4 * appendTextArray.length);
		list1 = new ArrayList<>(maxListSize);
		list2 = new ArrayList<>(maxListSize);
		
        String word;
        while ((word = workersManager.readFromFile(updateUi)) != null) {

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
                        writeToSb(wordFromTheList);
                        writeToSbLine(a);
                    }
                });

                // Because we used list2 - clear it:
                list2.clear();

                //##############################################################
            } else if (changeEndings) {
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
                            writeToSb(wordFromTheList.substring(0, wordLength - ep.getFromLength()));
                            writeToSbLine(ep.getTo());
                            break; // Go to the next word in the list1.
                        }
                    }
                });

                //##############################################################
            } else if (appendText) {
                //##############################################################
                //*** Append only ***//

                if (appendTextExportOriginals) {
                    writeToSB(list1);
                }

                list1.forEach((wordFromTheList) -> {
                    for (String a : appendTextArray) {
                        writeToSb(wordFromTheList);
                        writeToSbLine(a);
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

    private void writeToSbLine(String word) {
        sb.append(word).append('\n');
    }

    private void writeToSb(String word) {
        sb.append(word);
    }

    /**
     * Writes StringBuilder 'sb' object to file + resets it.
     */
    private void flushSB() {
        // Write to file:
        workersManager.writeToFile(sb.toString());
        // Also reset it:
        sb.setLength(0);
    }

}
