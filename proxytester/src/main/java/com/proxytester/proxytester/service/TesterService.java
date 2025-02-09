package com.proxytester.proxytester.service;

import org.springframework.stereotype.Service;

import com.proxytester.proxytester.repository.ProxyRepository;

@Service
public class TesterService {
    private final ProxyRepository proxyRepository;

    public TesterService(ProxyRepository proxyRepository) {
        this.proxyRepository = proxyRepository;
    }

    // public Map<String, String> getBrowser(String id){
    //     if (proxyRepository.existsById(id)) {
    //         Map<String, String> response = new HashMap<>();
    //         ProxyModel proxy = proxyRepository.findById(id).orElseThrow();
    //         response.put("ip", proxy.getId());
    //         response.put("port", proxy.getCookie());
    //         response.put("username", proxy.getDomain());
    //         response.put("password", proxy.getDomain());
    //         return response;
    //     }
    //     return null;
    // }

    
    // // Add new Browser Auths/Cookies
    // public Browser addBrowser(String id, Browser browser){
    //     if (testerRepository.existsById(id)) {
    //         Browser oldBrowser = testerRepository.findById(id).orElseThrow();
    //         oldBrowser.setAuth(browser.getId(), browser.getCookie(), browser.getDomain());
    //         return testerRepository.save(browser);
    //     }
        
    //     return testerRepository.save(browser);
    // }

}
