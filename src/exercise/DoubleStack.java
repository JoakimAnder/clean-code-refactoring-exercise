package exercise;

import java.util.Arrays;

import javax.swing.JOptionPane;


public class DoubleStack {
	private double[] stackArray;
	private int depthInt;
	
	public DoubleStack() {
		stackArray = new double[200];
		depthInt = 0;
	}
	
	public int depth() {
		return depthInt;
	}
	
	public void push(double value) { // assuming that the stack never gets overfilled!
		stackArray[depthInt] = value;
		depthInt++;
	}
	
	public double pop(){
		if (depthInt == 0){
			JOptionPane.showMessageDialog(null, "Pop - stack empty, returning 0!");
			return 0.0;
		} else {
			depthInt--;
			return stackArray[depthInt];
		}
	}
	
	public double peek(){
		if (depthInt == 0){
			JOptionPane.showMessageDialog(null, "Peek - stack empty, returning 0!");
			return 0.0;
		} else {
			return stackArray[depthInt-1];
		}
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
