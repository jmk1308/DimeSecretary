package com.example.dimeguard;

import android.content.Context;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttHandler {

    private MqttClient client;
    Context contexto;

    public void connect(String brokerUrl, String clientId, Context contexto) {
        this.contexto = contexto;
        try {
            // persistencia de datos
            MemoryPersistence persistence = new MemoryPersistence();
            Toast.makeText(contexto, "crea cliente", Toast.LENGTH_SHORT).show();
            client = new MqttClient(brokerUrl, clientId, persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            String USERNAME = "ndroidteststiqq";
            String PASSWORD = "W0U2XNxCKinXaOBv";
            connectOptions.setUserName(USERNAME);
            connectOptions.setPassword(PASSWORD.toCharArray());
            client.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean reconnect, String serverURI) {
                    Toast.makeText(contexto, "conectado", Toast.LENGTH_SHORT).show();
                    // Realiza las operaciones de publicación y suscripción después de la conexión exitosa
                    subscribeToTopic("Test");
                    publishMessage("Test", "mensaje de prueba");
                }

                @Override
                public void connectionLost(Throwable cause) {
                    // Manejar pérdida de conexión
                    Toast.makeText(contexto, "Error al conectar", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // Este método se llama cuando se recibe un mensaje en el tema suscrito
                    String messageText = new String(message.getPayload());
                    // Muestra el mensaje con un Toast
                    Toast.makeText(contexto, messageText, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Este método se llama cuando se ha entregado un mensaje (por ejemplo, después de publicar un mensaje)
                }
            });
            Toast.makeText(contexto, "antes de conectar", Toast.LENGTH_SHORT).show();
            connectOptions.setCleanSession(true);
            
            // Conectar al broker
            client.connect(connectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    private void publishMessage(String topic, String message){
        Toast.makeText(contexto, "Publishing message: " + message, Toast.LENGTH_SHORT).show();
        publish(topic,message);
    }
    private void subscribeToTopic(String topic){
        Toast.makeText(contexto, "Subscribing to topic "+ topic, Toast.LENGTH_SHORT).show();
        subscribe(topic);
    }
    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            Toast.makeText(contexto,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}