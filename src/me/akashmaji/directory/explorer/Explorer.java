package me.akashmaji.directory.explorer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Explorer {

    public String rootDirectory;
    public Path rootPath;
    public DirectoryStream<Path> stream;
    public String extensionsFile;

    // DONE: Define a global list in a csv or .txt file
    List<String> extensionsAllowed;

    public Explorer(String rootDirectory, String extensionsFile) {
        this.rootDirectory = rootDirectory;
        this.rootPath = Paths.get(rootDirectory);
        this.extensionsFile = extensionsFile;
        this.extensionsAllowed = readExtensionsFromFile(this.extensionsFile);
        assert Files.isDirectory(rootPath) : Color.red(" >>> ERROR: The provided path is NOT a directory: " + rootPath);

    }

    public static List<String> readExtensionsFromFile(String filePath) {
        List<String> extensions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                // Add the line to the extensions list.  No need to trim in this case.
                extensions.add(line.trim());
            }
        } catch (IOException e) {
           Color.out.println(Color.red(" >>> ERROR: Reading File: " + e.getMessage()));
        }
        return extensions;
    }

    public void printAllFiles(Path rootPath) throws IOException {
        this.stream =  Files.newDirectoryStream(rootPath);
        for (Path path : this.stream) {
            if(Files.isDirectory(path)) {
                printFullName(path, true);
                printAllFiles(path);
            }else{
                printFullName(path, false);
                printFileContents(path);
            }
        }

    }

    private void printFileContents(Path path) throws IOException {
        assert Files.isRegularFile(path) : Color.red(" >>> ERROR: The provided path is NOT a file: " + path);
        boolean isProcessable = checkProcessableFile(path);
        if(isProcessable) {
                // using modern Java (read and print at once)
                String dataContents = Files.readString(path);
                System.out.println(dataContents);
        }else{
//            System.out.println("WARN: The provided file is not a processable file: " + path.toAbsolutePath());
            Color.out.println(Color.yellow("WARN: The provided file is not a processable file: " + path.toAbsolutePath()));
        }
    }

    private boolean checkProcessableFile(Path path) {
        assert Files.isRegularFile(path) : Color.red(" >>> ERROR: The provided path is NOT a file: " + path);
        String fileName = path.getFileName().toString();
        String fileExtension = fileName.substring(fileName.contains(".") ? fileName.lastIndexOf(".") : fileName.length());

        // System.out.println(fileExtension);
        // allowed extensions (e.g., ".txt", ".java", ".py", ".c", ".cpp", ".h", ".js", ".html",
        // ".css", ".xml", ".json", ".md", ".sh", ".bat").

        return extensionsAllowed.contains(fileExtension);

    }

    public void printAllNames(Path rootPath) throws IOException {
        this.stream =  Files.newDirectoryStream(rootPath);
        for (Path path : this.stream) {
            if(Files.isDirectory(path)) {
                printFullName(path, true);
                printAllNames(path);
            }else{
                printFullName(path, false);
            }
        }

    }

    private void printFullName(Path path, boolean isDirectory) throws IOException {
        if(isDirectory) {
//            System.out.println("DIR: " + path.toAbsolutePath());
            Color.out.println(Color.blue("DIR: " + path.toAbsolutePath()));
        }else{
//            System.out.println("FILE: " + path.toAbsolutePath());
            Color.out.println(Color.green("  FILE: " + path.toAbsolutePath()));
        }
    }


}
