import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.junit.Test;

/**
 * @Auther: DELL
 * @Date: 2018/12/20 13:42
 * @Description:
 */
public class SolrCloudTest {

    @Test
    public void testadd(){
        CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.160.128:2181");
    }
}
