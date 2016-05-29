package com.tools;

import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * sqlconfig XML文件读取
 *
 * @author huhoo
 */
public class SqlXmlRead {

    public static String getSQL(String id) {
        String value = "";
        String configPath = System.getProperty("user.dir")+"/sqlconfig.xml";
        try {
            configPath = java.net.URLDecoder.decode(configPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        File file = new File(configPath);
        Document doc = XmlHelper.getDocument(file);
        Element sqlElement = doc.elementByID(id);
        if (sqlElement != null) {
            value = sqlElement.valueOf("@value");
        }
        value = value.replaceAll("＜", "<");
        value = value.replaceAll("＞", ">");
        return value;
    }

    public static String getSQL(int id)  {
        return getSQL(String.valueOf(id));
    }

    public static Document getSqlParamFile(String id) throws UnsupportedEncodingException {
        String configPath = System.getProperty("user.dir")+"/sqlmap/"+id+".xml";
        configPath = java.net.URLDecoder.decode(configPath, "UTF-8");
        File file = new File(configPath);
        if (file.exists()) {
            return XmlHelper.getDocument(file);
        }
        return null;
    }
}