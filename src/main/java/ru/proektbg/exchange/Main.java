package ru.proektbg.exchange;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        Exchange exchange = new Exchange();
        exchange.init();
        exchange.setVisible(true);
        ExecutorService service = Executors.newFixedThreadPool(1);
        ExchangeService restClient = new ExchangeService(exchange);
        while (true){
            Thread.sleep(15000);
            service.execute(restClient);
        }
    }
}
