
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

/*
WorkerManager is like a man in the middle between MainWindow and Worker.
Worker takes words from WorkerManager and submits words to WorkerManager while
WorkerManager also updates progress bar.
 */
public class WorkersManager {

	private final javax.swing.JProgressBar progressBar;
	private final double linesCount;
	private final File inputFile;
	private final File outputFile;

	private double progressCounter = 0;

	private final BufferedReader bufferedReader;
	private final BufferedWriter bufferedWriter;

	public WorkersManager(JProgressBar progressBar, double linesCount, File inputFile, File outputFile) throws FileNotFoundException, IOException {
		this.progressBar = progressBar;
		this.linesCount = linesCount;
		this.inputFile = inputFile;
		this.outputFile = outputFile;

		// Open input file for reading line by line:
		bufferedReader = new BufferedReader(new FileReader(inputFile));

		// Open output file for writting:
		bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

	}

	/**
	 * Returns word from input dictionary file.
	 *
	 * @param updateUi
	 * @return string if read. null if end of file.
	 */
	public synchronized String readFromFile(boolean updateUi) {

		progressCounter++;
		if (updateUi) {
			double res = (progressCounter * 100) / linesCount;
			progressBar.setValue((int) res);
			progressBar.setString(String.format("%.2f%%", (res > 100 ? 100.0 : res)));
		}

		try {
			return bufferedReader.readLine();
		} catch (IOException ex) {
			Logger.getLogger(WorkersManager.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}
		return null;
	}

	/**
	 * Writes given text to output dictionary file.
	 *
	 * @param text to write to file.
	 */
	public synchronized void writeToFile(String text) {
		try {
			bufferedWriter.write(text);
			//bufferedWriter.flush(); // Not needed. Buffer will automatically flush (saves contents to disk) when fills up.
		} catch (IOException ex) {
			Logger.getLogger(WorkersManager.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}
	}

	public void cleanUp() throws IOException {
		bufferedReader.close();
		bufferedWriter.close();
	}

}
