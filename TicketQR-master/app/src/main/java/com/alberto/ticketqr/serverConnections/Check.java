package com.alberto.ticketqr.serverConnections;

import java.util.Random;

public class Check {
    public int checkTicket(String code){
        return new Random().nextInt(3); //i 3 stati che ci sono sul database
    }
}
