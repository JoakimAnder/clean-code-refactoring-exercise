package refactored2.operations.supplier;

import refactored2.actions.Action;
import refactored2.operations.Operation;

import java.util.function.Consumer;

public interface OperationSupplier {
    void setOnQuit(Action onQuit);
    void setOnError(Consumer<String> onError);

    Operation find(String input);
}
