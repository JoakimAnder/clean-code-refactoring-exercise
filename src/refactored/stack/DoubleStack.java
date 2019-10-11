package refactored.stack;

import java.util.Arrays;

import javax.swing.JOptionPane;


public class DoubleStack implements Stack {
	private double[] stackArray;
	private int depthInt;
	
	public DoubleStack() {
		stackArray = new double[200];
		depthInt = 0;
	}
	
	public int depth() {
		return depthInt;
	}
	
	public void push(Double value) { // assuming that the stack never gets overfilled!
		stackArray[depthInt] = value;
		depthInt++;
	}

	public boolean isEmpty() {
		return depthInt == 0;
	}
	
	public Double pop(){
		if (depthInt == 0){
			JOptionPane.showMessageDialog(null, "Pop - stack empty, returning 0!");
			return 0.0;
		} else {
			depthInt--;
			return stackArray[depthInt];
		}
	}

	@Override
	public Double[] pop(int amount) {
		Double[] popped = new Double[amount];
		for (int i = amount-1; i >= 0; i--) {
			popped[i] = pop();
		}
		return popped;
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0; ; i++) {
			b.append(stackArray[i]);
			if (i == depthInt-1)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	
	public void clear(){
		depthInt = 0;
	}
	

}
