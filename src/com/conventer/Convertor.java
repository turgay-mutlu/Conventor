/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conventer;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML <-> json
 * @author Turgay
 */
public class Convertor {
    /**
     * Verlinen xml stringini insanların okuyabileceği düzene getirir.
     * @param unformattedXml Düzesiz xml stringi
     * @return Düzenli xml stringi
     */
    private String format(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Stringi xml Documentine çevirir.
     * @param in xml Stringi
     * @return xml Documenti
     */
    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Bir json stringini xml Stringine çevirir.
     * @param json json Stringi
     * @return XML Çıktısı
     */
    public String convertToXML(String json){
        JSONObject jsonObject;
        String xml="";
        try {
            jsonObject=new JSONObject(json);
            xml=XML.toString(jsonObject);
            
        } catch (JSONException ex) {
            Logger.getLogger(Convertor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return format(xml);
    }
    
    /**
     * Bir XML stringini json Stringine çevirir.
     * @param xml XML stringi
     * @return json Çıktısı
     */
    public String convertToJson(String xml){
        
        try {
            return XML.toJSONObject(xml).toString(1);
        } catch (JSONException ex) {
            Logger.getLogger(Convertor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
}
