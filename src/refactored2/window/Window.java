package refactored2.window;

import refactored2.window.text.TextBox;

import java.util.function.Consumer;

public interface Window {

    TextBox input();
    TextBox output();

    void setOnInputSubmit(Consumer<String> action);
    void setOnInputChange(Consumer<String> action);

    void exit();

    void show(boolean shouldShow);

    void showErrorMessage(String message);
}
