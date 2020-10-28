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
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int companyCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Company> companyPage(Map<String, Object> map);

    /**
     *
     * @Title: selectCompanyName
     * @Description: 查询物料，自动补全
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
     * @Description: 证据ID查询对应存货编码
     * @author: 许超
     * @param COMPANY_ID 供应商ID
     * @return
     */
    List<Company> selectCompanyByCompanyID(int COMPANY_ID);

    Company findCompanyByName(String COMPANY_NAME);

}
