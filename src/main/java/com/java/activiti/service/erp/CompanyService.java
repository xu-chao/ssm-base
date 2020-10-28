package com.java.activiti.service.erp;

import com.java.activiti.model.erp.Company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface CompanyService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Company> companyPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int companyCount(Map<String, Object> map);

    /**
     *
     * @Title: findCompanyName
     * @Description: like查询物料名
     * @return
     */
    List<Company> findCompanyName(String q);

    /**
     *
     * @Title: findCompanyName
     * @Description: like查询物料名
     * @return
     */
    List<Company> findCompanyName();

    /**
     * 修改物料
     * @param company
     * @return
     */
    int updateCompany(Company company);

    /**
     * 新增物料
     * @param company
     * @return
     */
    int addCompany(Company company);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteCompanyById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param company
     * @return
     */
    List<Company> findCompany(Company company);

    /**
     *
     * @Title: export
     * @Description: 导出excel
     * @author: 许超
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: goodsImport
     * @Description: 导入excel
     * @author: 许超
     * @param is
     * @throws IOException
     */
    void companyImport(InputStream is) throws IOException;

    Company selectCompanyById(int COMPANY_ID);

    Company findCompanyByName(String COMPANY_NAME);
}
