package org.jiucheng.orm.support;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.util.ObjectUtil;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceFacotry implements IDataSourceFactory {

    private DataSource dataSource;

    /**
     * jdbc.driverClassName=com.mysql.jdbc.Driver
     * jdbc.url=jdbc:mysql://127.0.0.1:3306/DBName jdbc.username=root
     * jdbc.password=root jdbc.filters=stat jdbc.initialSize=2
     * jdbc.maxActive=300 jdbc.minIdle=5 jdbc.maxWait=60000
     * jdbc.timeBetweenEvictionRunsMillis=60000
     * jdbc.minEvictableIdleTimeMillis=300000 jdbc.validationQuery=SELECT 1
     * jdbc.testWhileIdle=true jdbc.testOnBorrow=false jdbc.testOnReturn=false
     * jdbc.poolPreparedStatements=false
     * jdbc.maxPoolPreparedStatementPerConnectionSize=200
     * jdbc.removeAbandoned=false
     * jdbc.removeAbandonedTimeoutMillis=180000
     * 
     */
    public DruidDataSourceFacotry() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(DefaultPropertiesUtil
                .getString("jdbc.driverClassName"));
        druidDataSource.setUrl(DefaultPropertiesUtil.getString("jdbc.url"));
        druidDataSource.setUsername(DefaultPropertiesUtil
                .getString("jdbc.username"));
        druidDataSource.setPassword(DefaultPropertiesUtil
                .getString("jdbc.password"));
        druidDataSource.setFilters(DefaultPropertiesUtil
                .getString("jdbc.filters"));// for mysql
        druidDataSource.setInitialSize(Integer.parseInt(DefaultPropertiesUtil
                .getString("jdbc.initialSize")));
        druidDataSource.setMinIdle(Integer.parseInt(DefaultPropertiesUtil
                .getString("jdbc.minIdle", "1")));
        druidDataSource.setMaxActive(Integer.parseInt(DefaultPropertiesUtil
                .getString("jdbc.maxActive", "30")));// 启用监控统计功能
        druidDataSource.setMaxWait(Integer.parseInt(DefaultPropertiesUtil
                .getString("jdbc.maxWait", "60000")));
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(ObjectUtil
                .toLong(DefaultPropertiesUtil.getString(
                        "jdbc.timeBetweenEvictionRunsMillis", "60000")));
        //配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(ObjectUtil
                .toLong(DefaultPropertiesUtil.getString(
                        "jdbc.minEvictableIdleTimeMillis", "300000")));
        druidDataSource.setValidationQuery(DefaultPropertiesUtil
                .getString("jdbc.validationQuery"));
        druidDataSource.setTestWhileIdle(ObjectUtil
                .toBoolean(DefaultPropertiesUtil.getString(
                        "jdbc.testWhileIdle", "true")));
        druidDataSource.setTestOnBorrow(ObjectUtil
                .toBoolean(DefaultPropertiesUtil.getString("jdbc.testOnBorrow",
                        "false")));
        druidDataSource.setTestOnReturn(ObjectUtil
                .toBoolean(DefaultPropertiesUtil.getString("jdbc.testOnReturn",
                        "false")));
        druidDataSource.setPoolPreparedStatements(ObjectUtil
                .toBoolean(DefaultPropertiesUtil.getString(
                        "jdbc.poolPreparedStatements", "false")));
        druidDataSource
                .setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(DefaultPropertiesUtil
                        .getString(
                                "jdbc.maxPoolPreparedStatementPerConnectionSize",
                                "200")));
        druidDataSource.setRemoveAbandoned(ObjectUtil
                .toBoolean(DefaultPropertiesUtil.getString(
                        "jdbc.removeAbandoned", "false")));
        //关闭长时间不使用的连接超时时间，单位是毫秒
        druidDataSource.setRemoveAbandonedTimeoutMillis(ObjectUtil
                .toLong(DefaultPropertiesUtil.getString(
                        "jdbc.removeAbandonedTimeoutMillis", "1800000")));
        dataSource = druidDataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
