package com.inventaire.Gestioninventaire.service;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RSocketClientService {

    @Autowired
    private RSocketRequester.Builder requesterBuilder;

    public void sendGreeting(String name) {
        RSocketRequester requester = requesterBuilder.connectTcp("localhost", 7000).block();
        requester.route("greeting")
                .data(name)
                .retrieveMono(String.class)
                .doOnTerminate(() -> System.out.println("Message envoyé!"))
                .subscribe(response -> System.out.println("Réponse du serveur: " + response));
    }
}
