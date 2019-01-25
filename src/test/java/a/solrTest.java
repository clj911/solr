package a;

import com.itheima.pojo.Items;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.apache.solr.client.solrj.SolrQuery.ORDER ;

public class solrTest {
    HttpSolrServer solrServer ;
    @Before
    public void init() {
        //连接solr服务器
        solrServer = new HttpSolrServer("http://localhost:8080/solr/core2");
    }

    @Test
    public void solrTest1() throws IOException, SolrServerException {
        //创建文档
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id",15l);
        doc.addField("title","8848手机，就是屌");
        doc.addField("price",999900l);
        solrServer.add(doc);
    }

    @Test
    public void solrTest2() throws IOException, SolrServerException {
        //创建实体类对象
        Items item = new Items();
        item.setId(16l);
        item.setTitle("霸王手机，手机中的战斗机");
        item.setPrice(1000000l);
        solrServer.addBean(item);
    }

    @Test
    public void deleteTest() throws IOException, SolrServerException {
        solrServer.deleteById("16");
    }

    @Test
    public void queryTest1() throws SolrServerException {
        //创建查询对象
        SolrQuery query = new SolrQuery("title:8848");
        //执行查询  获取响应
        QueryResponse response = solrServer.query(query);
        //解析响应
        SolrDocumentList lists = response.getResults();
        System.out.println("共有"+lists.getNumFound()+"条数据");
        //遍历
        for (SolrDocument doc : lists) {
            System.out.println("id:"+doc.getFieldValue("id"));
            System.out.println("title:"+doc.getFieldValue("title"));
            System.out.println("price:"+doc.getFieldValue("price"));
        }
    }

    @Test
    public void queryTest2() throws SolrServerException {
        //创建查询对象
        SolrQuery query = new SolrQuery("title:8848 OR title:华为");
        //执行查询  获取响应
        QueryResponse response = solrServer.query(query);
        //解析响应
        SolrDocumentList lists = response.getResults();
        System.out.println("共有"+lists.getNumFound()+"条数据");
        //遍历
        lists.forEach(System.out::println);
    }

    @Test
    public void QueryByBean() throws SolrServerException {
        SolrQuery query = new SolrQuery("title:Apple");
        QueryResponse response = solrServer.query(query);
        List<Items> list = response.getBeans(Items.class);
        list.forEach(System.out::println);
    }

    @Test
    public void queryByPage() throws SolrServerException {
        int num = 2 ;
        int size = 3 ;
        int start = (num-1)*size ;
        SolrQuery query = new SolrQuery("title:华为");
        query.setSort("price", ORDER.desc);
        query.setStart(start);
        query.setRows(3);
        QueryResponse response = solrServer.query(query);
        List<Items> list = response.getBeans(Items.class);
        list.forEach(System.out::println);
    }

    @Test
    public void queryByHighLighlting() throws SolrServerException {
        SolrQuery query = new SolrQuery("title:华为");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        //设置高亮字段
        query.addHighlightField("title");
        QueryResponse response = solrServer.query(query);
        //得到非高亮结果
        List<Items> list = response.getBeans(Items.class);
        //得到高亮结果
        Map<String, Map<String, List<String>>> map = response.getHighlighting();
        //处理高亮
        /*for (Items item : list) {
            item.setTitle(map.get(item.getId()+"").get("title").get(0));
        }*/
        //遍历
        list.forEach(item -> {
            item.setTitle(map.get(item.getId()+"").get("title").get(0));
            System.out.println(item);
        });


    }

    @After
    public void after() throws IOException, SolrServerException {
        solrServer.commit();

    }
}
