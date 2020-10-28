package com.java.activiti.ureport;
/**
 * @ProjectName: OMMS
 * @Package: com.java.activiti.ureport
 * @ClassName: ReportBuildinDatasource
 * @Author: xuchao
 * @Description: ureport�Զ�������Դ
 * @Date: 2019/12/29 22:16
 * @Version: 1.0
 */

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description
 * @updateRemark
 * @author xuchao
 * @updateUser
 * @createDate 2019/12/29 22:16
 * @updateDate 2019/12/29 22:16     
 * @version 1.0
 **/
@Component
public class UReportBuildinDatasource implements BuildinDatasource {

    private Logger log = LoggerFactory.getLogger(UReportBuildinDatasource.class);

    private static final String NAME = "��������Դ";

    @Resource
    private DataSource dataSource;

    /**
     * ����
     * @author xuchao
     * @description ��������Դ����
     * @date 2019/12/29 22:22
     * @Version     1.0
     */
    @Override
    public String name() {
        return NAME;
    }

    /**
     * ����
     * @author xuchao
     * @description ���ص�ǰ��������Դ��һ������
     * @date 2019/12/29 22:23
     * @Version     1.0
     */
    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error(NAME + ",��ȡ����ʧ�ܣ�");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
