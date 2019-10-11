package refactored;

import refactored.actions.Action;
import refactored.parser.Parser;
import refactored.parser.QueryParser;
import refactored.stack.DoubleStack;
import refactored.stack.Stack;
import refactored.window.SimpleWindow;
import refactored.window.Window;

public class Application {
	public static void main(String[] args) {
		Application app = new Application();
		app.setup();
		app.run();
	}

	private Window window;
	private Parser parser;
	private Stack stack;

	public Application() {
		window = SimpleWindow.createWindow("Calculator");
		parser = new QueryParser();
		stack = new DoubleStack();
	}

	private void setup() {
		parser.setOnExit(() -> window.exit());
		parser.setOnEmpty(() -> {});
		parser.setOnClear(() -> stack.clear());
		parser.setOnDigit(digit -> stack.push(digit));
		parser.setOnInvalid(operation -> window.showMessage("'"+operation+"' is an illegal command"));
		parser.setOnBinaryOperator((() -> stack.pop(2)), value -> stack.push(value));
		parser.setOnUnaryOperator(() -> stack.pop(), value -> stack.push(value));
	}

	private void run() {
		window.show(true);
		while (true){
			refreshDisplay();
			parseOperation(window.getInput())
				.execute();
		}
	}

	private void refreshDisplay() {
		window.clear();
		window.appendToOutput(stack.isEmpty() ? parser.getDefaultMessage() : stack.toString());
	}

	private Action parseOperation(String input) {
		return parser.parse(input);
	}


}
