package refactored.actions;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MyOperation<T> {
    private Supplier<T> getVariable;
    private Consumer<Double> giveResult;

    public void execute(Function<T, Double> calculate) {
        T x = getVariable.get();
        double result = calculate.apply(x);
        giveResult.accept(result);
    }

    public MyOperation() {
        getVariable = () -> null;
        giveResult = e -> {};
    }

    public MyOperation(Supplier<T> getVariable, Consumer<Double> giveResult) {
        this.getVariable = getVariable;
        this.giveResult = giveResult;
    }

    public Supplier<T> getGetVariable() {
        return getVariable;
    }

    public void setGetVariable(Supplier<T> getVariable) {
        this.getVariable = getVariable;
    }

    public Consumer<Double> getGiveResult() {
        return giveResult;
    }

    public void setGiveResult(Consumer<Double> giveResult) {
        this.giveResult = giveResult;
    }
}
