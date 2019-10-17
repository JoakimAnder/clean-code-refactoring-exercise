package refactored.actions;


import java.util.function.Function;

public class Operation<T> {
    private int amount;
    private Function<T[], T[]> function;

    public Operation(int amount, Function<T[], T[]> function) {
        this.amount = amount;
        this.function = function;
    }

    public int amount() {
        return amount;
    }

    public T[] apply(T[] input) {
        return function.apply(input);
    }
}
