import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class LifeGUI {
    // Life class variables
    boolean[][] currentState = new boolean[30][30];
    boolean[][] nextState = new boolean[30][30];

    // GUI class variables
    JFrame window;
    JPanel[][] life_grid = new JPanel[30][30];
    JMenuBar bar;
    JMenu file, edit, runspeed;
    JMenuItem clear, open, save, single, continuous;
    JRadioButton red, green, blue, color_chooser;
    JButton pause, run, restart;
    JColorChooser chooser;
    JSlider delay;
    ButtonGroup b_group;
    Color set;
    boolean sing;
    boolean cont;

    // Timer
    javax.swing.Timer timer = new javax.swing.Timer(100, null);
    int counter = 0;

    // usual GUI stuff, adds gui elements to listeners as well
    public void createGUI() {
        // set window constants
        window = new JFrame("Life with GUI - By Aniruddh Bharadwaj");
        window.setBounds(200, 200, 600, 800);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        // menu
        bar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        clear = new JMenuItem("Clear");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        single = new JMenuItem("Single-Step Mode");
        continuous = new JMenuItem("Continuous Mode");
        runspeed = new JMenu("Run-Speed");
        file.add(open);
        file.add(save);
        edit.add(clear);
        runspeed.add(single);
        runspeed.add(continuous);
        bar.add(file);
        bar.add(runspeed);
        bar.add(edit);
        window.setJMenuBar(bar);

        // adds JPanels to JPanel 2D array
        for (int r = 0; r < life_grid.length; r++) {
            for (int c = 0; c < life_grid[0].length; c++) {
                JPanel pix = new JPanel();
                pix.setBackground(Color.WHITE);
                pix.setMaximumSize(new Dimension(5, 5));
                pix.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                life_grid[r][c] = pix;
            }
        }

        // works on the mid_grid
        JPanel mid_grid = new JPanel();
        mid_grid.setLayout(new BorderLayout());

        // adds JPanels to a sub JPanel
        JPanel mid_board = new JPanel();
        mid_board.setLayout(new GridLayout(30, 30));
        for (int r = 0; r < life_grid.length; r++) {
            for (int c = 0; c < life_grid[0].length; c++) {
                mid_board.add(life_grid[r][c]);
            }
        }

        // adds stuff to mid_grid
        mid_grid.add(new Box.Filler(new Dimension(20, 20), new Dimension(20, 20), new Dimension(20, 20)), BorderLayout.NORTH);
        mid_grid.add(new Box.Filler(new Dimension(15, 15), new Dimension(15, 15), new Dimension(15, 15)), BorderLayout.EAST);
        mid_grid.add(new Box.Filler(new Dimension(15, 15), new Dimension(15, 15), new Dimension(15, 15)), BorderLayout.WEST);
        mid_grid.add(new Box.Filler(new Dimension(10, 10), new Dimension(10, 10), new Dimension(10, 10)), BorderLayout.SOUTH);
        mid_grid.add(mid_board, BorderLayout.CENTER);

        // adds buttons
        JPanel full_button_panel = new JPanel();
        full_button_panel.setLayout(new BorderLayout());
        JPanel color_panel = new JPanel();
        color_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel rgb_panel = new JPanel();
        rgb_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        rgb_panel.setBorder(new TitledBorder("Colors"));
        red = new JRadioButton("Red");
        green = new JRadioButton("Green");
        blue = new JRadioButton("Blue");
        b_group = new ButtonGroup();
        b_group.add(red);
        b_group.add(green);
        b_group.add(blue);
        rgb_panel.add(red);
        rgb_panel.add(green);
        rgb_panel.add(blue);
        color_panel.add(rgb_panel);
        JPanel choose_color = new JPanel();
        choose_color.setBorder(new TitledBorder("Color Picker"));
        color_chooser = new JRadioButton("Choose Your Own Color!");
        b_group.add(color_chooser);
        choose_color.add(color_chooser);
        color_panel.add(new Box.Filler(new Dimension(30, 50), new Dimension(30, 50), new Dimension(30, 50)));
        color_panel.add(choose_color);
        JPanel but_pan = new JPanel();
        but_pan.setLayout(new BorderLayout());
        JPanel clear_panel = new JPanel();
        clear_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        pause = new JButton("Pause");
        run = new JButton("Run");
        restart = new JButton("Stop and Clear");
        clear_panel.add(new Box.Filler(new Dimension(20, 20), new Dimension(20, 20), new Dimension(20, 20)));
        clear_panel.add(run);
        clear_panel.add(new Box.Filler(new Dimension(20, 20), new Dimension(20, 20), new Dimension(20, 20)));
        clear_panel.add(pause);
        clear_panel.add(new Box.Filler(new Dimension(20, 20), new Dimension(20, 20), new Dimension(20, 20)));
        clear_panel.add(restart);
        clear_panel.add(new Box.Filler(new Dimension(20, 20), new Dimension(20, 20), new Dimension(20, 20)));
        JPanel b_panel = new JPanel();
        b_panel.setLayout(new BorderLayout());
        b_panel.add(new Box.Filler(new Dimension(30, 30), new Dimension(30, 30), new Dimension(30, 30)));
        JPanel s_panel = new JPanel();
        s_panel.setBorder(new TitledBorder("Delay for Continuous Speed-Mode (MS)"));
        delay = new JSlider(0, 1000, 500);
        delay.setPreferredSize(new Dimension(400, 50));
        delay.setMajorTickSpacing(200);
        delay.setPaintLabels(true);
        delay.setPaintTicks(true);
        s_panel.add(delay);
        b_panel.add(new Box.Filler(new Dimension(30, 10), new Dimension(30, 10), new Dimension(30, 10)), BorderLayout.SOUTH);
        b_panel.add(s_panel, BorderLayout.CENTER);
        b_panel.add(new Box.Filler(new Dimension(40, 20), new Dimension(40, 20), new Dimension(40, 20)), BorderLayout.NORTH);
        b_panel.add(new Box.Filler(new Dimension(30, 10), new Dimension(30, 10), new Dimension(30, 10)), BorderLayout.EAST);
        b_panel.add(new Box.Filler(new Dimension(30, 10), new Dimension(30, 10), new Dimension(30, 10)), BorderLayout.WEST);
        but_pan.add(clear_panel, BorderLayout.CENTER);
        but_pan.add(new Box.Filler(new Dimension(20, 10), new Dimension(20, 10), new Dimension(20, 10)), BorderLayout.NORTH);
        but_pan.add(new Box.Filler(new Dimension(20, 10), new Dimension(20, 10), new Dimension(20, 10)), BorderLayout.SOUTH);

        full_button_panel.add(color_panel, BorderLayout.NORTH);
        full_button_panel.add(b_panel, BorderLayout.CENTER);
        full_button_panel.add(but_pan, BorderLayout.SOUTH);

        // adds stuff to window
        window.getContentPane().add(mid_grid, BorderLayout.CENTER);
        window.getContentPane().add(full_button_panel, BorderLayout.SOUTH);

        // window stuff
        window.pack();
        window.setResizable(true);
        window.setVisible(true);

        // registers GUI elements with listeners
        actions a = new actions();
        red.addActionListener(a);
        green.addActionListener(a);
        blue.addActionListener(a);
        color_chooser.addActionListener(a);
        run.addActionListener(a);
        pause.addActionListener(a);
        clear.addActionListener(a);
        open.addActionListener(a);
        save.addActionListener(a);
        restart.addActionListener(a);
        timer.addActionListener(a);
        single.addActionListener(a);
        continuous.addActionListener(a);

        changes c = new changes();
        delay.addChangeListener(c);

        mouses m = new mouses();
        for (int i = 0; i < life_grid.length; i++) {
            for (int j = 0; j < life_grid[0].length; j++) {
                life_grid[i][j].addMouseListener(m);
                life_grid[i][j].addMouseMotionListener(m);
            }
        }
    }

    private class actions implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == red) {
                set = Color.RED;
                for (int r = 0; r < life_grid.length; r++) {
                    for (int c = 0; c < life_grid.length; c++) {
                        if (!life_grid[r][c].getBackground().equals(Color.WHITE)) {
                            life_grid[r][c].setBackground(set);
                        }
                    }
                }
            } else if (e.getSource() == green) {
                set = Color.GREEN;
                for (int r = 0; r < life_grid.length; r++) {
                    for (int c = 0; c < life_grid.length; c++) {
                        if (!life_grid[r][c].getBackground().equals(Color.WHITE)) {
                            life_grid[r][c].setBackground(set);
                        }
                    }
                }
            } else if (e.getSource() == blue) {
                set = Color.BLUE;
                for (int r = 0; r < life_grid.length; r++) {
                    for (int c = 0; c < life_grid.length; c++) {
                        if (!life_grid[r][c].getBackground().equals(Color.WHITE)) {
                            life_grid[r][c].setBackground(set);
                        }
                    }
                }
            } else if (e.getSource() == color_chooser) {
                chooser = new JColorChooser(Color.BLACK);
                set = chooser.showDialog(null, "Color Chooser", Color.BLACK);
                for (int r = 0; r < life_grid.length; r++) {
                    for (int c = 0; c < life_grid.length; c++) {
                        if (!life_grid[r][c].getBackground().equals(Color.WHITE)) {
                            life_grid[r][c].setBackground(set);
                        }
                    }
                }
            } else if (e.getSource() == run) {
                if (cont == true) {
                    timer.setDelay(delay.getValue());
                    timer.start();
                } else if (sing == true) {
                    fillCurrentState();
                    runWithGUI();
                    window.setTitle("Life with GUI - Generation " + counter);
                }
            }  else if (e.getSource() == timer) {
                window.setTitle("Life with GUI - Generation " + counter);
                fillCurrentState();
                runWithGUI();
                window.setTitle("Life with GUI - Generation " + counter);
            } else if (e.getSource() == pause) {
                timer.stop();
            } else if (e.getSource() == open) {
                if (timer.isRunning()) {
                    timer.stop();
                }
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
                                    Color p1 = new Color(r, g, b);
                                    if (!p1.equals(Color.WHITE)) {
                                        set = new Color(r, g, b);
                                    }
                                    life_grid[x][y].setBackground(new Color(r, g, b));
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
                counter = 0;
                fillCurrentState();
                clearNextState();
            } else if (e.getSource() == save) {
                if (timer.isRunning()) {
                    timer.stop();
                }
                JFileChooser jc = new JFileChooser();
                int save_val = jc.showSaveDialog(null);
                if (save_val == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter fw = new FileWriter(jc.getSelectedFile(), true);
                        fw.write("P3\r\n");
                        fw.write("# Written by Aniruddh Bharadwaj, Period 3 - APCS\r\n");
                        fw.write("# Monkeys help the universe expand\r\n");
                        String len = life_grid.length + " " + life_grid[0].length + "\r\n";
                        fw.write(len);
                        fw.write("255\r\n");
                        for (int r = 0; r < life_grid.length; r++) {
                            for (int c = 0; c < life_grid.length; c++) {
                                Color col = life_grid[r][c].getBackground();
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
                } else {

                }
            } else if (e.getSource() == clear) {
                counter = 0;
                window.setTitle("Life with GUI");
                for (int r = 0; r < life_grid.length; r++) {
                    for (int c = 0; c < life_grid[0].length; c++) {
                        life_grid[r][c].setBackground(Color.WHITE);
                    }
                }
                timer.stop();
            } else if (e.getSource() == restart) {
                counter = 0;
                window.setTitle("Life with GUI");
                for (int r = 0; r < life_grid.length; r++) {
                    for (int c = 0; c < life_grid[0].length; c++) {
                        life_grid[r][c].setBackground(Color.WHITE);
                    }
                }
                timer.stop();
            } else if (e.getSource() == single) {
                sing = true;
                cont = false;
                if (timer.isRunning()) {
                    timer.stop();
                }
            } else if (e.getSource() == continuous) {
                cont = true;
                sing = false;
            }
        }
    }

    private class changes implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == delay) {
                if (timer.isRunning()) {
                    timer.stop();
                    timer.setDelay(delay.getValue());
                    timer.start();
                } else {
                    timer.setInitialDelay(delay.getValue());
                }
            }
        }
    }

    private class mouses extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
                ((JPanel)e.getSource()).setBackground(set);
            } else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK) {
                ((JPanel)e.getSource()).setBackground(Color.WHITE);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
                ((JPanel)e.getSource()).setBackground(set);
            } else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK) {
                ((JPanel)e.getSource()).setBackground(Color.WHITE);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                ((JPanel)e.getSource()).setBackground(set);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                ((JPanel)e.getSource()).setBackground(Color.WHITE);
            }
        }
    }

    public void showOnScreen() {
        for (int r = 0; r < life_grid.length; r++) {
            for (int c = 0; c < life_grid.length; c++) {
                if (currentState[r][c] == true) {
                    life_grid[r][c].setBackground(set);
                } else if (currentState[r][c] == false) {
                    life_grid[r][c].setBackground(Color.WHITE);
                }
            }
        }
    }

    public void runWithGUI() {
        nextGeneration();
        showOnScreen();
        counter++;
    }

    public void runLife(int numGenerations){
        for (int generationCount = 0; generationCount < numGenerations; generationCount++){
            for (int outer = 0; outer < currentState.length; outer++){
                for (int inner = 0; inner < currentState[0].length; inner++){
                    int amountOfNeighbors = countNeighbors(outer, inner);

                    if (currentState[outer][inner] == false && (amountOfNeighbors == 3)){
                        nextState[outer][inner] = true;
                    }

                    if (currentState[outer][inner] == true && (amountOfNeighbors < 2)){
                        nextState[outer][inner] = false;
                    }

                    if (currentState[outer][inner] == true && (amountOfNeighbors >= 4)){
                        nextState[outer][inner] = false;
                    }

                    if (currentState[outer][inner] == true && (amountOfNeighbors == 2 || amountOfNeighbors == 3)){
                        nextState[outer][inner] = true;
                    }
                }
            }
            copyNextState();
            clearNextState();
        }
    }

    public void fillCurrentState() {
        for (int i = 0; i < life_grid.length; i++) {
            for (int j = 0; j < life_grid[0].length; j++) {
                currentState[i][j] = false;
            }
        }

        for (int r = 0; r < life_grid.length; r++) {
            for (int c = 0; c < life_grid[0].length; c++) {
                if (!life_grid[r][c].getBackground().equals(Color.WHITE)) {
                    currentState[r][c] = true;
                } else if (life_grid[r][c].getBackground().equals(Color.WHITE)) {
                    currentState[r][c] = false;
                }
            }
        }
    }

    public void nextGeneration(){
        runLife(1);
    }

    public int countNeighbors(int xPos, int yPos){
        int neighborCount = 0;
        int[][] possibleNeighborPositions = {{1, 1}, {1, 0}, {1, -1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}};

        for (int[] possibleLocation : possibleNeighborPositions){
            int newXPos = xPos + possibleLocation[0];
            int newYPos = yPos + possibleLocation[1];

            boolean pointInBounds = checkBoundary(newXPos, newYPos);
            boolean isAlive = false;

            if (pointInBounds == true){
                isAlive = checkIfLive(newXPos, newYPos);
            }

            if (pointInBounds == true && isAlive == true){
                neighborCount++;
            }
        }

        return neighborCount;
    }

    public boolean checkBoundary(int xFinal, int yFinal){
        if ((xFinal >= 0 && xFinal < currentState.length) && (yFinal >= 0 && yFinal < currentState.length)){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfLive(int xFinal, int yFinal){
        if (currentState[xFinal][yFinal] == true){
            return true;
        } else {
            return false;
        }
    }

    public void copyNextState(){
        for (int outer = 0; outer < nextState.length; outer++){
            for (int inner = 0; inner < nextState[0].length; inner++){
                currentState[outer][inner] = nextState[outer][inner];
            }
        }
    }

    public void clearNextState(){
        for (int outer = 0; outer < nextState.length; outer++){
            for (int inner = 0; inner < nextState[0].length; inner++){
                nextState[outer][inner] = false;
            }
        }
    }

    public static void main(String[] args) {
        LifeGUI gui = new LifeGUI();
        gui.createGUI();
    }
}
