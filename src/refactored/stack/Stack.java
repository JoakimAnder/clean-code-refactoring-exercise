package refactored.stack;

public interface Stack {
    void clear();

    void push(Double digit);

    Double pop();

    Double[] pop(int amount);

    boolean isEmpty();
}
