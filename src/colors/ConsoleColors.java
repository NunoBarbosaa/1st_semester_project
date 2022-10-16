package colors;

public enum ConsoleColors {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_CYAN("\u001B[36m"),
    ANSI_RESET("\u001B[0m");

    private final String code;

    ConsoleColors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
