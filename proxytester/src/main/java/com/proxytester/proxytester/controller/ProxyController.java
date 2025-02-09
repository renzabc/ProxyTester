package com.proxytester.proxytester.controller;

import java.io.BufferedReader;
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

    @RequestMapping(value = "/proxy", method = RequestMethod.POST)
    public ResponseEntity<String> testProxy(@RequestBody ProxyModel proxy) {
        String proxyHost = proxy.ip;
        int proxyPort = Integer.parseInt(proxy.port);
        String username = proxy.username;
        String password = proxy.password;
        String targetUrl = proxy.url;

        // Set up proxy
        HttpHost prxy = new HttpHost(proxyHost, proxyPort, "http");

        // Set up credentials for the proxy
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
            // Create HTTP GET request
            HttpGet httpGet = new HttpGet(targetUrl);
            HttpResponse response = httpClient.execute(httpGet);

            // Read and print the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            
            // Close the HTTP client
            httpClient.close();

            // Check if the response contains the <h1>ERROR</h1> tag
            if (responseContent.toString().contains("<h1>ERROR</h1>")) {
                System.out.println("Error page detected: <h1>ERROR</h1> found in the response.");
                return ResponseEntity.ok("Bad");
            } else {
                System.out.println("No error found in the response.");
                return ResponseEntity.ok("Good");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.ok("Error");
        }

    }

}
