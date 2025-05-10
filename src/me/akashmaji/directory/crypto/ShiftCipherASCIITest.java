package me.akashmaji.directory.crypto;

public class ShiftCipherASCIITest {

    public static void main(String[] args) {
        ShiftCipherASCII cipher = new ShiftCipherASCII(5); // you can vary the shift value

        String[] testStrings = {
                "Hello World!",                // Regular text
                "1234567890",                  // Digits
                "!@#$%^&*()_+",                 // Special characters
                "The quick brown fox jumps over the lazy dog.", // Pangram
                "   ",                         // Spaces only
                "\n\t\r",                      // Control characters
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ",    // Uppercase letters
                "abcdefghijklmnopqrstuvwxyz",    // Lowercase letters
                "~`{}[]|\\:\";'<>?,./",        // Punctuation and symbols
                "\u007F",                      // ASCII 127 (DEL)
                "",                            // Empty string
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890",    // Letters + digits
                "12345 67890",                 // Mixed digits with spaces
                "   Hello  ",                  // Text with leading/trailing spaces
                "\u0000",                      // ASCII NUL (null character)
                "\u0001",                      // ASCII SOH (start of heading)
                "\u0002",                      // ASCII STX (start of text)
                "Shift Cipher!",               // Text with punctuation
                "   A test with spaces   ",    // Text with spaces around it
                "1234567890!@#$%^&*()",        // Digits + symbols
                "ABCDabcd",                    // Uppercase + lowercase mixed
                "Test case with some spaces",  // Regular sentence with spaces
                "Line 1\nLine 2",              // Text with newline
                "Line1\tTabbed Line2",         // Text with tabs
                "\rReturn Carriage",           // Text with carriage return
                "    Leading spaces",          // Text with leading spaces
                "Trailing spaces    ",         // Text with trailing spaces
                "Whitespace\tand\nControl\r",  // Mixed whitespace and control chars
                "Shift\nCipher Test",          // Line breaks included
                "\u0003",                      // ASCII ETX (end of text)
                "SomeText1234",                // Text + digits
                "StartWithSpace",              // Leading space
                "EndWithSpace   ",             // Trailing space
                "Space\tSeparated",            // Text with a tab between words
                "UppercaseTEST",               // All uppercase
                "lowercase",                   // All lowercase
                "Number12345",                 // Digits with no spaces
                "Special!@#Characters$%&",      // Special characters
                "Tab\tTab",                    // Tabs within string
                "Multiple\nLines\nText",       // Multiple lines
                "\tWhitespace\tBetween",       // Tabs at different positions
                "Mix123withSymbols$%^",        // Mixed symbols and numbers
                "123\t456\n789",               // Mixed tabs and newlines
                "\tA mixture of spaces and tabs", // Leading tab and spaces
                "TESTING@#STRING",              // Text with special chars
                "   A series of spaces      ",  // Multiple spaces with text
                "abc123abc123",                // Alphanumeric repeated
                "Number1234withText",          // Digits with text
                "Line1\nLine2\nLine3",         // Text with line breaks
                "Test with double quotes \"\"", // Text with double quotes
                "MixOfLettersAnd@#$",          // Mix letters and special characters
                "   More spaces and text   ",  // More spaces with text
                "Quotes\"andEscaped\\Chars",   // Escaped characters
                "Empty",                       // Single word
                "Empty123",                    // Empty and digits
                "NotEmptyNow",                 // Non-empty text
                "Tab\tand\tSpaces",            // Tabs and spaces mixed
                "SpacesAtStart\tandEnd ",      // Leading and trailing spaces and tabs
                "EndOfLine\nHere",             // Line break at the end
                "Multiple \tTabs \nAnd Spaces", // Mixed control chars with spaces
                "Line1 with special chars! @#\n",  // Special chars and newline
                "Another#Test with@Symbols!",  // Symbols within text
                "Some text with punctuations.", // Text with period
                "Newline\nin-between text",    // Newline in the middle
                "StringWithOneWord",           // One word string
                "StringWithSpace After",       // Space after word
                "Space Before String ",        // Space before word
                "MultipleWordsInLineWithoutSpaces", // No spaces between words
                "MixedUPCASElowercase",        // Mixed case
                "LongTextHereThatIsExtendedWithSpecialChars!@#^&", // Long text
                "MixedDigits1234andText5678",  // Digits + text
                "Random-Text-With-Hyphens",    // Hyphens within text
                "EndOfStringWithSpecialChars{}[]", // Special chars inside text
                "JustAString",                 // Plain string
                "IncludingDigits123WithAlphabets",  // Mixed digits and letters
                "StringWithNumber1234567",     // String + number
                "1234567890Testing",           // Digits first, followed by text
                "Password123#Test",            // Password like text with symbols
                "TextWithSymbol$%@^",          // Symbols and text
                "TestingMoreComplexInput!",    // Complex input
                "File#Input_String",           // Input with file-like name
                "C0de123WithMix!",             // Code with mix of digits
                "FullSTOP.",                   // Text with period
                "More#Test-String$Mix",        // Test case with mixed symbols
                "HyperTextMarkupLanguage<>()", // HTML-like text
                "Space Between Words",         // Space between words
                "WhitespacesOnly   ",           // Only whitespaces
                "MixedCharacters@#1234",       // Characters mixed with numbers
                "AnotherTest1234WithNumbers",  // Mixed text and numbers
                "EndWithSymbols$#@!"            // Text ending with symbols
        };


        int passed = 0;
        for (String input : testStrings) {
            boolean result = testShiftCipher(cipher, input);
            if (result) passed++;
        }

        System.out.println("\nPassed " + passed + " out of " + testStrings.length + " tests.");
    }

    private static boolean testShiftCipher(ShiftCipherASCII cipher, String input) {
        try {
            String encrypted = cipher.encryptASCII(input);
            String decrypted = cipher.decryptASCII(encrypted);

            boolean isCorrect = input.equals(decrypted);
            System.out.printf("Test input: %-50s | Passed: %s%n", formatVisible(input), isCorrect ? "✅" : "❌");

            return isCorrect;
        } catch (Exception e) {
            System.out.printf("Test input: %-50s | Error: %s ❌%n", formatVisible(input), e.getMessage());
            return false;
        }
    }

    // Helper to show control characters visibly
    private static String formatVisible(String input) {
        return input.replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\r", "\\r")
                .replace("\u007F", "\\u007F")
                .replace(" ", "␣"); // visual space
    }
}
