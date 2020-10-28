import com.java.activiti.model.City;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.StringUtils;
import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.IOException;

public class SolrTest {
    @Test
    public void addOrUpdate() throws IOException, SolrServerException {
        //1 得到Solr 服务器地址
        String url = "http://localhost:8983/solr/xuchao";
        //2 创建客户端连接
        HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        //3 增加或修改
        City city = new City();
        city.setCityID(100);
        city.setCityName("安徽芜湖");
        city.setCityPinYin("anhuiwuhu");

        //4 将该对象添加到Solr索引库中
        UpdateResponse updateResponse = client.addBean(city);
        System.out.println("solr 执行成功");
        ///5 提交事务
        client.commit();
        //6 客户端关闭
        client.close();
    }

    @Test
    public void Delete() throws Exception {
        //1 得到Solr 服务器地址
        String url = "http://localhost:8983/solr/xuchao";
        //2 创建客户端连接
        HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        client.deleteById("100");
        System.out.println("删除成功");
        client.commit();
        client.close();
    }

    @Test
    public void Search(String keyword, String catalogName, String priceStr, int sort, Integer pageNum) throws Exception {
        //1 得到Solr 服务器地址
        String url = "http://localhost:8983/solr/xuchao";
        //2 创建客户端连接
        HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        //设置 q
        SolrQuery query = new SolrQuery();
        if (StringUtils.isEmpty(keyword)) {
            query.set("q", "*");
        } else {
            query.set("q", keyword);
        }
        //设置fq
        //类别筛选
        if (!StringUtils.isEmpty(catalogName)) {
            query.addFilterQuery("prod_catalog_name:" + catalogName);
        }
        //价格筛选
        if (!StringUtils.isEmpty(priceStr)) {
            String[] arrays = priceStr.split("-");
            if (arrays.length == 1) {
                query.addFilterQuery("prod_price:[" + arrays[0] + " TO * ]");
            } else {
                String prefix = arrays[0];
                if (StringUtils.isEmpty(arrays[0])) {
                    prefix = "*";
                }
                query.addFilterQuery("prod_price:[" + prefix + " TO " + arrays[1] + " ]");
            }
        }

        //设置sort 价格排序 1为升序 2为降序
        if (1 == sort) {
            query.addSort("prod_price", SolrQuery.ORDER.asc);
        } else if (2 == sort) {
            query.addSort("prod_price:", SolrQuery.ORDER.desc);
        }

        //设置分页功能
        query.setStart(0);
        query.setRows(10);

        query.set("df", "prod_pname");

        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("prod_pname");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        QueryResponse response = client.query(query);
        System.out.println("删除成功");
        client.commit();
        client.close();
    }
}
