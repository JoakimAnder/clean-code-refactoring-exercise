package refactored;

import refactored.actions.Operation;
import refactored.parser.Parser;
import refactored.parser.QueryParser;
import refactored.stack.DoubleStack;
import refactored.stack.Stack;
import refactored.window.SimpleWindow;
import refactored.window.Window;

public class Calculator {
	public static void main(String[] args) {
		Calculator app = new Calculator();
		app.setup();
	}

	private Window window;
	private Parser<Double> parser;
	private Stack stack;

	public Calculator() {
		window = SimpleWindow.createWindow("Calculator");
		parser = new QueryParser();
		stack = new DoubleStack();
	}

	private void setup() {
		window.setOnInputSubmit(this::onSubmit);
		parser.setOnError(s -> window.showErrorMessage((String) s));
		parser.setOnQuit(() -> window.exit());

		refreshDisplay();
		window.show(true);
	}

	private void onSubmit(String input) {
		Operation<Double> operation = parser.toOperation(input);
		Double[] arr = stack.pop(operation.amount());
		arr = operation.apply(arr);
		for (Double digit: arr) {
			stack.push(digit);
		}

		window.input().clear();
		refreshDisplay();
	}

	private void refreshDisplay() {
		window.output().clear();
		window.output().set(stack.isEmpty() ? parser.getDefaultMessage() : stack.toString());
	}


}
