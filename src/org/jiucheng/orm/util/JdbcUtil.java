package org.jiucheng.orm.util;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jiucheng.log.Log;
import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.util.ListUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;

public class JdbcUtil {
	
	private static final Log LOG = Log.getLog(JdbcUtil.class);

    public static void buildPreparedStatement(PreparedStatement pStmt, List<Object> args) throws SQLException{
        for(int i = 0; i < args.size(); i ++) {
            pStmt.setObject(i + 1, args.get(i));
        }
    }
    
    public static void buildCallableStatement(CallableStatement cStmt, List<Object> args) throws SQLException {
        for(int i = 0; i < args.size(); i ++) {
            cStmt.setObject(i + 1, args.get(i));
        }
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map> resultSetToListMap(ResultSet rs) {
        List<Map> result = ListUtil.newListMap();
        if(ObjectUtil.isNotNull(rs)) {
            String[] metaDatas = metaDataOfResultSet(rs);
            Map rows;
            try {
                while (rs.next()) {
                    rows = new HashMap();
                    for (int i = 0; i < metaDatas.length; i++) {
                        rows.put(metaDatas[i], rs.getObject(i + 1));
                    }
                    result.add(rows);
                }
            } catch (SQLException e) {
                if(LOG.isErrorEnabled()) {
                    LOG.error("", e);
                }
                throw new DataAccessException(e);
            }
        }
		return result;
	}
	
	public static String[] metaDataOfResultSet(ResultSet rs) {
		String[] result = StringUtil.NULL_ARRAY;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			result = new String[cols];
			for (int i = 1; i <= cols; i++) {
				result[i - 1] = rsmd.getColumnLabel(i);
			}
		} catch (SQLException e) {
            if(LOG.isErrorEnabled()) {
                LOG.error("", e);
            }
            throw new DataAccessException(e);
		}
		return result;
	}
	
    @SuppressWarnings("rawtypes")
	public static List<Map> list(Connection conn, SQLHelper sh){
        PreparedStatement pStmt = ObjectUtil.getNull();
        ResultSet rs = ObjectUtil.getNull();
        try{
            pStmt = conn.prepareStatement(sh.getSql());
            buildPreparedStatement(pStmt, sh.getValues());
            rs = pStmt.executeQuery();
            return resultSetToListMap(rs);
        }catch(SQLException e) {
            if(LOG.isErrorEnabled()) {
                LOG.error("", e);
            }
            throw new DataAccessException(e);
        }finally {
            close(rs);
            close(pStmt);
        }
    }
    
    public static int update(Connection conn, SQLHelper sh) {
        int r;
        PreparedStatement pStmt = ObjectUtil.getNull();
        try {
            pStmt = conn.prepareStatement(sh.getSql());
            buildPreparedStatement(pStmt, sh.getValues());
            r = pStmt.executeUpdate();
        }catch(SQLException e) {
            if(LOG.isErrorEnabled()) {
                LOG.error("",e);
            }
            throw new DataAccessException(e);
        }finally {
            close(pStmt);
        }
        return r;
    }
    
    public static Serializable save(Connection conn, SQLHelper sh) {
        Object r = ObjectUtil.getNull();
        PreparedStatement pStmt = ObjectUtil.getNull();
        ResultSet rs = ObjectUtil.getNull();
        try {
            pStmt = conn.prepareStatement(sh.getSql(), Statement.RETURN_GENERATED_KEYS);
            buildPreparedStatement(pStmt, sh.getValues());
            pStmt.execute();
            rs = pStmt.getGeneratedKeys();
            if(rs.next()) {
                r = rs.getObject(1);
            }  
        }catch(SQLException e) {
            if(LOG.isErrorEnabled()) {
            	LOG.error(e.getMessage(),e);
            }
            throw new DataAccessException(e);
        }finally {
            close(rs);
            close(pStmt);
        }
        return (Serializable) r;
    }
    
    public static int delete(Connection conn, SQLHelper sh) {
        return update(conn, sh);
    }
    
    @SuppressWarnings("rawtypes")
    public static List<Map> call(Connection conn, SQLHelper sh) {
        List<Map> r = ListUtil.newListMap();
        CallableStatement cStmt = null;
        ResultSet rs = null;
        try {
            cStmt = conn.prepareCall(sh.getSql());
            buildCallableStatement(cStmt, sh.getValues());
            cStmt.execute();
            rs = cStmt.getResultSet();
            r = resultSetToListMap(rs); 
        }catch(SQLException e) {
            if(LOG.isErrorEnabled()) {
                LOG.error("",e);
            }
            throw new DataAccessException(e);
        }finally {
            close(rs);
            close(cStmt);
        }
        return r;
    }
    
    public static ResultSet getResultSet(Connection conn, SQLHelper sh) {
        PreparedStatement pStmt = ObjectUtil.getNull();
        ResultSet rs = ObjectUtil.getNull();
        try {
            pStmt = conn.prepareStatement(sh.getSql());
            buildPreparedStatement(pStmt, sh.getValues());
            pStmt.execute();
            rs = pStmt.getResultSet();
        }catch(SQLException e) {
            if(LOG.isErrorEnabled()) {
                LOG.error("", e);
            }
            throw new DataAccessException(e);
        }
        return rs;
    }
    
    public static void close(Connection conn) {
        if (ObjectUtil.isNotNull(conn)) {
            try {
                if(!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                if(LOG.isDebugEnabled()) {
                	LOG.debug("close Connection fault");
                }
            }
        }
    }
    
	public static void close(ResultSet rs){
	    if(ObjectUtil.isNotNull(rs)) {
            try {
                rs.close();
            } catch (SQLException e) {
                if(LOG.isDebugEnabled()) {
                	LOG.debug("close ResultSet fault");
                }
            }
	    }
	}
	
	public static void close(Statement stmt){
        if(ObjectUtil.isNotNull(stmt)) {
            try {
                stmt.close();
            } catch (SQLException e) {
                if(LOG.isDebugEnabled()) {
                	LOG.debug("close Statement fault");
                }
            }
        }
	}
}
