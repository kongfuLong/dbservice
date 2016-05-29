package com.tools;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-30
 * Time: 下午2:01
 */
public class XmlHelper {

    public static Document createDocument() {
        return DocumentHelper.createDocument();
    }

    public static Document getDocument(String fileName) {
        return getDocument(new File(fileName));
    }

    public static Document getDocument(File file) {
        try {
            if (file.exists()) {
                return getDocument(new FileInputStream(file));
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static Document getDocument(InputStream ins) {
        try {
            return new SAXReader().read(ins);
        } catch (DocumentException e) {
            return null;
        }
    }

    public static boolean putDocument(File file, Document doc) {
        try {
            return putDocument(new FileOutputStream(file), doc);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean putDocument(OutputStream out, Document doc) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.setEscapeText(false);
            writer.write(doc);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean putDocument(OutputStream out, String xmlString) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.setEscapeText(false);
            writer.write(StringToXMLDocument(xmlString));
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Map<String, String> ElementToHash(Element el) {
        return ElementToHash(el, null);
    }

    public static Map<String, String> ElementToHash(Element el, String prefix) {
        if (el == null) {
            return null;
        }
        Map<String, String> props = new HashMap<String, String>();
        Iterator iterator = el.attributes().iterator();
        while (iterator.hasNext()) {
            Attribute attribute = (Attribute) iterator.next();
            String name = attribute.getName();
            if (prefix != null && prefix.length() > 0) {
                char c = name.charAt(0);
                if (c > 96 && c < 123) {
                    c -= 32;
                }
                name = prefix + c + name.substring(1);
            }
            String value = attribute.getText();
            props.put(name, value);
        }
        return props;
    }

    public static Document StringToXMLDocument(String xmlString) {
        try {
            return DocumentHelper.parseText(xmlString);
        } catch (DocumentException e) {
            return null;
        }
    }

    public static String XMLDocumentToString(Document document) {
        return document.asXML();
    }
}