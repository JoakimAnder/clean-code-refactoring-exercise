package refactored.window;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.*;



public class SimpleWindow implements Window {
	private JFrame window;
	private JTextArea output;
	private BlockingQueue<String> mq;
	private JTextField input;
	private ActionListener onSubmit;

	public static Window createWindow(String title) {
		SimpleWindow window = new SimpleWindow(title);
		window.setup();
		return window;
	}

	private SimpleWindow(String title){
		window = new JFrame(title);
		output = new JTextArea();
		input = new JTextField();
		mq = new ArrayBlockingQueue<>(100);
		onSubmit = event -> {
			try {
				mq.put(input.getText());
				input.setText("");
				input.requestFocusInWindow();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		};
	}

	private void setup() {
		setupInput();
		setupOutput();
		setupWindow();
	}

	private void setupInput() {
		input.setFont(new Font("Sansserif",Font.BOLD|Font.ITALIC, 18));
		input.requestFocusInWindow();
		input.registerKeyboardAction(onSubmit, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void setupOutput() {
		output = new JTextArea();
		output.setEditable(false);
		output.setBackground(new Color(255,220,220));
		output.setForeground(Color.BLUE);
		output.setFont(new Font("Sansserif",Font.BOLD, 18));
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
		return new JScrollPane(output);
	}

	private Component createSouth() {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(input, BorderLayout.CENTER);
		inputPanel.add(createSubmitButton(), BorderLayout.EAST);
		return inputPanel;
	}

	private Component createSubmitButton() {
		JButton submitButton = new JButton("Send");
		submitButton.setForeground(Color.RED.darker());
		submitButton.setBackground(Color.BLUE);
		submitButton.addActionListener(onSubmit);
		return submitButton;
	}
	
	@Override
	public String getInput(){
		try {
			return mq.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "Should not happen";
		}
	}
	
	@Override
	public void appendToOutput(String s){
		output.append(s);
	}
	
	@Override
	public void clear(){
		output.setText("");
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
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(window, message);
	}
	
	
}
