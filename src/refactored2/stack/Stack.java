package refactored2.stack;

public interface Stack<T> {
    void clear();

    void push(T digit);
    void push(T[] digits);

    T pop();
    T[] pop(int amount);

    int size();
    boolean isEmpty();

    String toString();


}
