package com.java.activiti.ureport;
/**
 * @ProjectName: OMMS
 * @Package: com.java.activiti.ureport
 * @ClassName: ReportBuildinDatasource
 * @Author: xuchao
 * @Description: ureport自定义数据源
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

    private static final String NAME = "报表数据源";

    @Resource
    private DataSource dataSource;

    /**
     * 参数
     * @author xuchao
     * @description 返回数据源名称
     * @date 2019/12/29 22:22
     * @Version     1.0
     */
    @Override
    public String name() {
        return NAME;
    }

    /**
     * 参数
     * @author xuchao
     * @description 返回当前采用数据源的一个连接
     * @date 2019/12/29 22:23
     * @Version     1.0
     */
    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error(NAME + ",获取连接失败！");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
