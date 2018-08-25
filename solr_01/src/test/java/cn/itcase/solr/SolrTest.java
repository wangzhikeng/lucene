package cn.itcase.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

public class SolrTest {

    private HttpSolrServer httpSolrServer;

    @Before
    public void test(){
        //solr服务器地址
        String baseURL="http://127.0.0.1:8080/solr";
        httpSolrServer=new HttpSolrServer(baseURL);
    }

    //新增或更新solr中数据
    @Test
    public void testAddOrUpdate()throws  Exception{
        //1、创建文档对象
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id",123);
        solrInputDocument.addField("name","你好!solr and lucene");
        //2、添加文档
httpSolrServer.add(solrInputDocument);
        //3、提交
        httpSolrServer.commit();
    }

    //删除索引
    @Test
    public void testDeleteById()throws Exception{
        //1.删除
        httpSolrServer.deleteById("123");
        //2.提交
        httpSolrServer.commit();
    }

    //根据条件删除
    @Test
    public void testDeleteByWhere() throws Exception{
        //1.条件删除
        httpSolrServer.deleteByQuery("name:solr");
        //2.提交
        httpSolrServer.commit();
    }

    //查询索引
    @Test
    public void testQuery() throws Exception{
        //1.创建solrQuery对象
        SolrQuery solrQuery = new SolrQuery("*:*");
        //2.使用httpSolrServer查询
        QueryResponse query = httpSolrServer.query(solrQuery);
        //3.查询并获取查询结果
        long numFound = query.getResults().getNumFound();
        System.out.println("本次查询数据总数为:"+numFound);
        //4.处理查询结果
       SolrDocumentList solrDocuments= query.getResults();
        for (SolrDocument solrDocument : solrDocuments) {
            System.out.println("id为:"+solrDocument.get("id"));
            System.out.println("name为:"+solrDocument.get("name"));
        }
    }

}
