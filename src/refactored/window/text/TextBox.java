package refactored.window.text;


import java.util.function.Consumer;

public interface TextBox {
    String getText();
    void set(String text);
    void append(String text);
    void clear();
}
