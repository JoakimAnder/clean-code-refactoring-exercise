package refactored.window;

public interface Window {
    String getInput();

    void appendToOutput(String s);

    void clear();

    void exit();

    void show(boolean shouldShow);

    void showMessage(String message);
}
