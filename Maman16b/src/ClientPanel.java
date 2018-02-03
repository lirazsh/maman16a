import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClientPanel extends JPanel {
	
	private JComboBox<String> fromComboBox, toComboBox;
	private JTextArea amountTextArea;
	private JButton calcBttn;
	private JLabel fromLbl, toLbl, resultLbl;
	
	private String host;
	private int port;
	
	public ClientPanel(String host, int port) {
		
		this.host = host;
		this.port = port;
		
		fromComboBox = new JComboBox<String>();
		toComboBox = new JComboBox<String>();
		amountTextArea = new JTextArea("Enter amount here");
		amountTextArea.selectAll();
		calcBttn = new JButton("Calculate");
		fromLbl = new JLabel("Source Currency");
		toLbl = new JLabel("Target Currency");
		resultLbl = new JLabel("Result: ");
		
		toComboBox.addItem("usd");
		toComboBox.addItem("euro");
		toComboBox.addItem("gbp");
		toComboBox.addItem("ils");
		toComboBox.addItem("pesso");
		
		fromComboBox.addItem("usd");
		fromComboBox.addItem("euro");
		fromComboBox.addItem("gbp");
		fromComboBox.addItem("ils");
		fromComboBox.addItem("pesso");
		
		calcBttn.addActionListener(new ControlsListener());
	
		
		add(fromLbl);
		add(fromComboBox);
		add(toLbl);
		add(toComboBox);
		add(amountTextArea);
		add(calcBttn);
		add(resultLbl);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
	
	// action listener for button clicks
		private class ControlsListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            if(e.getSource() == calcBttn)
	            {
	            	ClientComm cc = new ClientComm(host, port);
	            	String request = (String) fromComboBox.getSelectedItem() + "/" + toComboBox.getSelectedItem() +
	            			"/" + Float.parseFloat(amountTextArea.getText());
	            	
	            	String[] tmpRes = cc.convert(request).split(":");
	            	
	            	
	            	resultLbl.setText("Result: " + tmpRes[1]);

	            	repaint();
	            }
	        }
	    }
}