package es.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESClient {
    public static Client getClient(String clusterName, String ip, int port)
            throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
        return client;
    }
}