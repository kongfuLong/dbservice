package com.tools;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLDecoder;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class ReadWriteProperties {

    /**
     * 读取配置文件，包含配置文件扩展名.properties
     *
     * @param proFileName
     * @return
     */
    public Properties getProperties(String proFileName) {
        try {
            String filePath = URLDecoder.decode(this.getClass().getClassLoader().getResource(proFileName).getFile(), "UTF-8");
            filePath = filePath.replace("\\", "/");
            Properties props = new Properties();
            InputStream in = new FileInputStream(new File(filePath));
            props.load(in);
            in.close();

            return props;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 读取配置文件
     *
     * @param proFileName 配置文件的文件名，不包含文件扩展名
     * @param key
     * @return
     */
    public String readValue(String proFileName, String key) {
        if (proFileName == null || "".equals(proFileName) || key == null || "".equals(key)) {
            return null;
        }
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(proFileName);

            if (bundle.containsKey(key)) {
                String value = bundle.getString(key);
                value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                return value;
            }
        } catch (UnsupportedEncodingException e) {
        }

        return null;
    }

    /**
     * 读取配置文件所有键：值
     *
     * @param proFileName 配置文件的文件名，包含文件扩展名
     * @return
     */
    public Set<Entry<Object, Object>> readAllValue(String proFileName) {
        try {
            String filePath = URLDecoder.decode(this.getClass().getClassLoader().getResource(proFileName).getFile(), "UTF-8");
            filePath = filePath.replace("\\", "/");
            Properties props = new Properties();
            InputStream in = new FileInputStream(new File(filePath));
            props.load(in);
            in.close();

            return props.entrySet();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 向配置文件写入或更新键值
     *
     * @param proFileName 配置文件的文件名，包含文件扩展名
     * @param key
     * @param value
     */
    public boolean writeValue(String proFileName, String key, String value) {
        try {
            String filePath = URLDecoder.decode(this.getClass().getClassLoader().getResource(proFileName).getFile(), "UTF-8");
            filePath = filePath.replace("\\", "/");
            Properties props = new Properties();
            InputStream in = new FileInputStream(new File(filePath));
            props.load(in);
            in.close();

            OutputStream out = new FileOutputStream(new File(filePath));
            props.setProperty(key, value);
            props.store(out, "");
            out.flush();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }


}
