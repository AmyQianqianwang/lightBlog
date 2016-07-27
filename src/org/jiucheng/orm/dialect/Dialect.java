/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.orm.dialect;

import org.jiucheng.orm.SQLHelper;

/**
 * 
 * @author jiucheng
 *
 */
public interface Dialect {
    
    public <T> void forSave(T entity, SQLHelper sh);
    
    public <T> void forDelete(T entity, SQLHelper sh);
    
    public <T> void forDeleteById(T entity, SQLHelper sh);

    public <T> void forUpdateById(T entity, SQLHelper sh);
    
    public <T> void forFind(T entity, SQLHelper sh);

    public <T> void forFindById(T entity, SQLHelper sh);

    public String forColumnLabel(String columnLabel);
}
