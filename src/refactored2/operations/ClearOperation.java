package refactored2.operations;

public class ClearOperation extends Operation {
    public ClearOperation() {
        super(0, x -> new Double[0]);
    }

    @Override
    public void validateWithSize(int stacksize) {
        super.setAmount(stacksize);
    }
}
