package com.tools;

import com.interfaces.QshDbSuperInterface;
import com.beans.DbTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by rcl on 2015/1/6.
 * //由于远程调用机制 使用泛型不便于数据端维护操作 暂关闭泛型传入功能
 */
@Service
public class SpringDbHelper  implements QshDbSuperInterface{


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private <T> RowMapper<T> setRowMapper(Class<T> clazz){

        return BeanPropertyRowMapper.newInstance(clazz);
    }

    public Connection getConnect(){
        try {
            return jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void test() {
        System.out.println("测试成功!!!");
    }

    public <T> T querySingle(String sql,Object[] paramValues,int [] paramTypes,Class<T> clazz){
        List<?> list = jdbcTemplate.query(sql, paramValues, paramTypes, setRowMapper(clazz));
        if(list.size()<=0){
            return null;
        }
        return (T) list.iterator().next();
    }
    public <T> T querySingle(String sql,Object[] paramValues,Class<T> clazz){
        List<?> list = jdbcTemplate.query(sql, paramValues, setRowMapper(clazz));
        if(list.size()<=0){
            return null;
        }
        return (T) list.iterator().next();
    }
    @Override
    public Map<String,Object> querySingleMap(String sql,Object[] paramValues,int [] paramTypes){
        return jdbcTemplate.queryForMap(sql, paramValues, paramTypes);
    }
    @Override
    public Map<String,Object> querySingleMap(String sql,Object[] paramValues){
        return jdbcTemplate.queryForMap(sql, paramValues);
    }
    public <T> T querySingleMainServer(String sql,Object[] paramValues,int [] paramTypes,Class<T> clazz){
        List<?> list = jdbcTemplate.query(sql, paramValues, paramTypes, setRowMapper(clazz));
        if(list.size()<=0){
            return null;
        }
        return (T) list.iterator().next();
    }
    public <T> T querySingleMainServer(String sql,Object[] paramValues,Class<T> clazz){
        List<?> list = jdbcTemplate.query(sql, paramValues, setRowMapper(clazz));
        if(list.size()<=0){
            return null;
        }
        return (T) list.iterator().next();
    }
    @Override
    public Map<String,Object> querySingleMapMainServer(String sql,Object[] paramValues,int [] paramTypes){
        return jdbcTemplate.queryForMap(sql, paramValues, paramTypes);
    }
    @Override
    public Map<String,Object> querySingleMapMainServer(String sql,Object[] paramValues){
        return jdbcTemplate.queryForMap(sql, paramValues);
    }
    @Override
    public List<Map<String,Object>> queryMap(String sql,Object[] paramValues,int [] paramTypes){
        return jdbcTemplate.queryForList(sql, paramValues, paramTypes);
    }
    @Override
    public List<Map<String,Object>> queryMap(String sql,Object[] paramValues){
        return jdbcTemplate.queryForList(sql, paramValues);
    }

    @Override
    public List<Map<String, Object>> queryMap(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> queryMapMainServer(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String,Object>> queryMapMainServer(String sql,Object[] paramValues,int [] paramTypes){
        return jdbcTemplate.queryForList(sql, paramValues, paramTypes);
    }
    @Override
    public List<Map<String,Object>> queryMapMainServer(String sql,Object[] paramValues){
        return jdbcTemplate.queryForList(sql, paramValues);
    }
    public <T> List<T> queryList(String sql, Object[] paramValues, int[] paramTypes, Class<T> clazz){
        return jdbcTemplate.query(sql, paramValues, paramTypes,setRowMapper(clazz));
    }
    public <T> List<T> queryList(String sql, Class<T> clazz){
        return jdbcTemplate.query(sql,setRowMapper(clazz));
    }
    public <T> List<T> queryList(String sql, Object[] paramValues, Class<T> clazz){
        return jdbcTemplate.query(sql, paramValues,setRowMapper(clazz));
    }
    public <T> List<T> queryListMainServer(String sql, Class<T> clazz){
        return jdbcTemplate.query(sql,setRowMapper(clazz));
    }
    public <T> List<T> queryListMainServer(String sql, Object[] paramValues, int[] paramTypes, Class<T> clazz){
        return jdbcTemplate.query(sql, paramValues, paramTypes,setRowMapper(clazz));
    }
    public <T> List<T> queryListMainServer(String sql, Object[] paramValues, Class<T> clazz){
        return jdbcTemplate.query(sql, paramValues,setRowMapper(clazz));
    }
    @Override
    public void insertBatch(String sql, List<Object[]> params){
        jdbcTemplate.batchUpdate(sql,params);
    }
    @Override
    public int update(String sql, Object[] params){
        return jdbcTemplate.update(sql,params);
    }
    @Override
    public int update(String sql, Object[] params, int[] paramsType){
        return jdbcTemplate.update(sql,params,paramsType);
    }
   /* @Override
    public int update(DbTask dbTask){
        String sql  = SqlXmlRead.getSQL(dbTask.getTaskId());
        return 0;
    }*/
    @Override
    public int update(String sql){
        return jdbcTemplate.update(sql);
    }




    /**
     *
     * @param list  带事务处理的批量更新操作
     * @return
     */
    @Override
    public List<DbTask> batchUpdate(List<DbTask> list){
        Iterator<DbTask> iterator = list.iterator();
        try{
            while (iterator.hasNext()){
                DbTask dbTask = iterator.next();
                String sql  = dbTask.getSql();
                if(dbTask.isIndexNeed()){
                    KeyHolder key = new GeneratedKeyHolder();
                    dbTask.setRows(jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                            for(int i=0;i<dbTask.getParam().length;i++){
                                if(dbTask.getParamType()!=null){
                                    ps.setObject(i+1,dbTask.getParam()[i],dbTask.getParamType()[i]);
                                }else{
                                    ps.setObject(i+1,dbTask.getParam()[i]);
                                }
                            }
                            return ps;
                        }
                    },key));
                    dbTask.setIndexId(key.getKey());
                }else {
                    if(dbTask.getParamType()==null){
                        dbTask.setRows(jdbcTemplate.update(sql,dbTask.getParam()));
                    }else{
                        dbTask.setRows(jdbcTemplate.update(sql,dbTask.getParam(),dbTask.getParamType()));
                    }
                }
            }
        }catch (RuntimeException e){
            System.out.println("更新出错，进行数据回滚：" + e.getMessage());
            return null;
        }

        return list;
    }

    /**
     *
     * @param list  不带事务处理的批量更新操作
     * @return
     */
    @Override
    public List<DbTask> batchUpdateNoTransaction(List<DbTask> list){
        Iterator<DbTask> iterator = list.iterator();
        try{
            while (iterator.hasNext()){
                DbTask dbTask = iterator.next();
                String sql  = SqlXmlRead.getSQL(dbTask.getTaskId());
                if(dbTask.isIndexNeed()){
                    KeyHolder key = new GeneratedKeyHolder();
                    dbTask.setRows(jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                            for(int i=0;i<dbTask.getParam().length;i++){
                                if(dbTask.getParamType()!=null){
                                    ps.setObject(i+1,dbTask.getParam()[i],dbTask.getParamType()[i]);
                                }else{
                                    ps.setObject(i+1,dbTask.getParam()[i]);
                                }
                            }
                            return ps;
                        }
                    },key));
                    dbTask.setIndexId(key.getKey());
                }else {
                    if(dbTask.getParamType()==null){
                        dbTask.setRows(jdbcTemplate.update(sql,dbTask.getParam()));
                    }else{
                        dbTask.setRows(jdbcTemplate.update(sql,dbTask.getParam(),dbTask.getParamType()));
                    }
                }
            }

        }catch (RuntimeException e){
            System.out.println("更新出错，进行数据回滚：" + e.getMessage());
            return null;
        }

        return list;
    }

   


}
