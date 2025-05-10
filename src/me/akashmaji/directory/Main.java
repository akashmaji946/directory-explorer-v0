package me.akashmaji.directory;

import me.akashmaji.directory.crypto.ShiftCipherASCII;
import me.akashmaji.directory.explorer.Color;
import me.akashmaji.directory.explorer.Explorer;
import me.akashmaji.directory.explorer.ShiftCipherExplorer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
//        readAndSaveAllowedFiles(args);
//        testShiftCipher();
        readAndSaveAllowedFilesShiftCipher(args);
    }

    private static void readAndSaveAllowedFilesShiftCipher(String[] args) throws IOException {
        // read in the arguments
        Integer numArgs = args.length;
        System.out.println(">> Hello World! <<<" + ": readAndSaveAllowedFilesShiftCipher()");
        System.out.println(">>> Number of arguments: " + numArgs);
        if(numArgs < 1) {
            // System.out.println("Usage: java -jar directory.jar <directory>");
            Color.out.println(Color.red("Usage: java -jar directory.jar <srcDir(mandatory)> <encDir(optional)> <decDir(optional)>"));
            System.exit(1);
        }
        System.out.println(">>>> Your argument(s): " + Arrays.toString(args));

        // get and pass the <directory> name
        String rootDirectory = args[0];
        String encRootDirectory = numArgs > 1 ? args[1] : "encRootDirectory";
        String decRootDirectory = numArgs > 1 ? args[2] : "decRootDirectory";
        System.out.println(">>>>> Root directory: " + rootDirectory);
        System.out.println(">>>>> New ENCRYPTED directory: " + encRootDirectory);
        System.out.println(">>>>> New DECRYPTED directory: " + decRootDirectory);

        System.out.println("________________________________________________________________________________________________");
        ShiftCipherExplorer explorer = new ShiftCipherExplorer(rootDirectory, "extensions.txt");
        explorer.printAllFiles(explorer.rootPath);
        System.out.println("________________________________________________________________________________________________");
        explorer.printAllFilesEncrypted(explorer.rootPath);
        System.out.println("________________________________________________________________________________________________");
        Path srcDir = Paths.get(rootDirectory);
        Path encDir = Paths.get(encRootDirectory);
        Path decDir = Paths.get(decRootDirectory);
        explorer.saveAllFilesEncrypted(srcDir, encDir);
        explorer.saveAllFilesDecrypted(encDir, decDir);

        // check if all files have been processed(no missing file)
        boolean a = explorer.checkForSameNumberOfFiles(srcDir, encDir);
        boolean b = explorer.checkForSameNumberOfFiles(srcDir, decDir);
        boolean c = explorer.checkForSameNumberOfFiles(encDir, decDir);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }


    private static void readAndSaveAllowedFiles(String[] args) throws IOException {
        // read in the arguments
        Integer numArgs = args.length;
        System.out.println(">> Hello World! <<<");
        System.out.println(">>> Number of arguments: " + numArgs);
        if(numArgs < 1) {
            // System.out.println("Usage: java -jar directory.jar <directory>");
            Color.out.println(Color.red("Usage: java -jar directory.jar <srcDir(mandatory)> <destDir(optional)>"));
            System.exit(1);
        }
        System.out.println(">>>> Your argument(s): " + Arrays.toString(args));

        // get and pass the <directory> name
        String rootDirectory = args[0];
        String newRootDirectory = numArgs > 1 ? args[1] : "newRootDirectory";
        System.out.println(">>>>> Root directory: " + rootDirectory);
        System.out.println(">>>>> New Root directory: " + newRootDirectory);

        System.out.println("________________________________________________________________________________________________");
        Explorer explorer = new Explorer(rootDirectory, "extensions.txt");
        explorer.printAllFiles(explorer.rootPath);
        System.out.println("________________________________________________________________________________________________");
        Path srcDir = Paths.get(rootDirectory);
        Path destDir = Paths.get(newRootDirectory);
        explorer.saveAllFiles(srcDir, destDir);
    }

    private static void testShiftCipher() throws IOException {
        System.out.println("Hello World!");
        String plainText = "Hello World!";
        ShiftCipherASCII sc = new ShiftCipherASCII(3);
        String cipherText = sc.encryptASCII(plainText);
        System.out.println(cipherText);
        String decryptedText = sc.decryptASCII(cipherText);
        System.out.println(decryptedText);
        System.out.println(plainText.equals(decryptedText));

    }
}
