package ru.proektbg.exchange;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exchange extends JFrame{
    private JPanel panel1;
    private JTextPane test;
    private JTextPane diffHeader;
    private JTextPane dt;
    private JTextPane value;
    private JTextPane profitTextPane;
    private JTextPane profitValue;
    private JTextPane top3TextPane;
    private JTextPane exchanger1;
    private JTextPane exchanger2;
    private JTextPane exchanger3;
    public static String ex1;
    public static String ex2;
    public static String ex3;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Exchange(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void init(){
        this.add(panel1);
        this.setAlwaysOnTop(true);
        this.setSize(350, 200);
        this.setTitle("Exchange");
        ex1 = Main.properties.getProperty("exchanger1");
        ex2 = Main.properties.getProperty("exchanger2");
        ex3 = Main.properties.getProperty("exchanger3");
        exchanger1.setText(ex1);
        exchanger2.setText(ex2);
        exchanger3.setText(ex3);
    }

    public void updateDt(){
        dt.setText(LocalDateTime.now().format(formatter));
    }

    public void updateValue(String value){
        this.value.setText(value);
    }

    public void updateProfitValue(String value){
        this.profitValue.setText(value);
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public void setDtBackgroundColor(Color color){
        dt.setBackground(color);
    }

    public JTextPane getWwPay() {
        return exchanger1;
    }

    public JTextPane getExchanger2() {
        return exchanger2;
    }

    public JTextPane getExchanger3() {
        return exchanger3;
    }

    public void setExchangersWhiteColor(){
        exchanger1.setBackground(Color.WHITE);
        exchanger2.setBackground(Color.WHITE);
        exchanger3.setBackground(Color.WHITE);
    }

}
