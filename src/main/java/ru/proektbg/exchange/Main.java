package ru.proektbg.exchange;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws Exception {
        Exchange exchange = new Exchange();
        exchange.init();
        exchange.setVisible(true);
        ExecutorService service = Executors.newFixedThreadPool(1);
        RestClient restClient = new RestClient();
        while (true){
            Thread.sleep(5000);
            Future<Double> result = service.submit(restClient);
            exchange.setValue(String.format("%1$,.2f", result.get()));
            exchange.setDt();
        }
    }
}
