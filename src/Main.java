import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

class SecondWindow extends JFrame {
    private JLabel label;

    public SecondWindow() {
        setTitle("Second Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

    }




    public void updateLabelColor(Color backgroundColor) {
    label.setForeground(backgroundColor);
    repaint();
}
    public void setLabelText() {
        label.setText("Мітка");
        repaint();
    }
}

class MainWindow extends JFrame {
    class VerticalMenuBar extends JMenuBar {
        private  final  LayoutManager grid = new GridLayout(0,1);
        public VerticalMenuBar() {
            setLayout(grid);
        }
    }

    private JCheckBoxMenuItem maximizeMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu viewMenu;
    private String selectedColor;
    private static MainWindow instance;
    private SecondWindow secondWindow;

    private MainWindow() {
        setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        secondWindow = new SecondWindow();

        createMenuBar();

        setVisible(true);
        secondWindow.setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

    }

    public static synchronized MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }



    private void createMenuBar() {
        JMenuBar menuBar = new VerticalMenuBar();


        JMenu windowMenu = new JMenu("Window");

        windowMenu.setMnemonic(java.awt.event.KeyEvent.VK_W);

        maximizeMenuItem = new JCheckBoxMenuItem("Maximized");
        maximizeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSecondWindow();
            }
        });

        windowMenu.add(maximizeMenuItem);
        menuBar.add(windowMenu);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));


        viewMenu = new JMenu("View");

        createFormColorOptions();

        menuBar.add(viewMenu);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));


        exitMenuItem = new JMenuItem("Exit");

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuBar.add(exitMenuItem);

        setJMenuBar(menuBar);
    }

    private void createFormColorOptions() {
        JMenu formColorMenu = new JMenu("Form.Color");

        String[] colorOptions = {"clBtnFace", "clRed", "clBlue", "clYellow"};

        ButtonGroup colorButtonGroup = new ButtonGroup();
        for (String colorOption : colorOptions) {
            JRadioButtonMenuItem colorMenuItem = new JRadioButtonMenuItem(colorOption);
            colorMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedColor = colorOption;
                    updateSecondWindow();
                }
            });

            colorButtonGroup.add(colorMenuItem);
            formColorMenu.add(colorMenuItem);
        }

        JMenuItem labelMenuItem = new JMenuItem("Jlabel.Caption");
        labelMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondWindow.setLabelText();

            }
        });

        viewMenu.add(formColorMenu);
        viewMenu.add(labelMenuItem);
    }

    private void updateSecondWindow() {
        if (secondWindow == null) {
            secondWindow = new SecondWindow();
        } else {
            secondWindow.updateLabelColor(getBackgroundColor());
            if (maximizeMenuItem.isSelected()) {
                secondWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                secondWindow.setExtendedState(JFrame.NORMAL);
            }
        }

        secondWindow.setVisible(true);
    }




    private Color getBackgroundColor() {
        if (selectedColor != null) {
            switch (selectedColor) {
                case "clBtnFace":
                    return Color.WHITE;
                case "clRed":
                    return Color.RED;
                case "clBlue":
                    return Color.BLUE;
                case "clYellow":
                    return Color.YELLOW;
            }
        }
        return UIManager.getColor("Button.background");
    }



}

 class TwoWindowsProgram {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = MainWindow.getInstance();
            mainWindow.setVisible(true);
        });
    }
}
