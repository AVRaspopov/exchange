package ru.proektbg.exchange;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;

public class ExchangeService implements Runnable {
    private JSONObject binanceBody;
    private String bestChangeBody;
    private Exchange exchange;

    public ExchangeService(Exchange exchange) {
        this.exchange = exchange;
        initBinanceBody();
        initBestChangeBody();
    }

    public void initBestChangeBody(){
        bestChangeBody = "action=getrates&page=rates&from=63&to=10&city=0&type=&give=&get=&commission=0&sort=from&range=asc&sortm=0&tsid=0";
    }

    public void initBinanceBody(){
        binanceBody = new JSONObject();
        binanceBody.put("asset", "USDT");
        binanceBody.put("fiat", "RUB");
        binanceBody.put("page", 1);
        binanceBody.put("payTypes", new JSONArray().put("Tinkoff"));
        binanceBody.put("publisherType", JSONObject.NULL);
        binanceBody.put("rows", 10);
        binanceBody.put("tradeType", "BUY");
        binanceBody.put("transAmount", "100000");
    }

    @Override
    public void run() {
        Double valueFromBinance = null;
        Double valueFromBestChange = null;
        try {
            valueFromBinance = getValueFromBinance();
            valueFromBestChange = getValueFromBestChange();
        } catch (UnirestException e) {
            return;
        }
        Double difference = valueFromBinance - valueFromBestChange;
        exchange.updateValue(String.format("%1$,.2f", difference));
        Double profit = calcProfit(valueFromBinance, valueFromBestChange);
        exchange.updateProfitValue(String.format("%1$,.2f", profit));
        exchange.updateDt();
        exchange.setDtBackgroundColor(Color.WHITE);
        if (profit >= 1000)
            exchange.setDtBackgroundColor(Color.RED);
        else if (profit >= 750)
            exchange.setDtBackgroundColor(Color.GREEN);
        else if (profit >= 500)
            exchange.setDtBackgroundColor(new Color(153, 255, 153));
        else if (profit > 250)
            exchange.setDtBackgroundColor(Color.ORANGE);
        else if (profit <= 100)
            exchange.setDtBackgroundColor(Color.GRAY);
        else exchange.setDtBackgroundColor(Color.WHITE);
    }

    private Double calcProfit(Double valueFromBinance, Double valueFromBestChange){
        return ((100000/valueFromBestChange*valueFromBinance)-100000)*0.999;
    }

    private Double getValueFromBinance() throws UnirestException {
        HttpResponse<JsonNode> response =
                Unirest.post("https://p2p.binance.com/bapi/c2c/v2/friendly/c2c/adv/search")
                        .header("Content-Type", "application/json")
                        .body(binanceBody).asJson();
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray array = jsonObject.getJSONArray("array");
        String price = array.getJSONObject(0)
                .getJSONArray("data").getJSONObject(0)
                .getJSONObject("adv").getString("price");
        return Double.parseDouble(price);
    }

    private Double getValueFromBestChange() throws UnirestException {
        HttpResponse<String> response =
                Unirest.post("https://www.bestchange.ru/action.php")
                        .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                        .body(bestChangeBody).asString();
        Document doc = Jsoup.parse(response.getBody());
        checkExchanger(doc);
        Elements elements = doc.getElementsByClass("fs");
        Element element = elements.get(0);
        Double result = Double.parseDouble(element.ownText());
        return Math.round(result*100)/100.0;
    }

    private void checkExchanger(Document doc){
        exchange.setExchangersWhiteColor();
        Elements elements = doc.getElementsByClass("ca");
        for (int i = 0; i < 3; i++) {
            Element element = elements.get(i);
            if (element.ownText().equals(Exchange.ex1))
                exchange.getExchanger1().setBackground(Color.GREEN);
            else if (element.ownText().equals(Exchange.ex2))
                exchange.getExchanger2().setBackground(Color.GREEN);
            else if (element.ownText().equals(Exchange.ex3))
                exchange.getExchanger3().setBackground(Color.GREEN);
        }
    }

}
