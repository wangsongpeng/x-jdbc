package com.jd.jdbc.route;

import com.jd.jdbc.ds.DataSourceWrapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * 数据源同机房调用路由算法基类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class LocalRoomRoute extends AbstractRoute {

    /**
     * 根据路由算法获取真正的数据源
     * @param dataSourceWrappers 可用的数据源集合
     * @return 数据源
     */
    @Override
    public String doRoute(List<DataSourceWrapper> dataSourceWrappers) {
        // TODO: 2017/9/27 暂未实现 
        return null;
    }




}

