package refactored.parser;

import refactored.actions.MyOperation;
import refactored.actions.Action;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Parser {
    Action onClear;
    Action onEmpty;
    Action onExit;
    Consumer<Double> onDigit;
    Consumer<String> onInvalid;
    MyOperation<Double> unaryOperator;
    MyOperation<Double[]> binaryOperator;


    public Parser() {
        onClear = () -> {};
        onEmpty = () -> {};
        onExit = () -> {};
        onDigit = d -> {};
        onInvalid = s -> {};
        unaryOperator = new MyOperation<>();
        binaryOperator = new MyOperation<>();
    }

    public abstract Action parse(String query);
    public abstract String getDefaultMessage();

    protected MyOperation<Double> getOnUnaryOperator() {
        return unaryOperator;
    }

    public void setOnUnaryOperator(Supplier<Double> before, Consumer<Double> after) {
        unaryOperator = new MyOperation<>(before, after);
    }

    protected MyOperation<Double[]> getOnBinaryOperator() {
        return binaryOperator;
    }

    public void setOnBinaryOperator(Supplier<Double[]> before, Consumer<Double> after) {
        binaryOperator = new MyOperation<>(before, after);
    }

    protected Action getOnClear() {
        return onClear;
    }

    public void setOnClear(Action onClear) {
        this.onClear = onClear;
    }

    protected Action getOnExit() {
        return onExit;
    }

    public void setOnExit(Action onExit) {
        this.onExit = onExit;
    }

    protected Consumer<Double> getOnDigit() {
        return onDigit;
    }

    public void setOnDigit(Consumer<Double> onDigit) {
        this.onDigit = onDigit;
    }

    protected Consumer<String> getOnInvalid() {
        return onInvalid;
    }

    public void setOnInvalid(Consumer<String> onInvalid) {
        this.onInvalid = onInvalid;
    }

    public Action getOnEmpty() {
        return onEmpty;
    }

    public void setOnEmpty(Action onEmpty) {
        this.onEmpty = onEmpty;
    }
}
