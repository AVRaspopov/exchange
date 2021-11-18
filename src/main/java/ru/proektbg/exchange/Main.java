package ru.proektbg.exchange;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static Properties properties = new Properties();

    static {
        try {
            String propertiesPath = "./exchange.properties";
            InputStreamReader reader = new InputStreamReader(new FileInputStream(propertiesPath));
            properties.load(reader);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Exchange exchange = new Exchange();
        exchange.init();
        exchange.setVisible(true);
        ExecutorService service = Executors.newFixedThreadPool(1);
        ExchangeService restClient = new ExchangeService(exchange);
        while (true){
            Thread.sleep(Long.parseLong(properties.getProperty("sleep_time"))*1000);
            service.execute(restClient);
        }
    }
}
