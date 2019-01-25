package a;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class solrCloudTest {
    CloudSolrServer solrServer ;
    @Before
    public void init() {
        //连接solr服务器
        solrServer = new CloudSolrServer("192.168.10.130:2181,192.168.10.128:2181,192.168.10.131:2181");
        solrServer.setDefaultCollection("myCollection1");
    }

    @Test
    public void solrTest1() throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","2");
        doc.addField("title","2号solrJ");
        solrServer.add(doc);
    }


    @After
    public void after() throws IOException, SolrServerException {
        solrServer.commit();

    }
}
