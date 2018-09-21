import Client.TestClient;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Initiator {

    public static final String BROKER = "BROKER_URL";
    public static ArrayList<String> topics;

    public static void main(String[] args) {

        TestClient client1 = new TestClient();
        TestClient client2 = new TestClient();
        TestClient client3 = new TestClient();
        TestClient client4 = new TestClient();
        TestClient client5 = new TestClient();

       try{

           topics = new ArrayList<>();
           topics.add("topic/orange");
           topics.add("topic/tech");
           topics.add("topic/sports");
           topics.add("topic/travel");
           topics.add("topic/apache");

           client1 = new TestClient(Initiator.BROKER, "Client-1", "THIS IS CLIENT-1");
           client2 = new TestClient(Initiator.BROKER, "Client-2", "THIS IS CLIENT-2");
           client3 = new TestClient(Initiator.BROKER, "Client-3", "THIS IS CLIENT-3");
           client4 = new TestClient(Initiator.BROKER, "Client-4", "THIS IS CLIENT-4");
           client5 = new TestClient(Initiator.BROKER, "Client-5", "THIS IS CLIENT-5");
           System.out.println();

           /************************************* Unsubscribe ********************************/
           for(String str: topics){

               /*client1.unsubscribe(str);
               client2.unsubscribe(str);
               client3.unsubscribe(str);
               client4.unsubscribe(str);
               client5.unsubscribe(str);*/
           }

           /************************************* Subscribe ********************************/
           for(String str: topics){

               client1.subscribe(str, ThreadLocalRandom.current().nextInt(0, 1));
               client2.subscribe(str, ThreadLocalRandom.current().nextInt(0, 1));
               client3.subscribe(str, ThreadLocalRandom.current().nextInt(0, 1));
               client4.subscribe(str, ThreadLocalRandom.current().nextInt(0, 1));
               client5.subscribe(str, ThreadLocalRandom.current().nextInt(0, 1));
           }

           /************************************* Publish ********************************/
           client3.publish("topic/sports", client3.getMessage().getPayload(), 0, false);
           client5.publish("topic/tech", client5.getMessage().getPayload(), 0, false);
           client1.publish("topic/orange", client1.getMessage().getPayload(), 0, false);
           client2.publish("topic/travel", client2.getMessage().getPayload(), 0, false);
           client2.publish("topic/apache", client2.getMessage().getPayload(), 0, false);
           client4.publish("topic/orange", client4.getMessage().getPayload(), 0, false);
           client4.publish("topic/apache", client4.getMessage().getPayload(), 0, false);
           client5.publish("topic/sports", client5.getMessage().getPayload(), 0, false);
           client1.publish("topic/tech", client1.getMessage().getPayload(), 0, false);

       }
       catch(MqttPersistenceException exp){

           System.out.println(exp.getMessage());
       }
       catch(MqttException exp){

           System.out.println(exp.getMessage());
       }
       catch(InterruptedException exp){

           System.out.println(exp.getMessage());
       }
       finally {

           /************************************* Detach! ********************************/
           try{

               System.out.println();
               client5.disconnect();
               client2.disconnect();
               client1.disconnect();
               client3.disconnect();
               client4.disconnect();
           }
           catch (MqttException e){

               System.out.println(e.getMessage());
           }
       }
    }
}
