package ru.proektbg.exchange;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exchange extends JFrame{
    private JPanel panel1;
    private JTextPane test;
    private JTextPane diffHeader;
    private JTextPane dt;
    private JTextPane value;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Exchange(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void init(){
        this.add(panel1);
        this.setAlwaysOnTop(true);
        this.setSize(400, 200);
        this.pack();
        this.setTitle("Exchange");
    }

    public void updateDt(){
        dt.setText(LocalDateTime.now().format(formatter));
    }

    public void updateValue(String value){
        this.value.setText(value);
    }
}
