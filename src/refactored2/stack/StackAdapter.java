package refactored2.stack;


import java.util.Arrays;

public class StackAdapter implements Stack<Double> {
	private java.util.Stack<Double> stack;


	public StackAdapter() {
		stack = new java.util.Stack<>();
	}

	@Override
	public void clear() {
		stack.clear();
	}

	@Override
	public void push(Double digit) {
		stack.push(digit);
	}

	@Override
	public void push(Double[] digits) {
		stack.addAll(Arrays.asList(digits));
	}

	@Override
	public Double pop() {
		return stack.pop();
	}

	@Override
	public Double[] pop(int amount) {
		Double[] arr = new Double[amount];

		for (int i = amount-1; i >= 0; i--) {
			arr[i] = stack.pop();
		}

		return arr;
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public String toString() {
		return stack.toString();
	}
}
