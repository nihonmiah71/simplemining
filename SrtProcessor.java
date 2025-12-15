import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Java program that reads an SRT subtitle file and converts it into a
 * clean text file based on specific rules.
 *
 * The program handles the following:
 * 1. Removes all timestamps.
 * 2. Consolidates dialogue from the same character into a single line.
 * It identifies characters by names in parentheses (e.g., "(Character Name)")
 * at the start of a line. If a line lacks a character name, it assumes the
 * dialogue continues from the previous speaker. Non-dialogue lines (e.g., sound
 * descriptions) are ignored.
 * 3. Replaces various punctuation marks with a single space. The long dash symbol
 * ('ー') is an exception and is preserved.
 */
public class SrtProcessor {

    // Regex to match and remove Japanese and English punctuation marks,
    // while preserving the long dash 'ー'.
    private static final String PUNCTUATION_REGEX = "[\\p{Punct}&&[^ー]]|[。？！、…]";

    // Regex to find a character name in parentheses at the start of a line,
    // including an optional reading in nested parentheses.
    // E.g., （上条(かみじょう)）
    private static final Pattern CHARACTER_PATTERN = Pattern.compile("^（([^（）]+)(?:（[^（）]+）)?）\\s*(.*)");

    public static void main(String[] args) {
        // Define the input and output file paths.
        String inputFilePath = "input.srt"; // Replace with your input file path.
        String outputFilePath = "output.txt"; // The output file will be created here.

        // Call the core processing method.
        processSrtFile(inputFilePath, outputFilePath);
    }

    /**
     * Reads an SRT file line by line, processes it, and writes the
     * result to a new text file.
     *
     * @param inputFilePath  The path to the SRT file.
     * @param outputFilePath The path to the output text file.
     */
    public static void processSrtFile(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            String currentCharacter = null;
            StringBuilder currentDialogue = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                // Ignore empty lines, sequence numbers, and timestamp lines.
                if (line.trim().isEmpty() || line.matches("\\d+") || line.contains("-->")) {
                    continue;
                }

                Matcher matcher = CHARACTER_PATTERN.matcher(line);

                if (matcher.find()) {
                    // A new character name was found.
                    String newCharacter = matcher.group(1).trim();
                    String dialoguePart = matcher.group(2).trim();

                    if (currentCharacter != null && !currentCharacter.equals(newCharacter)) {
                        // A new speaker has started. Write the previous speaker's dialogue.
                        writeDialogue(writer, currentCharacter, currentDialogue);
                        currentDialogue.setLength(0); // Clear for the new dialogue.
                    }

                    currentCharacter = newCharacter;
                    if (currentDialogue.length() > 0) {
                        currentDialogue.append(" "); // Add a space for continuity.
                    }
                    currentDialogue.append(removePunctuation(dialoguePart));

                } else if (currentCharacter != null) {
                    // No new character name, assume the dialogue continues.
                    String cleanedLine = removePunctuation(line);
                    if (!cleanedLine.isEmpty()) {
                        if (currentDialogue.length() > 0) {
                             currentDialogue.append(" ");
                        }
                        currentDialogue.append(cleanedLine);
                    }
                }
                // Lines that don't match the character pattern and have no currentCharacter
                // are considered non-dialogue (e.g., sound effects) and are ignored.
            }

            // Write the last buffered dialogue to the file.
            if (currentCharacter != null || currentDialogue.length() > 0) {
                writeDialogue(writer, currentCharacter, currentDialogue);
            }

            System.out.println("Processing complete. Output written to " + outputFilePath);

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Helper method to replace all specified punctuation with a single space.
     * @param text The string to clean.
     * @return The cleaned string.
     */
    private static String removePunctuation(String text) {
        return text.replaceAll(PUNCTUATION_REGEX, " ").trim();
    }

    /**
     * Helper method to write the consolidated dialogue to the output file.
     * @param writer The BufferedWriter to write to.
     * @param character The name of the character.
     * @param dialogue The consolidated dialogue.
     * @throws IOException If a write error occurs.
     */
    private static void writeDialogue(BufferedWriter writer, String character, StringBuilder dialogue) throws IOException {
        if (dialogue.length() > 0) {
            writer.write("[" + character + "] " + dialogue.toString().trim());
            writer.newLine();
        }
    }
}
