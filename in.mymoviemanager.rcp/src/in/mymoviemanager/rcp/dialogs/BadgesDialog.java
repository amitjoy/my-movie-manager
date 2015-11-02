package in.mymoviemanager.rcp.dialogs;

import in.mymoviemanager.rcp.Activator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Show All Predefined badges dialog box
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class BadgesDialog extends JFrame {

	private static final long serialVersionUID = -5864877553347555804L;

	public BadgesDialog() {
		super("All Predefined Badges"); //$NON-NLS-1$
		setSize(1190, 595);
		setContentPane(new ImagePanel("icons/all_badges.png")); //$NON-NLS-1$
		Container con = getContentPane();
		setUndecorated(true);
		con.setPreferredSize(new Dimension(1190, 561));
		con.setLayout(null);
		JLabel closeLabel = new JLabel();
		closeLabel.setForeground(new Color(255, 255, 255));
		closeLabel.setBounds(1130, 20, 75, 20);
		con.add(closeLabel);
		pack();
		addListeners();
		centerWindow();
	}

	private void addListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}
		});
	}

	private void centerWindow() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getSize().width) / 2,
				(screenSize.height - getSize().height) / 2);
	}

	class ImagePanel extends JPanel {

		private static final long serialVersionUID = 5868261704795322267L;
		private Image img;

		public ImagePanel(String img) {
			this(Activator.getAWTImage(img));
		}

		public ImagePanel(Image img) {
			setOpaque(true);
			this.img = img;
			Dimension size = new Dimension(img.getWidth(null),
					img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			setLayout(null);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}

	}
}
