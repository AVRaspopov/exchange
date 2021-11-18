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
    private JTextPane wwPay;
    private JTextPane fastExchange;
    private JTextPane eMoney;
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
        return wwPay;
    }

    public JTextPane getFastExchange() {
        return fastExchange;
    }

    public JTextPane geteMoney() {
        return eMoney;
    }

    public void setExchangersWhiteColor(){
        wwPay.setBackground(Color.WHITE);
        fastExchange.setBackground(Color.WHITE);
        eMoney.setBackground(Color.WHITE);
    }

}
