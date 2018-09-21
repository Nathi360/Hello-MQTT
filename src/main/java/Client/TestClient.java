package Client;

import org.eclipse.paho.client.mqttv3.*;

public class TestClient implements MqttCallback {

    private MqttClient client;
    private MqttConnectOptions options;
    private MqttMessage message;
    private String clientID;

    public TestClient() {
    }

    /**
     *
     * @param serverURI
     * @param clientId
     * @param msg
     * @throws MqttException
     * @throws InterruptedException
     */
    public TestClient(String serverURI, String clientId, String msg) throws MqttException, InterruptedException {

        client = new MqttClient(serverURI, clientId);
        client.setCallback(this);
        message = new MqttMessage(msg.getBytes());
        clientID = clientId;

        options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

        client.connect(options);

        if(client.isConnected()){
            System.out.println(client.getClientId() + " connected to Broker!");
        }
    }


    /**
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
    }

    /**
     *
     * @param s
     * @param mqttMessage
     * @throws InterruptedException
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws InterruptedException{

        Thread.sleep(3000);
        System.out.println(clientID + " received message: '" + mqttMessage.toString() + "' : TOPIC = " + s.toUpperCase());
    }

    /**
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        try{
            System.out.println("Delivery Report: '" + iMqttDeliveryToken.getMessage().toString() + "' [Sender : " + clientID + "]");
        }
        catch (MqttException exp){

            System.out.println(exp.getMessage());
        }
    }

    /**
     *
     * @param topic
     * @param qos
     * @throws MqttException
     * @throws InterruptedException
     */
    public void subscribe(String topic, int qos) throws MqttException, InterruptedException{

        this.client.subscribe(topic, qos);
        Thread.sleep(2200);
        System.out.println(clientID + " subscribed to " + topic.toUpperCase() + " @ QoS = " + qos);
    }

    /**
     *
     * @param topic
     * @throws MqttException
     */
    public void unsubscribe(String topic) throws MqttException{

        this.client.unsubscribe(topic);
        System.out.println(clientID + " unsubscribed to " + topic.toUpperCase());
    }

    /**
     *
     * @param topic
     * @param payload
     * @param qos
     * @param restrained
     * @throws MqttException
     * @throws InterruptedException
     */
    public void publish(String topic, byte[] payload, int qos, Boolean restrained) throws MqttException, InterruptedException{

        this.client.publish(topic, payload, qos, restrained);
        System.out.println(clientID + " publishing: '" + message.toString() + "' to " + topic.toUpperCase());
        Thread.sleep(4000);

    }

    /**
     *
     * @throws MqttException
     */
    public void disconnect() throws MqttException{

        client.disconnect();
        System.out.println(clientID + " detached from Broker!");
    }

    /**
     *
     * @return
     */
    public MqttMessage getMessage() {
        return message;
    }
}