package me.akashmaji.directory.explorer;

import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Explorer {

    public String rootDirectory;
    public Path rootPath;
    public DirectoryStream<Path> stream;

    // Now we define a local ArrayList
    // TODO: Define a global list in a csv or .txt file
    ArrayList<String> extensionsAllowed = new ArrayList<>();

    public Explorer(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.rootPath = Paths.get(rootDirectory);
        assert Files.isDirectory(rootPath) : " >>> ERROR: The provided path is NOT a directory: " + rootPath;
        extensionsAllowed.addAll(Arrays.asList(".txt", ".java", ".py", ".c", ".cpp", ".h", ".js", ".html", ".css", ".xml", ".json", ".md", ".sh", ".bat"));

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
        assert Files.isRegularFile(path) : " >>> ERROR: The provided path is NOT a file: " + path;
        boolean isProcessable = checkProcessableFile(path);
        if(isProcessable) {
                // using modern Java (read and print at once)
                String dataContents = Files.readString(path);
                System.out.println(dataContents);
        }else{
            System.out.println("WARN: The provided file is not a processable file: " + path.toAbsolutePath());
        }
    }

    private boolean checkProcessableFile(Path path) {
        assert Files.isRegularFile(path) : " >>> ERROR: The provided path is NOT a regular file: " + path;
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
            System.out.println("DIR: " + path.toAbsolutePath());
        }else{
            System.out.println("FILE: " + path.toAbsolutePath());
        }
    }


}
