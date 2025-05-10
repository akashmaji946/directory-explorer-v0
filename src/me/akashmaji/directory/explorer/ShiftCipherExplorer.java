package me.akashmaji.directory.explorer;


import me.akashmaji.directory.crypto.ShiftCipherASCII;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;

public class ShiftCipherExplorer extends Explorer {

    // public variables already inherited

    ShiftCipherASCII cipher;

    public ShiftCipherExplorer(String rootDirectory, String extensionsFile) {
        super(rootDirectory, extensionsFile);
        this.cipher = new ShiftCipherASCII(3);
    }

    public void printAllFilesEncrypted(Path rootPath) throws IOException {
        this.stream =  Files.newDirectoryStream(rootPath);
        for (Path path : this.stream) {
            if(Files.isDirectory(path)) {
                printFullName(path, true);
                printAllFilesEncrypted(path);
            }else{
                printFullName(path, false);
                printFileContentsEncrypted(path);
            }
        }

    }

    private void printFileContentsEncrypted(Path path) throws IOException {
        assert Files.isRegularFile(path) : Color.red(" >>> ERROR: The provided path is NOT a file: " + path);
        boolean isProcessable = checkProcessableFile(path);
        if(isProcessable) {
            try {
                Charset charset = EncodingDetector.detectEncoding(path);
                String dataContents = Files.readString(path, charset);
                dataContents =  cipher.encryptASCII(dataContents);
                System.out.println(dataContents);
            } catch (MalformedInputException e) {
                Color.out.println(Color.red(" >>> ERROR: Malformed input - could not decode file: " + path.toAbsolutePath()));
            } catch (IOException e) {
                Color.out.println(Color.red(" >>> ERROR: I/O error reading file: " + path.toAbsolutePath() + " - " + e.getMessage()));
            } catch (Exception e) {
                Color.out.println(Color.red(" >>> ERROR: Unexpected error: " + path.toAbsolutePath() + " - " + e.getMessage()));
            }
        }else{
            // System.out.println("WARN: The provided file is not a processable file: " + path.toAbsolutePath());
            Color.out.println(Color.yellow("WARN: The provided file is not a processable file: " + path.toAbsolutePath()));
        }
    }

    public void printAllFilesDecrypted(Path rootPath) throws IOException {
        this.stream =  Files.newDirectoryStream(rootPath);
        for (Path path : this.stream) {
            if(Files.isDirectory(path)) {
                printFullName(path, true);
                printAllFilesDecrypted(path);
            }else{
                printFullName(path, false);
                printFileContentsDecrypted(path);
            }
        }

    }

    private void printFileContentsDecrypted(Path path) throws IOException {
        assert Files.isRegularFile(path) : Color.red(" >>> ERROR: The provided path is NOT a file: " + path);
        boolean isProcessable = checkProcessableFile(path);
        if(isProcessable) {
            try {
                Charset charset = EncodingDetector.detectEncoding(path);
                String dataContents = Files.readString(path, charset);
                dataContents =  cipher.decryptASCII(dataContents);
                System.out.println(dataContents);
            } catch (MalformedInputException e) {
                Color.out.println(Color.red(" >>> ERROR: Malformed input - could not decode file: " + path.toAbsolutePath()));
            } catch (IOException e) {
                Color.out.println(Color.red(" >>> ERROR: I/O error reading file: " + path.toAbsolutePath() + " - " + e.getMessage()));
            } catch (Exception e) {
                Color.out.println(Color.red(" >>> ERROR: Unexpected error: " + path.toAbsolutePath() + " - " + e.getMessage()));
            }
        }else{
            // System.out.println("WARN: The provided file is not a processable file: " + path.toAbsolutePath());
            Color.out.println(Color.yellow("WARN: The provided file is not a processable file: " + path.toAbsolutePath()));
        }
    }

    public void saveAllFilesEncrypted(Path rootDir, Path newRootDir) throws IOException {
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
                    saveAllFilesEncrypted(sourcePath, targetPath);
                } else if (Files.isRegularFile(sourcePath)) {
                    // Ensure parent directories exist
                    Files.createDirectories(targetPath.getParent());
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    Color.out.println(Color.green("  COPIED: " + sourcePath + " ==> " + targetPath));
                    encryptFileInplace(targetPath);
                } else {
                    Color.out.println(Color.yellow(" SKIPPED COPYING: " + sourcePath));
                }
            }
        }
    }

    public void encryptFileInplace(Path path) throws IOException {
        assert Files.isRegularFile(path) : Color.red(" >>> ERROR: The provided path is NOT a file: " + path);
        boolean isProcessable = checkProcessableFile(path);
        if(isProcessable) {

            try {
                Charset charset = EncodingDetector.detectEncoding(path);
                String dataContents = Files.readString(path, charset);
                dataContents =  cipher.encryptASCII(dataContents);
                Files.writeString(path, dataContents);
            } catch (MalformedInputException e) {
                Color.out.println(Color.red(" >>> ERROR: Malformed input - could not decode file: " + path.toAbsolutePath()));
            } catch (IOException e) {
                Color.out.println(Color.red(" >>> ERROR: I/O error reading file: " + path.toAbsolutePath() + " - " + e.getMessage()));
            } catch (Exception e) {
                Color.out.println(Color.red(" >>> ERROR: Unexpected error: " + path.toAbsolutePath() + " - " + e.getMessage()));
            }finally {
                Color.out.println(Color.green("  ENCRYPTED: " + path));
            }

        }else{
            Color.out.println(Color.yellow(" SKIPPED ENCRYPTING: " + path));
        }

    }



    public void saveAllFilesDecrypted(Path rootDir, Path newRootDir) throws IOException {
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
                    saveAllFilesDecrypted(sourcePath, targetPath);
                } else if (Files.isRegularFile(sourcePath)) {
                    // Ensure parent directories exist
                    Files.createDirectories(targetPath.getParent());
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    Color.out.println(Color.green("  COPIED: " + sourcePath + " ==> " + targetPath));
                    decryptFileInplace(targetPath);
                } else {
                    Color.out.println(Color.yellow(" SKIPPED COPYING: " + sourcePath));
                }
            }
        }
    }

    public void decryptFileInplace(Path path) throws IOException {
        assert Files.isRegularFile(path) : Color.red(" >>> ERROR: The provided path is NOT a file: " + path);
        boolean isProcessable = checkProcessableFile(path);
        if(isProcessable) {
            try {
                Charset charset = EncodingDetector.detectEncoding(path);
                String dataContents = Files.readString(path, charset);
                dataContents =  cipher.decryptASCII(dataContents);
                Files.writeString(path, dataContents);
            } catch (MalformedInputException e) {
                Color.out.println(Color.red(" >>> ERROR: Malformed input - could not decode file: " + path.toAbsolutePath()));
            } catch (IOException e) {
                Color.out.println(Color.red(" >>> ERROR: I/O error reading file: " + path.toAbsolutePath() + " - " + e.getMessage()));
            } catch (Exception e) {
                Color.out.println(Color.red(" >>> ERROR: Unexpected error: " + path.toAbsolutePath() + " - " + e.getMessage()));
            }finally {
                Color.out.println(Color.green("  DECRYPTED: " + path));
            }
        }else{
            Color.out.println(Color.yellow(" SKIPPED DECRYPTING: " + path));
        }

    }

}

