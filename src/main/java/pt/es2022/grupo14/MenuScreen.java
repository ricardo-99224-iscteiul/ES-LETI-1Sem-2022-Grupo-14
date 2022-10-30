package pt.es2022.grupo14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static pt.es2022.grupo14.Utils.alphaGray;

public class MenuScreen
{
    JFrame frm;

    public void showMenu() {
        frm = Main.getFrm();
        frm.getContentPane().removeAll();
        frm.repaint();

        JPanel panel = new JPanel();

        if (Main.darkMode)
        {
            panel.setBackground(Color.DARK_GRAY);
        }
        else
        {
            panel.setBackground(alphaGray);
        }

        JTextField webcalTextField = new JTextField();
        webcalTextField.setPreferredSize(new Dimension(200, 24));

        CalendarReader reader = new CalendarReader();

        JButton webcalConfirm = new JButton("Enter");
        webcalConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (webcalTextField.getText().isBlank()) return;

                try
                {
                    reader.read(webcalTextField.getText());
                }
                catch (IllegalArgumentException exception)
                {
                    exception.printStackTrace();
                }

                Main.changeScreen(Screen.CALENDAR);
            }
        });
        panel.add(webcalTextField);
        panel.add(webcalConfirm);

        frm.add(panel);

        frm.revalidate();
        frm.repaint();
    }
}
