package refactored2.parser;


import refactored2.actions.Action;
import refactored2.operations.Operation;
import refactored2.stack.StackAdapter;
import refactored2.stack.Stack;

import java.util.function.Consumer;

public class QueryParser implements Parser {
    private static final String ON_EMPTY_MESSAGE = "[empty]\nCommands: q=quit c=clear + - * / number";

    private Stack<Double> stack;
    private Consumer<String> onError;

    public QueryParser() {
        stack = new StackAdapter();
        onError = s -> {
            throw new RuntimeException(s);
        };
    }

    @Override
    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
        Operation.setOnError(onError);
    }

    @Override
    public void setOnQuit(Action onQuit) {
        Operation.setOnQuit(onQuit);
    }

    @Override
    public String getMessage() {
        return stack.isEmpty()
                ? ON_EMPTY_MESSAGE
                : stack.toString();
    }

    @Override
    public void parse(String input) {
        Operation operation = Operation.parse(input);
        operation.validateWithSize(stack.size());
        applyOperation(operation);
    }

    private void applyOperation(Operation operation) {
        Double[] arr = stack.pop(operation.getAmount());
        arr = operation.apply(arr);
        stack.push(arr);
    }
}
