package refactored2;

import refactored2.parser.Parser;
import refactored2.parser.QueryParser;
import refactored2.window.SimpleWindow;
import refactored2.window.Window;


public class Calculator {
	public static void main(String[] args) {
		Calculator app = new Calculator();
		app.setup();
	}

	private Window window;
	private Parser parser;

	private Calculator() {
		window = SimpleWindow.createWindow("Calculator");
		parser = new QueryParser();
	}

	private void setup() {
		window.setOnInputSubmit(this::onSubmit);
		parser.setOnError(s -> window.showErrorMessage(s));
		parser.setOnQuit(() -> window.exit());

		refreshDisplay();
		window.show(true);
	}

	private void onSubmit(String input) {
		parser.parse(input);
		window.input().clear();
		refreshDisplay();
	}

	private void refreshDisplay() {
		window.output().set(parser.getMessage());
	}


}
