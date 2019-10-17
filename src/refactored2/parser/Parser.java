package refactored2.parser;

import refactored2.actions.Action;

import java.util.function.Consumer;

public interface Parser {
    void setOnError(Consumer<String> onError);
    void setOnQuit(Action onQuit);

    String getMessage();

    void parse(String input);
}
