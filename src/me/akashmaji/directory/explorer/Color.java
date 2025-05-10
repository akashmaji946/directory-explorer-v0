package me.akashmaji.directory.explorer;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Color {

    //  Use this as System.out, for example: Color.out.println("Hello");
    public static final PrintStream out;

    private static final String COLOR_RESET = "\u001B[0m";

    // Static initialization block to set up the 'out' PrintStream
    static {
        PrintStream ps;
        //StandardCharsets.UTF_8 works in most cases
        ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out = ps;
    }

    private static String color(String text, String colorCode) {
        return colorCode + text + COLOR_RESET;
    }

    public static void print(String text) {
        out.print(text);
    }

    public static void println(String text) {
        out.println(text);
    }

    public static void printf(String format, Object... args) {
        out.printf(format, args);
    }

    public static String red(String text) {
        return color(text, "\u001B[31m");
    }

    public static String green(String text) {
        return color(text, "\u001B[32m");
    }

    public static String yellow(String text) {
        return color(text, "\u001B[33m");
    }

    public static String blue(String text) {
        return color(text, "\u001B[34m");
    }

    public static String cyan(String text) {
        return color(text, "\u001B[36m");
    }

    public static String magenta(String text) {
        return color(text, "\u001B[35m");
    }

    public static String white(String text) {
        return color(text, "\u001B[37m");
    }

    public static String black(String text) {
        return color(text, "\u001B[30m");
    }

    // Example usage
    public static void test(String[] args) {
        //Using the class methods
        Color.out.println(Color.red("This is red text"));
        Color.out.println(Color.green("This is green text"));
        Color.out.println(Color.blue("This is blue text"));
        Color.out.println(Color.yellow("This is yellow text"));
        Color.out.println(Color.cyan("This is cyan text"));
        Color.out.println(Color.magenta("This is magenta text"));
        Color.out.println(Color.white("This is white text"));
        Color.out.println(Color.black("This is black text"));

        Color.out.println("This is " + Color.red("red") + " and this is " + Color.blue("blue"));

        String formatted = Color.yellow("Formatted ") + Color.green("Output");
        Color.out.printf("Result: %s\n", formatted);

        Color.out.print(Color.cyan("Non New Line"));
        Color.out.println(Color.magenta(" Then New Line"));
    }
}
