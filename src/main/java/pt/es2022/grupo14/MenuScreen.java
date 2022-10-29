package pt.es2022.grupo14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen
{
    JFrame frm;

    public void showMenu() {
        frm = Main.getFrm();
        frm.getContentPane().removeAll();
        frm.repaint();

        JPanel panel = new JPanel();

        JTextField webcalTextField = new JTextField();
        webcalTextField.setPreferredSize(new Dimension(200, 24));

        CalendarReader reader = new CalendarReader();

        JButton webcalConfirm = new JButton("Enter");
        webcalConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (webcalTextField.getText().isBlank()) return;

                reader.read(webcalTextField.getText());
            }
        });
        panel.add(webcalTextField);
        panel.add(webcalConfirm);

        frm.add(panel);

        frm.revalidate();
        frm.repaint();
    }
}
