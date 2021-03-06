import java.awt.Graphics;
import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import javax.swing.JComboBox;

import javax.swing.*;

public class ClientPanel extends JPanel {
	
	private JButton addItem, removeItem, sendList, clearAllItems, confirmOrder, submit, cancel;
	private JOptionPane sumOrder;
	private JTextArea infoTxt;
	private JComboBox<String> menuBox;
	private JLabel price, inBasket, orderSumLbl, custInfoLbl, totalPriceLbl;
	private HashMap<String, Integer> basket;
	private HashMap<String, String> menu;
	private JFrame diag;
	private JPanel mypanel;
	private ControlsListener l;
	
	String host;
	int port;
	
	private Order myOrder;
	
	public ClientPanel(HashMap<String, String> menu, String host, int port) {
		
		this.host = host;
		this.port = port;
		
		addItem = new JButton("Add");
		confirmOrder = new JButton("Add");
		removeItem = new JButton("Remove");
		sendList = new JButton("Send");
		clearAllItems = new JButton("Clear cart");
		inBasket = new JLabel("Basket: 0");
		menuBox = new JComboBox<String>();
		sumOrder = new JOptionPane("TEST");
		
		basket = new HashMap<String, Integer>();
		
		this.menu = menu;
		for (String item : menu.keySet()) {
			menuBox.addItem(item);
			basket.put(item, 0);
		}
		
		menuBox.setSelectedItem(null);
		price = new JLabel("Price: 0");
		
		add(menuBox);
		add(price);
		add(inBasket);
		add(addItem);
		add(removeItem);
		add(sendList);
		add(clearAllItems);
		
		// end of graphics
		
		
		// ControlsListener is a private class within this class to handle button events (see below)
		l = new ControlsListener();
		
		 addItem.addActionListener(l);
		 removeItem.addActionListener(l);
		 sendList.addActionListener(l);
		 clearAllItems.addActionListener(l);
		 menuBox.addActionListener(l);
		 confirmOrder.addActionListener(l);
		 
		
	}
	
	
			 
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
	
	// action listener for button clicks
	private class ControlsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        	// adds rest menu item to basket
            if(e.getSource() == addItem)
            {
            	String current = menuBox.getSelectedItem().toString();
            	int newVal = basket.get(current) + 1;
            	basket.put(current, newVal);
            	inBasket.setText("Basket: " + Integer.toString(newVal));
            	repaint();
            }
            // removed item from basket
            else if (e.getSource() == removeItem)
            {
            	String current = menuBox.getSelectedItem().toString();
            	int newVal = basket.get(current) - 1;
            	if (newVal < 0 )
            		newVal = 0;
            	basket.put(current, newVal);
            	inBasket.setText("Basket: " + Integer.toString(newVal));
            	repaint();
            // clear basket
            } else if (e.getSource() == clearAllItems) {
            	
            	for (String item : basket.keySet()) {
            		basket.put(item, 0);
            	}
            	inBasket.setText("Basket: 0");
            	repaint();
            
            // if a different item is selected in GUI, update its price label
            } else if (e.getSource() == menuBox ) {
            	price.setText("Price: " + menu.get(menuBox.getSelectedItem()).toString());
            	inBasket.setText("Basket: " + Integer.toString(basket.get(menuBox.getSelectedItem())));
            	repaint();
            	
            // if source is the "Send" button, open a verification panel with order summary
            } else if (e.getSource() == sendList)
            {
            	diag = new JFrame();
            	mypanel = new JPanel();
            	mypanel.setSize(400, 150);
            	diag.setSize(400,  150);
            	
            	orderSumLbl = new JLabel("Your order: \n" + basket.toString());
            	
            	// total price
            	int totalPrice = 0;
        		for (String item : basket.keySet()) {
        			totalPrice += basket.get(item) * Integer.parseInt(menu.get(item));
        		}
                 	
            	totalPriceLbl = new JLabel("Total Price: " + totalPrice);
            	submit = new JButton("Submit");
            	cancel = new JButton("Cancel");
            	custInfoLbl = new JLabel("Shipping details:");
            	infoTxt = new JTextArea("Name, Phone, Address");
            	infoTxt.selectAll();
                submit.addActionListener(l);
       		    cancel.addActionListener(l);
            	
       		    mypanel.add(orderSumLbl);
       		    mypanel.add(totalPriceLbl);
            	mypanel.add(custInfoLbl);
            	mypanel.add(infoTxt);
            	mypanel.add(submit);
            	mypanel.add(cancel);
            	
            	diag.add(mypanel);
            	
            	diag.setVisible(true);
            	mypanel.setVisible(true);
            	        
            // if "Submit" is pressed, send info to server;
            } else if (e.getSource() == submit) {
            	
            	Order myOrder = new Order(infoTxt.getText(), basket);
            	ClientComm cm = new ClientComm(host, port);
            	
            	cm.sendOrder(myOrder);
            	
            }
        }
    }

		

}
