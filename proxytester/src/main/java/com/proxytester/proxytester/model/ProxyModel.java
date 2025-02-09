package com.proxytester.proxytester.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proxy")
public class ProxyModel {
    @Id

    @Column(columnDefinition = "text")
    public String ip;

    @Column(columnDefinition = "text")
    public String port;

    @Column(columnDefinition = "text")
    public String username;

    @Column(columnDefinition = "text")
    public String password;

    @Column(columnDefinition = "text")
    public String url;

    public void setProxy(String ip, String port, String username, String password, String url) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.url = url;
    }

}
