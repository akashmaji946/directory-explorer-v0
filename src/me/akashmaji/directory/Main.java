package me.akashmaji.directory;

import me.akashmaji.directory.explorer.Color;
import me.akashmaji.directory.explorer.Explorer;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // read in the arguments
        Integer numArgs = args.length;
        System.out.println(">> Hello World! <<<");
        System.out.println(">>> Number of arguments: " + numArgs);
        if(numArgs < 1) {
//            System.out.println("Usage: java -jar directory.jar <directory>");
            Color.out.println(Color.red("Usage: java -jar directory-explorer.jar numArgs"));
            System.exit(1);
        }
        System.out.println(">>>> Your argument(s): " + Arrays.toString(args));

        // get and pass the <directory> name
        String rootDirectory = args[0];
        System.out.println(">>>>> Root directory: " + rootDirectory);
        System.out.println("________________________________________________________________________________________________");

        Explorer explorer = new Explorer(rootDirectory, "extensions.txt");
//        printAllFilesAndDirectories.printAllNames(printAllFilesAndDirectories.rootPath);
        explorer.printAllFiles(explorer.rootPath);
        System.out.println("________________________________________________________________________________________________");

    }
}
