/*
∗ @file: Proj4.java
∗ @description: Reads a dataset from a file, organizes it into sorted,
shuffled, and reversed lists, and measures the time to insert, search,
and delete each list in a hash table.
∗ @author: Neil Sawhney
∗ @date: December 4, 2025
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME

        ArrayList<String> dataList = new ArrayList<>();

        // Read the dataset
        for (int i = 0; i < numLines && inputFileNameScanner.hasNextLine(); i++) {
            dataList.add(inputFileNameScanner.nextLine().trim());
        }

        // Close file resources
        inputFileNameScanner.close();
        inputFileNameStream.close();

        // Prepare lists
        ArrayList<String> sortedList = new ArrayList<>(dataList);
        ArrayList<String> shuffledList = new ArrayList<>(dataList);
        ArrayList<String> reversedList = new ArrayList<>(dataList);

        Collections.sort(sortedList);
        Collections.shuffle(shuffledList);
        Collections.sort(reversedList, Collections.reverseOrder());

        // Measure operations
        measureHashOperations(sortedList, "Already Sorted");
        measureHashOperations(shuffledList, "Shuffled");
        measureHashOperations(reversedList, "Reversed");
    }

    // Measures insert, search, and delete times for a list
    public static void measureHashOperations(ArrayList<String> list, String label) throws IOException {
        SeparateChainingHashTable<String> hashTable = new SeparateChainingHashTable<>();

        // Insert
        long startInsert = System.nanoTime();
        for (String s : list) hashTable.insert(s);
        long endInsert = System.nanoTime();

        // Search
        long startSearch = System.nanoTime();
        for (String s : list) hashTable.contains(s);
        long endSearch = System.nanoTime();

        // Delete
        long startDelete = System.nanoTime();
        for (String s : list) hashTable.remove(s);
        long endDelete = System.nanoTime();

        long insertTime = endInsert - startInsert;
        long searchTime = endSearch - startSearch;
        long deleteTime = endDelete - startDelete;

        // Print results
        System.out.println(label + " List:");
        System.out.println("Insert time: " + insertTime + " ns");
        System.out.println("Search time: " + searchTime + " ns");
        System.out.println("Delete time: " + deleteTime + " ns");
        System.out.println("----------------------------");

        // Append results to analysis.txt
        String csvLine = list.size() + "," + label + "," + insertTime + "," + searchTime + "," + deleteTime + "\n";
        appendToFile("analysis.txt", csvLine);
    }

    // Appends text to a file
    public static void appendToFile(String filename, String text) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename, true);
        fos.write(text.getBytes());
        fos.close();
    }
}

