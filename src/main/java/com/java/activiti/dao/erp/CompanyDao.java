package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.Company;
import com.java.activiti.model.erp.Supplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CompanyDao {

    Company findById(int COMPANY_ID);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int companyCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Company> companyPage(Map<String, Object> map);

    /**
     *
     * @Title: selectCompanyName
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param COMPANY_NAME
     * @return
     */
    List<Company> selectCompanyName(@Param("COMPANY_NAME") String COMPANY_NAME);

    List<Company> selectALL();

    int addCompany(Company company);

    int updateCompany(Company company);

    int deleteCompanyById(List<String> id);

    List<Company> findCompany(Company company);

    /**
     *
     * @Title: selectCompanyByCompanyID
     * @Description: ֤��ID��ѯ��Ӧ�������
     * @author: ��
     * @param COMPANY_ID ��Ӧ��ID
     * @return
     */
    List<Company> selectCompanyByCompanyID(int COMPANY_ID);

    Company findCompanyByName(String COMPANY_NAME);

}
