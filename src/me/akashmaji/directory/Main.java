package me.akashmaji.directory;

import me.akashmaji.directory.explorer.Explorer;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // read in the arguments
        Integer numArgs = args.length;
        System.out.println("Number of arguments: " + numArgs);
        if(numArgs < 1) {
            System.out.println("Usage: java -jar directory.jar <directory>");
        }
        System.out.println("Your argument(s): " + Arrays.toString(args));
        System.out.println("Hello World!");

        // get and pass the <directory> name
        String rootDirectory = args[0];
        System.out.println("Root directory: " + rootDirectory);

        Explorer explorer = new Explorer(rootDirectory);
//        printAllFilesAndDirectories.printAllNames(printAllFilesAndDirectories.rootPath);
        explorer.printAllFiles(explorer.rootPath);


    }
}
