package org.example;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class Main {
    private final static String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(URL))) {
            Gson gson = new Gson();
            List<CatsFact> facts = gson.fromJson(EntityUtils.toString(response.getEntity()),
                    new TypeToken<List<CatsFact>>() {
                    });

            facts.stream()
                    .filter(fact -> fact.getUpvotes() > 0)
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
