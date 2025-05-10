package me.akashmaji.directory.explorer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
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

    public Explorer() {
    }

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

    public void printFileContents(Path path) throws IOException {
        assert Files.isRegularFile(path) : Color.red(" >>> ERROR: The provided path is NOT a file: " + path);
        boolean isProcessable = checkProcessableFile(path);
        if(isProcessable) {
            try {
                Charset charset = EncodingDetector.detectEncoding(path);
                String dataContents = Files.readString(path, charset);
                System.out.println(dataContents);
            } catch (MalformedInputException e) {
                Color.out.println(Color.red(" >>> ERROR: Malformed input - could not decode file: " + path.toAbsolutePath()));
            } catch (IOException e) {
                Color.out.println(Color.red(" >>> ERROR: I/O error reading file: " + path.toAbsolutePath() + " - " + e.getMessage()));
            } catch (Exception e) {
                Color.out.println(Color.red(" >>> ERROR: Unexpected error: " + path.toAbsolutePath() + " - " + e.getMessage()));
            }
        }else{
//            System.out.println("WARN: The provided file is not a processable file: " + path.toAbsolutePath());
            Color.out.println(Color.yellow("WARN: The provided file is not a processable file: " + path.toAbsolutePath()));
        }
    }

    public boolean checkProcessableFile(Path path) {
        assert Files.isRegularFile(path) : Color.red(" >>> ERROR: The provided path is NOT a file: " + path);
        String fileName = path.getFileName().toString();
        String fileExtension = fileName.substring(fileName.contains(".") ? fileName.lastIndexOf(".") : fileName.length());

        // System.out.println(fileExtension);

        // allowed extensions (e.g., ".txt", ".java", ".py", ".c", ".cpp", ".h", ".js", ".html")
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

    public void printFullName(Path path, boolean isDirectory) throws IOException {
        if(isDirectory) {
//            System.out.println("DIR: " + path.toAbsolutePath());
            Color.out.println(Color.blue("DIR: " + path.toAbsolutePath()));
        }else{
//            System.out.println("FILE: " + path.toAbsolutePath());
            Color.out.println(Color.green("  FILE: " + path.toAbsolutePath()));
        }
    }


    public void saveAllFiles(Path rootDir, Path newRootDir) throws IOException {
        // Validate that rootDir is a directory
        assert Files.isDirectory(rootDir) : Color.red(" >>> ERROR: Source path is NOT a directory: " + rootDir);

        // Create the root of the new directory structure if it doesn't exist
        if (!Files.exists(newRootDir)) {
            Files.createDirectories(newRootDir);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootDir)) {
            for (Path sourcePath : stream) {
                Path relativePath = rootDir.relativize(sourcePath);
                Path targetPath = newRootDir.resolve(relativePath);

                if (Files.isDirectory(sourcePath)) {
                    // Recurse into subdirectory
                    saveAllFiles(sourcePath, targetPath);
                } else if (Files.isRegularFile(targetPath)) {
                    // Ensure parent directories exist
                    Files.createDirectories(targetPath.getParent());
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    Color.out.println(Color.green("  COPIED: " + sourcePath + " ==> " + targetPath));
                } else {
                    Color.out.println(Color.yellow(" SKIPPED: " + sourcePath));
                }
            }
        }
    }

    public boolean checkForSameNumberOfFiles(Path firstDir, Path secondDir) throws IOException {
        boolean hasSameNumberOfFiles = true;
        try (DirectoryStream<Path> streamFirstDir = Files.newDirectoryStream(firstDir)) {
            for (Path sourcePath : streamFirstDir) {
                Path relativePath = firstDir.relativize(sourcePath);
                Path targetPath = secondDir.resolve(relativePath);

                if(Files.isDirectory(sourcePath) && Files.isDirectory(targetPath)) {
                    boolean check = checkForSameNumberOfFiles(sourcePath, targetPath);
                    hasSameNumberOfFiles = hasSameNumberOfFiles && check;
                }else if(Files.isRegularFile(targetPath) && Files.isRegularFile(sourcePath)) {
                    // ok
                }else{
                    return false;
                }
            }
        }

        return hasSameNumberOfFiles;

    }


}
