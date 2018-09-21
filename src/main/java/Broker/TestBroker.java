package Broker;

import Client.TestClient;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import java.util.ArrayList;

public class TestBroker{

    private String brokerURL;
    private ArrayList<TestClient> clients;
    private ArrayList<MqttTopic> knownTopics;


}
