package refactored2.operations;

import java.util.function.Function;

public class CollectedOperation extends Operation {
    private static final Function<Double[], Double[]> doNothing = d -> d;

    public CollectedOperation(int amount, Function<Double[], Double[]> function) {
        super(amount, function);
    }

    @Override
    public void validateWithSize(int stackSize) {
        if(getAmount() > stackSize) {
            setFunction(doNothing);
        }
    }
}
