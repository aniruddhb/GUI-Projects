import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.border.*;

public class SimpleDrawGUI {
	
	// class variables
	JPanel[][] paint_grid = new JPanel[20][20];
	JFrame window;
	JRadioButton red_button;
  JRadioButton green_button;
  JRadioButton blue_button;
  JMenuBar menu_bar;
  JMenu file_menu;
  JMenu edit_menu;
  JMenuItem open;
  JMenuItem save;
  JMenuItem clear;
  JButton clr_button;
  JRadioButton open_chooser;
	Color set = Color.RED;

	public void create() {
		// setting JFrame constants
		window = new JFrame("Paint Program");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setBounds(200, 200, 600, 600);
		window.setLayout(new BorderLayout());

		// add panels to paint_grid
		for (int r = 0; r < paint_grid.length; r++) {
			for (int c = 0; c < paint_grid[0].length; c++) {
				JPanel one_panel = new JPanel();
				one_panel.setPreferredSize(new Dimension(30, 30));
				one_panel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY));
				paint_grid[r][c] = one_panel;
			}
		}

		// sub JPanels
		JPanel big_pane = new JPanel();
		big_pane.setLayout(new BorderLayout());
		big_pane.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GRAY, Color.BLACK, Color.GRAY, Color.BLACK));
		Box.Filler top = new Box.Filler(new Dimension(2, 2), new Dimension(2, 2), new Dimension(2, 2));
		Box.Filler bottom = new Box.Filler(new Dimension(2, 2), new Dimension(2, 2), new Dimension(2, 2));
		Box.Filler right = new Box.Filler(new Dimension(1, 1), new Dimension(1, 1), new Dimension(1, 1));
		Box.Filler left = new Box.Filler(new Dimension(1, 1), new Dimension(1, 1), new Dimension(1, 1));
		JPanel button_panel = new JPanel();
		button_panel.setPreferredSize(new Dimension(500, 50));
		button_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		button_panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED), "Colors", TitledBorder.LEADING, TitledBorder.CENTER));
		JPanel mid_grid = new JPanel();
		mid_grid.setLayout(new GridLayout(20, 20, 0, 0));
		Box.Filler west_fill = new Box.Filler(new Dimension(30, 30), new Dimension(30, 30), new Dimension(30, 30));
		Box.Filler east_fill = new Box.Filler(new Dimension(30, 30), new Dimension(30, 30), new Dimension(30, 30));
		Box.Filler north_fill = new Box.Filler(new Dimension(5, 5), new Dimension(5, 5), new Dimension(5, 5));
		big_pane.add(left, BorderLayout.EAST);
		big_pane.add(mid_grid, BorderLayout.CENTER);
		big_pane.add(right, BorderLayout.WEST);
		big_pane.add(top, BorderLayout.NORTH);
		big_pane.add(bottom, BorderLayout.SOUTH);

		// buttons panel
		JPanel temp = new JPanel();
    clr_button = new JButton("Clear");
		red_button = new JRadioButton("Red");
		red_button.setPreferredSize(new Dimension(80, 15));
		green_button = new JRadioButton("Green");
		green_button.setPreferredSize(new Dimension(80, 15));
		blue_button = new JRadioButton("Blue");
		blue_button.setPreferredSize(new Dimension(80, 15));
    open_chooser = new JRadioButton("Choose");
    open_chooser.setPreferredSize(new Dimension(80, 15));
		ButtonGroup b = new ButtonGroup();
		b.add(red_button);
		b.add(green_button);
		b.add(blue_button);
    b.add(open_chooser);
		button_panel.add(red_button);
		button_panel.add(new Box.Filler(new Dimension(10, 10), new Dimension(10, 10), new Dimension(10, 10)));
		button_panel.add(green_button);
		button_panel.add(new Box.Filler(new Dimension(10, 10), new Dimension(10, 10), new Dimension(10, 10)));
		button_panel.add(blue_button);
        button_panel.add(new Box.Filler(new Dimension(10, 10), new Dimension(10, 10), new Dimension(10, 10)));
        button_panel.add(open_chooser);
		temp.add(new Box.Filler(new Dimension(10, 10), new Dimension(10, 10), new Dimension(10, 10)));
		temp.add(button_panel);
		temp.add(new Box.Filler(new Dimension(10, 10), new Dimension(10, 10), new Dimension(10, 10)));
		JPanel top_to_bottom = new JPanel();
		JPanel clear_temp = new JPanel();
		clear_temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		top_to_bottom.setLayout(new BoxLayout(top_to_bottom, BoxLayout.Y_AXIS));
		top_to_bottom.add(temp);
    clear_temp.add(clr_button);
		top_to_bottom.add(clear_temp);

		// adds panels to mid_grid
		for (int r = 0; r < paint_grid.length; r++) {
			for (int c = 0; c < paint_grid[0].length; c++) {
				mid_grid.add(paint_grid[r][c]);
			}
		}

    // menu items
    menu_bar = new JMenuBar();
    file_menu = new JMenu("File");
    edit_menu = new JMenu("Edit");
    open = new JMenuItem("Open");
    save = new JMenuItem("Save");
    clear = new JMenuItem("Clear");
    file_menu.add(open);
    file_menu.add(save);
    edit_menu.add(clear);
    menu_bar.add(file_menu);
    menu_bar.add(edit_menu);
    window.setJMenuBar(menu_bar);

		// adds content
		window.getContentPane().add(top_to_bottom, BorderLayout.SOUTH);
		window.getContentPane().add(big_pane, BorderLayout.CENTER);
		window.getContentPane().add(west_fill, BorderLayout.WEST);
		window.getContentPane().add(east_fill, BorderLayout.EAST);
		window.getContentPane().add(north_fill, BorderLayout.NORTH);

    // visibility
    window.setResizable(true);
    window.pack();
    window.setVisible(true);

		class myRadioListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == red_button) {
					set = Color.RED;
				} else if (e.getSource() == green_button) {
					set = Color.GREEN;
				} else if (e.getSource() == blue_button) {
					set = Color.BLUE;
				} else if (e.getSource() == clr_button) {
                    for (int r = 0; r < paint_grid.length; r++) {
                        for (int c = 0; c < paint_grid[0].length; c++) {
                            paint_grid[r][c].setBackground(new Color(240, 240, 240));
                        }
                    }
                } else if (e.getSource() == clear) {
                    for (int r = 0; r < paint_grid.length; r++) {
                        for (int c = 0; c < paint_grid[0].length; c++) {
                            paint_grid[r][c].setBackground(new Color(240, 240, 240));
                        }
                    }
                } else if (e.getSource() == open_chooser) {
                    set = JColorChooser.showDialog(null, "Color Picker", Color.BLACK);
                } else if (e.getSource() == open) {
                    JFileChooser jc = new JFileChooser();
                    jc.showOpenDialog(null);
                    File f = jc.getSelectedFile();
                    Scanner in = null;
                    int x = 0;
                    int y = 0;
                    try {
                        in = new Scanner(f);
                        String mag_num = in.next();
                        if (!mag_num.equals("P3")) {
                            System.out.println("Please only use a .PPM file");
                            // throw new IllegalFormatException(); Java says that java.util.IllegalFormatException isn't public in java.util import, so I can't access it at all
                        } else {
                            while (in.hasNextLine()) {
                                String next_line = in.nextLine();
                                if (next_line.contains("#")) {

                                } else if (next_line.length() != 0 && next_line.length() > 17) {
                                    StringTokenizer st = new StringTokenizer(next_line);
                                    int counter = 0;
                                    while (st.hasMoreTokens()) {
                                        if (counter % 3 == 0 && counter != 0) {
                                            y++;
                                        }
                                        int r = Integer.parseInt(st.nextToken());
                                        int g = Integer.parseInt(st.nextToken());
                                        int b = Integer.parseInt(st.nextToken());
                                        paint_grid[x][y].setBackground(new Color(r, g, b));
                                        counter += 3;
                                    }
                                    x++;
                                } else {

                                }
                                y = 0;
                            }
                        }
                    } catch (FileNotFoundException fnfe) {
                        // nothing
                    }
                } else if (e.getSource() == save) {
                    JFileChooser jc = new JFileChooser();
                    int save_val = jc.showSaveDialog(null);
                    if (save_val == JFileChooser.APPROVE_OPTION) {
                        try {
                            FileWriter fw = new FileWriter(jc.getSelectedFile(), true);
                            fw.write("P3\r\n");
                            fw.write("# Written by Aniruddh Bharadwaj, Period 3 - APCS\r\n");
                            fw.write("# Monkeys help the universe expand\r\n");
                            String len = paint_grid.length + " " + paint_grid[0].length + "\r\n";
                            fw.write(len);
                            fw.write("255\r\n");
                            for (int r = 0; r < paint_grid.length; r++) {
                                for (int c = 0; c < paint_grid.length; c++) {
                                    Color col = paint_grid[r][c].getBackground();
                                    int r1 = col.getRed();
                                    int g1 = col.getGreen();
                                    int b1 = col.getBlue();
                                    String leng = r1 + " " + g1 + " " + b1 + " ";
                                    fw.write(leng);
                                }
                                fw.write("\r\n");
                            }
                            fw.close();
                        } catch (IOException e1) {
                            // nothing
                        }
                    }
                }
			}
		}

		class myMouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					((JPanel)e.getSource()).setBackground(set);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					((JPanel)e.getSource()).setBackground(new Color(240, 240, 240));
				}
			}

      @Override
      public void mouseDragged(MouseEvent e) {
        if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
          ((JPanel)e.getSource()).setBackground(set);
        } else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK) {
          ((JPanel)e.getSource()).setBackground(new Color(240, 240, 240));
        }
      }


			@Override
			public void mouseEntered(MouseEvent e) {
				if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
					((JPanel)e.getSource()).setBackground(set);
				} else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK) {
					((JPanel)e.getSource()).setBackground(new Color(240, 240, 240));
				}
			}
		

		// registers GUI elements
		myRadioListener r = new myRadioListener();
		red_button.addActionListener(r);
		green_button.addActionListener(r);
		blue_button.addActionListener(r);
    open_chooser.addActionListener(r);
    clr_button.addActionListener(r);
    clear.addActionListener(r);
    open.addActionListener(r);
    save.addActionListener(r);

    // adds mouselisteners
		myMouseListener m = new myMouseListener();
		for (int i = 0; i < paint_grid.length; i++) {
			for (int j = 0; j < paint_grid[0].length; j++) {
				paint_grid[i][j].addMouseListener(m);
				paint_grid[i][j].addMouseMotionListener(m);
			}
		}
	}

	public static void main(String[] args) {
		SimpleDrawGUI gui = new SimpleDrawGUI();
        gui.create();
	}
}
