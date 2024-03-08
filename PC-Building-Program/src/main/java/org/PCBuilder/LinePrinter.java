package org.PCBuilder;

public class LinePrinter {
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_CYAN = "\u001B[36m";
    private int lineNumber;
    private String lineOfInput;

    //Method to alternate color of lines in terminal
    public LinePrinter(int lineNumber, String lineOfInput){
        if (lineNumber % 2 == 0) {
            System.out.println(ANSI_CYAN + lineNumber + ":      " + lineOfInput + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + lineNumber + ":      " + lineOfInput + ANSI_RESET);
        }
    }

}
