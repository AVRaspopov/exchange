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
import java.util.concurrent.Callable;

public class RestClient implements Callable<Double> {
    private JSONObject binanceBody;
    private String bestChangeBody;

    public RestClient() {
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
        binanceBody.put("payTypes", new JSONArray());
        binanceBody.put("publisherType", JSONObject.NULL);
        binanceBody.put("rows", 10);
        binanceBody.put("tradeType", "BUY");
        binanceBody.put("transAmount", "100000");
    }

    @Override
    public Double call() throws Exception {
        return getValueFromBinance() - getValueFromBestChange();
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
        Elements elements = doc.getElementsByClass("fs");
        Element element = elements.get(0);
        return Double.parseDouble(element.ownText());
    }

}
