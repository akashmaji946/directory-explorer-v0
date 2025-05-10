package me.akashmaji.directory.explorer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncodingDetector {

    public static Charset detectEncoding(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            byte[] bom = new byte[4];
            int n = is.read(bom, 0, 4);

            if (n >= 3 && bom[0] == (byte)0xEF && bom[1] == (byte)0xBB && bom[2] == (byte)0xBF)
                return StandardCharsets.UTF_8;

            if (n >= 2) {
                if (bom[0] == (byte)0xFF && bom[1] == (byte)0xFE)
                    return StandardCharsets.UTF_16LE;
                if (bom[0] == (byte)0xFE && bom[1] == (byte)0xFF)
                    return StandardCharsets.UTF_16BE;
            }

            if (n == 4) {
                if (bom[0] == (byte)0x00 && bom[1] == (byte)0x00 && bom[2] == (byte)0xFE && bom[3] == (byte)0xFF)
                    return Charset.forName("UTF-32BE");
                if (bom[0] == (byte)0xFF && bom[1] == (byte)0xFE && bom[2] == (byte)0x00 && bom[3] == (byte)0x00)
                    return Charset.forName("UTF-32LE");
            }
        }

        // Default fallback if no BOM is found
        return StandardCharsets.UTF_8;
    }
}
