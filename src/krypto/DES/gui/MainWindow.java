package krypto.DES.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import krypto.DES.algorithm.DES;
import krypto.DES.utils.BlockFileStream;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;

public class MainWindow {

	private JFrame frmSodkiJezusek;
	
	private JTextArea plainText;
	private JTextArea encryptedText;
	private JTextArea encryptedText2;
	private JTextArea plainText2;
	
	private byte[] key = {1, 2, 3, 4, 5, 6, 7, 8};
	private DES des = new DES(key);
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField keyField;

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmSodkiJezusek.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSodkiJezusek = new JFrame();
		frmSodkiJezusek.setTitle("Słodki Jezusek");
		frmSodkiJezusek.setBounds(100, 100, 450, 300);
		frmSodkiJezusek.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmSodkiJezusek.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tabbedPane.addTab("Encrypt text", null, panel, null);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane.setResizeWeight(0.5);
		panel.add(splitPane);

		plainText = new JTextArea();
		plainText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				encryptText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				encryptText();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				encryptText();
			}
		});
		plainText.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		splitPane.setLeftComponent(plainText);

		encryptedText = new JTextArea();
		encryptedText.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		splitPane.setRightComponent(encryptedText);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tabbedPane.addTab("Decrypt text", null, panel_1, null);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane_1.setResizeWeight(0.5);
		panel_1.add(splitPane_1);
		
		encryptedText2 = new JTextArea();
		encryptedText2.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				decryptText();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				decryptText();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				decryptText();
			}
		});
		encryptedText2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		splitPane_1.setLeftComponent(encryptedText2);
		
		plainText2 = new JTextArea();
		plainText2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		splitPane_1.setRightComponent(plainText2);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new EmptyBorder(10, 10, 10, 10)));
		tabbedPane.addTab("File encryption", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new EmptyBorder(10, 10, 10, 10)));
		panel_2.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BorderLayout(10, 10));
		
		textField = new JTextField();
		panel_4.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JButton btnSelectSource = new JButton("Select src");
		btnSelectSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectSource();
			}
		});
		panel_4.add(btnSelectSource, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_2.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new GridLayout(1, 0, 20, 20));
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				encryptFile();
			}
		});
		panel_5.add(btnEncrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decryptFile();
			}
		});
		panel_5.add(btnDecrypt);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new EmptyBorder(10, 10, 10, 10)));
		panel_2.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(10, 10));
		
		textField_1 = new JTextField();
		panel_6.add(textField_1, BorderLayout.CENTER);
		textField_1.setColumns(10);
		
		JButton btnSelectDestination = new JButton("Select dest");
		btnSelectDestination.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDestination();
			}
		});
		panel_6.add(btnSelectDestination, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tabbedPane.addTab("Key", null, panel_3, null);
		
		keyField = new JTextField();
		keyField.setText(keyToString(key));
		panel_3.add(keyField);
		keyField.setColumns(10);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
		    generateKey(keyField.getText().getBytes());
		  }
		});
		panel_3.add(addButton);
		
		tabbedPane.setSelectedIndex(2);
	}

	/**
	 * Encrypt text.
	 */
	private void encryptText() {
		String plain = plainText.getText();
		String encrypted = new String(des.enctrypt(plain.getBytes(StandardCharsets.UTF_16BE), false), StandardCharsets.UTF_16BE);
		encryptedText.setText(encrypted);
	}
	
	/**
	 * Decrypt text.
	 */
	private void decryptText() {
		String encrypted = encryptedText2.getText();
		String plain = new String(des.enctrypt(encrypted.getBytes(StandardCharsets.UTF_16BE), true), StandardCharsets.UTF_16BE);
		plainText2.setText(plain);
	}
	
	/**
	 * Encrypt file.
	 */
	private void encryptFile() {
		byte[] plain = null;
		try {
			plain = BlockFileStream.readFileBlocks(textField.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] encrypted = des.enctrypt(plain, false);
		try {
			BlockFileStream.writeFileBlocks(textField_1.getText(), encrypted);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Decrypt file.
	 */
	private void decryptFile() {
		byte[] encrypted = null;
		try {
			encrypted = BlockFileStream.readFileBlocks(textField.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] plain = des.enctrypt(encrypted, true);
		try {
			BlockFileStream.writeFileBlocks(textField_1.getText(), plain);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Select source.
	 */
	private void selectSource() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(frmSodkiJezusek);
		if (result == JFileChooser.APPROVE_OPTION) {
			textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	/**
	 * Select destination.
	 */
	private void selectDestination() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(frmSodkiJezusek);
		if (result == JFileChooser.APPROVE_OPTION) {
			textField_1.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private String keyToString(byte[] key) {
	  StringBuilder sb = new StringBuilder();
	  for (byte b : key) {
      sb.append((char) b);
    }
	  return sb.toString();
	}
	
	private void generateKey(byte[] key) {
	  if (key.length > 8) {
	    key = Arrays.copyOf(key, 8);
	  } else {
	    byte[] tmp = key.clone();
	    
	    key = new byte[] {0, 0, 0, 0, 0, 0, 0, 0};
	    for (int i = 0; i < tmp.length; i++) {
        key[i] = tmp[i];
      }
	  }
	  
	  if (key.length != 8) {
      throw new IllegalArgumentException("" + key.length);
    }
	  
	  des = new DES(key);
	  keyField.setText(keyToString(key));
	}
}




