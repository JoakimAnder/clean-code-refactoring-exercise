package exercise;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;



public class SimpleWindow  {
	private JFrame window;
	private JTextArea text;
	private JTextField inString;
	private JButton go;
	private JPanel sPanel;
	private BlockingQueue<String> mq;
	
	public SimpleWindow(String title){
		window = new JFrame(title);
		window.setLayout(new BorderLayout());
		text = new JTextArea();
		text.setEditable(false);
		text.setBackground(new Color(255,220,220));
		text.setForeground(Color.BLUE);
		text.setFont(new Font("Sansserif",Font.BOLD, 18));
		window.add(new JScrollPane(text), BorderLayout.CENTER);
		sPanel = new JPanel();
		sPanel.setLayout(new BorderLayout());
		window.add(sPanel,BorderLayout.SOUTH);
		inString = new JTextField();
		inString.setFont(new Font("Sansserif",Font.BOLD|Font.ITALIC, 18));
		inString.requestFocusInWindow();
		go = new JButton("Send");
		go.setForeground(Color.RED.darker());
		go.setBackground(Color.BLUE);
		mq = new ArrayBlockingQueue<String>(100);
		ActionListener goAction = new GoListener();
		go.addActionListener(goAction);
		inString.registerKeyboardAction(goAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		
		sPanel.add(inString,BorderLayout.CENTER);
		sPanel.add(go,BorderLayout.EAST);
		window.setSize(800,200);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationByPlatform(true);
		window.setVisible(true);
	}
	
	class GoListener implements ActionListener{		
		public void actionPerformed(ActionEvent e) {
			try {
				mq.put(inString.getText());
				inString.setText("");
				inString.requestFocusInWindow();
			} catch (InterruptedException e1) {
					e1.printStackTrace();
			}
		}		
	}
	
	public String getString(){
		
		try {
			return mq.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "Should not happen";
		}
	}
	
	public void addString(String s){
		text.append(s);
	}
	
	public void clear(){
		text.setText("");
	}
	
	public void exit() {
		window.dispose();
		System.exit(0);
	}
	
	
}
