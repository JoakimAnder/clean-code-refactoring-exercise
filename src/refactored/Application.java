package refactored;

import refactored.actions.Action;
import refactored.parser.Parser;
import refactored.parser.QueryParser;
import refactored.stack.DoubleStack;
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
	private DoubleStack stack;

	public Application() {
		window = SimpleWindow.createWindow("Calculator");
		parser = new QueryParser();
		stack = new DoubleStack();
	}

	private void setup() {
		parser.setOnExit(() -> window.exit());
		parser.setOnEmpty(() -> {});
		parser.setOnClear(() -> {window.clear(); stack.clear();});
		parser.setOnDigit(digit -> stack.push(digit));
		parser.setOnInvalid(operation -> window.showMessage("'"+operation+"' is an illegal command"));
		parser.setOnBinaryOperator((() -> new Double[]{stack.pop(), stack.pop()}), value -> stack.push(value));
		parser.setOnUnaryOperator(() -> stack.pop(), value -> stack.push(value));
	}

	private void run() {
		window.show(true);
		while (true){
			refreshDisplay();
			parseOperation()
				.execute();
		}
	}

	private void refreshDisplay() {
		window.clear();
		window.appendToOutput(stack.depth() == 0 ? parser.getDefaultMessage() : stack.toString());
	}

	private Action parseOperation() {
		return parser.parse(window.getInput());
	}


}
