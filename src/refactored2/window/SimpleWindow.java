package refactored2.window;

import refactored2.window.text.FunctionalTextBox;
import refactored2.window.text.TextBox;
import refactored2.actions.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;


public class SimpleWindow implements Window {
	private JFrame window;
	private JTextArea outputBox;
	private JTextField inputBox;
	private TextBox input;
	private TextBox output;
	private JButton submitButton;
	private Action onChange;
	private Action onSubmit;

	public static Window createWindow(String title) {
		SimpleWindow window = new SimpleWindow(title);
		window.setup();
		return window;
	}

	private SimpleWindow(String title){
		onSubmit = () -> {};
		onChange = () -> {};

		window = new JFrame(title);
		submitButton = new JButton("Send");

		inputBox = new JTextField();
		input = new FunctionalTextBox(inputBox);

		outputBox = new JTextArea();
		output = new FunctionalTextBox(outputBox);
	}

	private void setup() {
		setupInput();
		setupOutput();
		setupSubmitButton();
		setupWindow();
	}

	private void setupInput() {
		inputBox.setFont(new Font("Sansserif",Font.BOLD|Font.ITALIC, 18));
		inputBox.requestFocusInWindow();
		inputBox.addActionListener(e -> onSubmit.execute());
		inputBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!(e.isActionKey() || e.getKeyCode() == KeyEvent.VK_ENTER))
					onChange.execute();
			}
		});
	}

	private void setupOutput() {
		outputBox.setEditable(false);
		outputBox.setBackground(new Color(255,220,220));
		outputBox.setForeground(Color.BLUE);
		outputBox.setFont(new Font("Sansserif",Font.BOLD, 18));
	}

	private void setupSubmitButton() {
		submitButton.setForeground(Color.RED.darker());
		submitButton.setBackground(Color.BLUE);
		submitButton.addActionListener(e -> onSubmit.execute());
	}

	private void setupWindow() {
		window.setLayout(new BorderLayout());
		window.add(createCenter(), BorderLayout.CENTER);
		window.add(createSouth(), BorderLayout.SOUTH);

		window.setSize(800,200);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationByPlatform(true);
	}


	private Component createCenter() {
		return new JScrollPane(outputBox);
	}

	private Component createSouth() {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(inputBox, BorderLayout.CENTER);
		inputPanel.add(submitButton, BorderLayout.EAST);
		return inputPanel;
	}



	@Override
	public TextBox input() {
		return input;
	}

	@Override
	public TextBox output() {
		return output;
	}

	@Override
	public void setOnInputSubmit(Consumer<String> action) {
		onSubmit = () -> action.accept(input.getText());
	}

	@Override
	public void setOnInputChange(Consumer<String> action) {
		onChange = () -> action.accept(input.getText());
	}

	@Override
	public void exit() {
		window.dispose();
		System.exit(0);
	}

	@Override
	public void show(boolean shouldShow) {
		window.setVisible(shouldShow);
	}

	@Override
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(window, message);
	}
	
	
}
