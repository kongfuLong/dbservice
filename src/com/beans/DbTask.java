package com.beans;

/**
 * Created by rcl on 2015/12/22.
 * 主要为更新操作的 批量任务设计
 */
public class DbTask {

    private int taskId;

    private String sql;

    private boolean query = false;

    private Object[] param;

    private int[] paramType;

    private boolean indexNeed = false;//是否需要返回主键

    private int rows;

    private Object indexId;

    public DbTask() {
    }

    public DbTask(int taskId, Object[] param, int[] paramType) {
        this.taskId = taskId;
        this.param = param;
        this.paramType = paramType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public boolean isIndexNeed() {
        return indexNeed;
    }

    public void setIndexNeed(boolean indexNeed) {
        this.indexNeed = indexNeed;
    }

    public Object getIndexId() {
        return indexId;
    }

    public void setIndexId(Object indexId) {
        this.indexId = indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    public DbTask(int taskId, Object[] param) {
        this.taskId = taskId;
        this.param = param;
    }

    public DbTask(int taskId) {
        this.taskId = taskId;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }

    public int[] getParamType() {
        return paramType;
    }

    public void setParamType(int[] paramType) {
        this.paramType = paramType;
    }
}
