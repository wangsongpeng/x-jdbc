package com.wsp.xjdbc.core.statement;


import com.wsp.xjdbc.core.MasterSlaveConnection;
import com.wsp.xjdbc.core.connection.StandardMasterSlaveConnection;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Title :读写Statement执行者实现
 * Description: 实现JDBC Statement核心增删改查方法</br>
 *
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/20
 */
public class StandardMasterSlaveStatement extends AbstractMasterSlaveStatement{
    private static final Logger log = LoggerFactory.getLogger(StandardMasterSlaveStatement.class);

    private int resultSetType;

    private int resultSetConcurrency;

    private int resultSetHoldability;

    public StandardMasterSlaveStatement(MasterSlaveConnection connection) {
        this(connection, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    public StandardMasterSlaveStatement(final MasterSlaveConnection connection, final int resultSetType, final int resultSetConcurrency) {
        this(connection, resultSetType, resultSetConcurrency, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    public StandardMasterSlaveStatement(final MasterSlaveConnection connection, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) {
        this.masterSlaveConnection = connection;
        this.resultSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
        this.resultSetHoldability = resultSetHoldability;
    }

    @Override
    public Statement determineRealStatement(String sql) throws SQLException {
        if(null != targetStatement){
            return targetStatement;
        }
        try {
            targetStatement = masterSlaveConnection.determineRealConnection(sql).createStatement(resultSetType,resultSetConcurrency,resultSetHoldability);
            replayMethodsInvocation(targetStatement);
        }catch (Exception e){
            log.error("determineRealConnection Happen Exception",e);
        }
        return targetStatement;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return determineRealStatement(sql).executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return determineRealStatement(sql).executeUpdate(sql);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return determineRealStatement(sql).execute(sql);
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return determineRealStatement(sql).executeUpdate(sql,autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return determineRealStatement(sql).executeUpdate(sql,columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return determineRealStatement(sql).executeUpdate(sql,columnNames);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return determineRealStatement(sql).execute(sql,autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return determineRealStatement(sql).execute(sql,columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return determineRealStatement(sql).execute(sql,columnNames);
    }
}
