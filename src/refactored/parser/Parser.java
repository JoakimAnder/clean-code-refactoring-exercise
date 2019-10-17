package refactored.parser;

import refactored.actions.Action;
import refactored.actions.Operation;

import java.util.function.Consumer;

public interface Parser<T> {
    Operation<T> toOperation(String query);
    String getDefaultMessage();
    void setOnError(Consumer<String> onError);
    void setOnQuit(Action onQuit);
}
