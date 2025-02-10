package com.proxytester.proxytester.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proxytester.proxytester.model.ProxyModel;

@RestController
@RequestMapping("/test")
public class ProxyController {
    // private final TesterService testerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Hello() {
        return "Hello World";
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/proxy", method = RequestMethod.POST)
    public ResponseEntity<String> testProxy(@RequestBody ProxyModel proxy) {
        String proxyHost = proxy.ip;
        int proxyPort = Integer.parseInt(proxy.port);
        String username = proxy.username;
        String password = proxy.password;
        String targetUrl = proxy.url;

        // Check payload for valid input
        if (proxyHost.isEmpty() || proxyPort <= 0 || proxyPort > 65535
                || username.isEmpty() || password.isEmpty()) {
            return ResponseEntity.ok("Invalid payload: Missing or incorrect parameters");
        }
        if (targetUrl.isEmpty()) {
            targetUrl = "https://google.com";
        }

        // Set up proxy and proxy authentication
        HttpHost prxy = new HttpHost(proxyHost, proxyPort, "http");
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(proxyHost, proxyPort),
                new UsernamePasswordCredentials(username, password)
        );

        // Build HTTP client with proxy and authentication
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider)
                .setProxy(prxy)
                .build();

        try {
            // create GET request
            HttpGet httpGet = new HttpGet(targetUrl);
            HttpResponse response = httpClient.execute(httpGet);

            // use try-with-resources for auto closing of BufferedReader
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                StringBuilder responseContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }

                // Check if the response contains the <h1>ERROR</h1>
                if (responseContent.toString().contains("<h1>ERROR</h1>")) {
                    return ResponseEntity.status(400).body("Bad");
                } else {
                    return ResponseEntity.ok("Good");
                }
            }
        } catch (Exception e) {
            System.err.println("Error during proxy test: " + e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        } finally {
            // close HTTP client
            try {
                httpClient.close();
            } catch (IOException e) {
                System.err.println("Error closing HTTP client: " + e.getMessage());
            }
        }

    }

}
