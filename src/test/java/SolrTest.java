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
        //1 �õ�Solr ��������ַ
        String url = "http://localhost:8983/solr/xuchao";
        //2 �����ͻ�������
        HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        //3 ���ӻ��޸�
        City city = new City();
        city.setCityID(100);
        city.setCityName("�����ߺ�");
        city.setCityPinYin("anhuiwuhu");

        //4 ���ö�����ӵ�Solr��������
        UpdateResponse updateResponse = client.addBean(city);
        System.out.println("solr ִ�гɹ�");
        ///5 �ύ����
        client.commit();
        //6 �ͻ��˹ر�
        client.close();
    }

    @Test
    public void Delete() throws Exception {
        //1 �õ�Solr ��������ַ
        String url = "http://localhost:8983/solr/xuchao";
        //2 �����ͻ�������
        HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        client.deleteById("100");
        System.out.println("ɾ���ɹ�");
        client.commit();
        client.close();
    }

    @Test
    public void Search(String keyword, String catalogName, String priceStr, int sort, Integer pageNum) throws Exception {
        //1 �õ�Solr ��������ַ
        String url = "http://localhost:8983/solr/xuchao";
        //2 �����ͻ�������
        HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        //���� q
        SolrQuery query = new SolrQuery();
        if (StringUtils.isEmpty(keyword)) {
            query.set("q", "*");
        } else {
            query.set("q", keyword);
        }
        //����fq
        //���ɸѡ
        if (!StringUtils.isEmpty(catalogName)) {
            query.addFilterQuery("prod_catalog_name:" + catalogName);
        }
        //�۸�ɸѡ
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

        //����sort �۸����� 1Ϊ���� 2Ϊ����
        if (1 == sort) {
            query.addSort("prod_price", SolrQuery.ORDER.asc);
        } else if (2 == sort) {
            query.addSort("prod_price:", SolrQuery.ORDER.desc);
        }

        //���÷�ҳ����
        query.setStart(0);
        query.setRows(10);

        query.set("df", "prod_pname");

        //���ø���
        query.setHighlight(true);
        query.addHighlightField("prod_pname");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        QueryResponse response = client.query(query);
        System.out.println("ɾ���ɹ�");
        client.commit();
        client.close();
    }
}
