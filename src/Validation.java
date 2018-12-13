

import java.io.File;
import java.io.IOException;

public class Validation {

    public static boolean validEndings(String text) {
        String trimmed = text.trim();
        if ("".equals(trimmed)) {
            return false;
        }

        String[] splitted = trimmed.split("\\n");
        for (String element : splitted) {
            if (!element.matches(".+=>.+")) {
                return false;
            }
        }
        return true;
    }

    public static boolean fileExists(File f) {
        return f.exists() && !f.isDirectory();
    }

    public static boolean validOutputFilePath(File f) {
        if (f.exists()) {
            return f.canWrite();
        } else {
            try {
                f.createNewFile();
                f.delete();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

}
