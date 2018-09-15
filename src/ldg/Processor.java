package ldg;

import java.util.ArrayList;
import java.util.List;

public class Processor implements Runnable {

    Manager manager;

    boolean convertEndings;
    boolean convertEndingsIncludeOriginals;
    boolean appendText;
    boolean appendTextIncludeOriginals;
    boolean useLowercase;
    boolean useUppercase;
    EndingPair endingPairs[];
    String appendTextArray[];
    boolean updateUi;

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();

    public Processor(Manager manager,
            boolean convertEndings,
            boolean convertEndingsIncludeOriginals,
            boolean appendText,
            boolean appendTextIncludeOriginals,
            boolean useLowercase,
            boolean useUppercase,
            EndingPair endingPairs[],
            String[] appendTextArray,
            boolean updateUi) {
        this.manager = manager;
        this.convertEndings = convertEndings;
        this.convertEndingsIncludeOriginals = convertEndingsIncludeOriginals;
        this.appendText = appendText;
        this.appendTextIncludeOriginals = appendTextIncludeOriginals;
        this.useLowercase = useLowercase;
        this.useUppercase = useUppercase;
        this.endingPairs = endingPairs;
        this.appendTextArray = appendTextArray;
        this.updateUi = updateUi;
    }

    @Override
    public void run() {
        String word;
        String tmp;
        while ((word = manager.readWord(updateUi)) != null) {

            list1.clear();
            list2.clear();

            // Populate list1 with words
            //==================================================================
            if (useUppercase) {
                list1.add(
                    new StringBuilder().append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).toString()
                );
            }
            if (useLowercase) {
                list1.add(
                    new StringBuilder().append(Character.toLowerCase(word.charAt(0))).append(word.substring(1)).toString()
                );
            }
            if (!useUppercase && !useLowercase) {
                list1.add(word); // neither uppercase neither lowercase, so let's just use original word
            }
            //==================================================================

            // Populate list2 with words generated from list1:
            //==================================================================
            if (convertEndings) {

                // Copy list1 words to list2
                if (convertEndingsIncludeOriginals) {
                    list1.forEach((wordFromTheList) -> {
                        list2.add(wordFromTheList);
                    });
                }

                // What's cool about list1 at this stage? All words in it have equal length!
                int wLength = list1.get(0).length();

                // Use words from list1 to generate new words to list2
                list1.forEach((wordFromTheList) -> {
                    for (EndingPair ep : endingPairs) {
                        // Before we check if words ends with X, we then check if word is longer than X:
                        if (wLength > ep.getFromLength()) {
                            // Check if word ends with X:
                            if (wordFromTheList.endsWith(ep.getFrom())) {
                                // Change ending and add to list1
                                list2.add(
                                        wordFromTheList.substring(0, wLength - ep.getFromLength()) + ep.getTo()
                                );
                            }
                        }
                    }
                });

                // Move everything from list1 and list2:
                list1.clear();
                list1 = list2;
                list2 = new ArrayList<>();

            }
            //==================================================================

            // Populate list2 with words generated from list1:
            //==================================================================
            if (appendText) {

                // Copy list1 words to list2
                if (appendTextIncludeOriginals) {
                    list1.forEach((wordFromTheList) -> {
                        list2.add(wordFromTheList);
                    });
                }

                // Use words from list1 to generate new words to list2
                list1.forEach((wordFromTheList) -> {
                    for (String a : appendTextArray) {
                        list2.add(wordFromTheList + a);
                    }
                });

                // Switch list1 and list2:
                list1.clear();
                list1 = list2;
                list2 = new ArrayList<>();

            }
            //==================================================================

            // Write everything from list1 to output file:
            list1.forEach((wordFromTheList) -> {
                manager.writeWord(wordFromTheList);
            });

        }

        // Clear lists at the end:
        list1.clear();
        list2.clear();
    }

}
