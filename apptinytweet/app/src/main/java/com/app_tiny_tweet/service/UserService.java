package com.app_tiny_tweet.service;

import com.app_tiny_tweet.model.User;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;


import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class UserService {

    private OkHttpClient client;

    public UserService() {
        client = new OkHttpClient();
    }

    public boolean login(User user) {
        final boolean[] result = {false};

        Thread saveThread = new Thread(() -> {
            try {
                client = new OkHttpClient();
                OkHttpClient client = new OkHttpClient();
                String json = "{"
                        + "\"username\":\"" + user.getUsername() + "\","
                        + "\"password\":\"" + user.getPassword() + "\""
                        + "}";

                RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url("http://192.168.68.110:8080/auth/login")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

                // Verificando se a resposta foi bem-sucedida
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                // Processando a resposta
                Gson gson = new Gson();
                String token = (response.body().string());

                // Fechando o corpo da resposta
                response.close();

                result[0] = true;  // Definindo o resultado como verdadeiro em caso de sucesso
            } catch (IOException e) {
                e.printStackTrace();
                result[0] = false; // Definindo o resultado como falso em caso de exceção
            }
        });

        // Iniciando a thread
        saveThread.start();

        try {
            // Esperando a thread terminar
            saveThread.join();
        } catch (InterruptedException e) {
            System.out.println("A thread foi interrompida.");
            Thread.currentThread().interrupt();
            return false;
        }

        return result[0];
    }

    public void save(User user) {

        Thread t = new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                String json = "{"
                        + "\"username\":\"" + user.getUsername() + "\","
                        + "\"password\":\"" + user.getPassword() + "\""
                        + "}";

                RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url("http://192.168.68.110:8080/user/save")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(response.body().string(), Map.class);
                System.out.println(map);
                response.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("A thread foi interrompida.");
            Thread.currentThread().interrupt();
        }
    }
}
