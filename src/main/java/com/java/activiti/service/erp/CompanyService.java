package com.java.activiti.service.erp;

import com.java.activiti.model.erp.Company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface CompanyService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Company> companyPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int companyCount(Map<String, Object> map);

    /**
     *
     * @Title: findCompanyName
     * @Description: like��ѯ������
     * @return
     */
    List<Company> findCompanyName(String q);

    /**
     *
     * @Title: findCompanyName
     * @Description: like��ѯ������
     * @return
     */
    List<Company> findCompanyName();

    /**
     * �޸�����
     * @param company
     * @return
     */
    int updateCompany(Company company);

    /**
     * ��������
     * @param company
     * @return
     */
    int addCompany(Company company);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteCompanyById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param company
     * @return
     */
    List<Company> findCompany(Company company);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: ��
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: goodsImport
     * @Description: ����excel
     * @author: ��
     * @param is
     * @throws IOException
     */
    void companyImport(InputStream is) throws IOException;

    Company selectCompanyById(int COMPANY_ID);

    Company findCompanyByName(String COMPANY_NAME);
}
