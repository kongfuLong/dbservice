package com.interfaces;

import com.annotations.DBType;
import com.annotations.RmiService;
import com.beans.DbTask;
import com.beans.Types;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 * Created by rcl on 2016/1/6.
 */
@RmiService(serverType = Types.DB)
public interface QshDbSuperInterface  {
    @DBType(readOnly = true)
    void test();

    //@DBType(readOnly = true)
   // <T> T querySingle(String sql, Object[] paramValues, int[] paramTypes, Class<T> clazz);

   // @DBType(readOnly = true)
    //<T> T querySingle(String sql, Object[] paramValues, Class<T> clazz);

    @DBType(readOnly = true)
    Map<String,Object> querySingleMap(String sql, Object[] paramValues, int[] paramTypes);

    @DBType(readOnly = true)
    Map<String,Object> querySingleMap(String sql, Object[] paramValues);

    //@DBType(readOnly = false)
    //<T> T querySingleMainServer(String sql, Object[] paramValues, int[] paramTypes, Class<T> clazz);

    //@DBType(readOnly = false)
    //<T> T querySingleMainServer(String sql, Object[] paramValues, Class<T> clazz);

    @DBType(readOnly = false)
    Map<String,Object> querySingleMapMainServer(String sql, Object[] paramValues, int[] paramTypes);

    @DBType(readOnly = false)
    Map<String,Object> querySingleMapMainServer(String sql, Object[] paramValues);

    @DBType(readOnly = true)
    List<Map<String,Object>> queryMap(String sql, Object[] paramValues, int[] paramTypes);

    @DBType(readOnly = true)
    List<Map<String,Object>> queryMap(String sql, Object[] paramValues);

    @DBType(readOnly = true)
    List<Map<String,Object>> queryMap(String sql);

    @DBType(readOnly = false)
    List<Map<String,Object>> queryMapMainServer(String sql);

    @DBType(readOnly = false)
    List<Map<String,Object>> queryMapMainServer(String sql, Object[] paramValues, int[] paramTypes);

    @DBType(readOnly = false)
    List<Map<String,Object>> queryMapMainServer(String sql, Object[] paramValues);

    /*@DBType(readOnly = true)
    <T> List<T> queryList(String sql, Object[] paramValues, int[] paramTypes, Class<T> clazz);

    @DBType(readOnly = true)
    <T> List<T> queryList(String sql, Class<T> clazz);

    @DBType(readOnly = true)
    <T> List<T> queryList(String sql, Object[] paramValues, Class<T> clazz);

    @DBType(readOnly = false)
    <T> List<T> queryListMainServer(String sql, Class<T> clazz);

    @DBType(readOnly = false)
    <T> List<T> queryListMainServer(String sql, Object[] paramValues, int[] paramTypes, Class<T> clazz);

    @DBType(readOnly = false)
    <T> List<T> queryListMainServer(String sql, Object[] paramValues, Class<T> clazz);*/

    @DBType(readOnly = false)
    void insertBatch(String sql, List<Object[]> params);

    @DBType(readOnly = false)
    int update(String sql, Object[] params);

    @DBType(readOnly = false)
    int update(String sql, Object[] params, int[] paramsType);

  /*  @DBType(readOnly = false)
    int update(DbTask dbTask);*/

    @DBType(readOnly = false)
    int update(String sql);

    @DBType(readOnly = false)
    @Transactional(propagation= Propagation.REQUIRED)
    List<DbTask> batchUpdate(List<DbTask> list);

    @DBType(readOnly = false)
    List<DbTask> batchUpdateNoTransaction(List<DbTask> list);
}
