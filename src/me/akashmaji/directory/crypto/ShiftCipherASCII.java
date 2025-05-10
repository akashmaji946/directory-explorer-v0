package me.akashmaji.directory.crypto;

import me.akashmaji.directory.explorer.Color;

/* only does the ciphering of plain texts and code files (ASCII) characters only */
// TODO: do it for Unicode characters also later
public class ShiftCipherASCII {

    public int shift;
    public ShiftCipherASCII(int shift){
        this.shift = shift;
    }

    public String encryptASCII(String inputLine){
        // check that all the characters are ASCII
        assert checkASCII(inputLine): Color.red(">>> ERROR: Input is not ASCII for encryptASCII(): " + inputLine);
        StringBuilder outputLine = new StringBuilder();
        for(int i = 0; i < inputLine.length(); i++){
            char c = inputLine.charAt(i);
            char t = (char) (((int)c + shift) % 128);
            outputLine.append(t);
        }
        return outputLine.toString();
    }
    public String decryptASCII(String inputLine){
        assert checkASCII(inputLine): Color.red(">>> ERROR: Input is not ASCII for decryptASCII(): " + inputLine);
        StringBuilder outputLine = new StringBuilder();
        for(int i = 0; i < inputLine.length(); i++){
            char c = inputLine.charAt(i);
            char t = (char) (((int)c - shift + 128) % 128);
            outputLine.append(t);
        }
        return outputLine.toString();
    }

    private boolean checkASCII(String inputLine) {
        for (int i = 0; i < inputLine.length(); i++) {
            char c = inputLine.charAt(i);
            if (c <= 127) {
                continue;
            }else{
                return false;
            }
        }
        return true;
    }
}
