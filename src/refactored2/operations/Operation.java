package refactored2.operations;


import refactored2.actions.Action;
import refactored2.operations.supplier.AVeryLongSupplier;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Operation {
    private static AVeryLongSupplier supplier = new AVeryLongSupplier();

    private int amount;
    private Function<Double[], Double[]> function;

    public static void setOnError(Consumer<String> onError) {
        supplier.setOnError(onError);
    }

    public static void setOnQuit(Action onQuit) {
        supplier.setOnQuit(onQuit);
    }

    public static Operation parse(String input) {
        return supplier.find(input);
    }

    Operation(int amount, Function<Double[], Double[]> function) {
        this.amount = amount;
        this.function = function;
    }

    public int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    void setFunction(Function<Double[], Double[]> function) {
        this.function = function;
    }

    public Double[] apply(Double[] input) {
        return function.apply(input);
    }


    public abstract void validateWithSize(int stackSize);
}

