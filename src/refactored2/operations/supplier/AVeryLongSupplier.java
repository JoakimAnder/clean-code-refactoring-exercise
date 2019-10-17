package refactored2.operations.supplier;

import refactored2.actions.Action;
import refactored2.operations.ClearOperation;
import refactored2.operations.CollectedOperation;
import refactored2.operations.Operation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class AVeryLongSupplier implements OperationSupplier {
    private Map<Predicate<String>, Operation> operations;
    private Consumer<String> onError;
    private Action onQuit;
    private Operation OPERATION_NOT_SUPPORTED;

    private Predicate<String> isClear;
    private Predicate<String> isQuit;
    private Predicate<String> isSqrRoot;
    private Predicate<String> isPI;
    private Predicate<String> isPlus;
    private Predicate<String> isSubtract;
    private Predicate<String> isMultiply;
    private Predicate<String> isDivide;
    private Predicate<String> isNumber;

    private Function<Double[], Double[]> add;
    private Function<Double[], Double[]> sub;
    private Function<Double[], Double[]> mul;
    private Function<Double[], Double[]> div;
    private Function<Double[], Double[]> quit;
    private Function<Double[], Double[]> sqrt;
    private Function<Double[], Double[]> PI;

    public AVeryLongSupplier() {
        initOther();
        initFunctions();
        initPredicates();
        initOperations();
    }


    private void initOther() {
        onError = s -> {
            throw new RuntimeException(s);
        };
        onQuit = () -> onError.accept("Quitting");
        OPERATION_NOT_SUPPORTED = new CollectedOperation(0, x -> {
            onError.accept("Operation not supported");
            return x;
        });
    }


    private void initPredicates() {
        isClear = input -> input.equalsIgnoreCase("c") || input.equalsIgnoreCase("clear");
        isQuit = input -> input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit");
        isSqrRoot = input -> input.equalsIgnoreCase("sqrt");
        isPI = input -> input.equalsIgnoreCase("pi");
        isPlus = input -> input.equals("+");
        isSubtract = input -> input.equals("-");
        isMultiply = input -> input.equals("*");
        isDivide = input -> input.equals("/");
        isNumber = input -> {
            try {
                Double.parseDouble(input);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    private void initFunctions() {
        add = i -> Collections.singletonList(i[0] + i[1]).toArray(new Double[1]);
        sub = i -> Collections.singletonList(i[0] - i[1]).toArray(new Double[1]);
        mul = i -> Collections.singletonList(i[0] * i[1]).toArray(new Double[1]);
        div = i -> Collections.singletonList(i[0] / i[1]).toArray(new Double[1]);
        sqrt = i -> Collections.singletonList(Math.sqrt(i[0])).toArray(new Double[1]);
        PI = i -> Collections.singletonList(Math.PI).toArray(new Double[1]);
        quit = i -> {onQuit.execute(); return new Double[0];};
    }

    private void initOperations() {
        operations = new HashMap<>();
        operations.put(isClear, new ClearOperation());
        operations.put(isQuit, new CollectedOperation(0, quit));
        operations.put(isPlus, new CollectedOperation(2, add));
        operations.put(isSubtract, new CollectedOperation(2, sub));
        operations.put(isMultiply, new CollectedOperation(2, mul));
        operations.put(isDivide, new CollectedOperation(2, div));
        operations.put(isSqrRoot, new CollectedOperation(1, sqrt));
        operations.put(isPI, new CollectedOperation(0, PI));

    }


    @Override
    public Operation find(String input) {
        if(isNumber.test(input))
            return addNumberOperation(input);

        for(Map.Entry<Predicate<String>, Operation> e: operations.entrySet()) {
            if(e.getKey().test(input))
                return e.getValue();
        }

        return OPERATION_NOT_SUPPORTED;
    }

    private Operation addNumberOperation(String input) {
        return new CollectedOperation(0, x -> Collections.singletonList(Double.parseDouble(input)).toArray(new Double[1]));
    }

    @Override
    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    @Override
    public void setOnQuit(Action onQuit) {
        this.onQuit = onQuit;
    }
}
