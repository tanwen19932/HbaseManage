/*    */
package es.client;
/*    */ 
/*    */

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ public class ESClient
/*    */ {
    /*    */
    public static Client getClient()
/*    */ {

/* 20 */
        return getClient("", "", 9300);
/*    */
    }

    /*    */
    public static Client getClient(String clusterName, String ip, int port)
/*    */ {
/* 16 */
        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
/* 18 */
        Client client = new TransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(ip, port)
        );
/*    */ 
/* 20 */
        return client;
/*    */
    }

    /*    */
    public static Client getNode()
/*    */ {
/* 27 */
        Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("", 9300));
/* 28 */
        return client;
/*    */
    }
/*    */ 
/*    */
}

/* Location:           D:\PROJECT\J2eeProject\dataProcessor_weibo_20160701\lib\elasticsearch\ir_zhongyi-6.2.0.jar
 * Qualified Name:     edu.buaa.nlp.es.client.ESClient
 * JD-Core Version:    0.6.2
 */