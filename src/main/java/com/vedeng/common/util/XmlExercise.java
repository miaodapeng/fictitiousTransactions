package com.vedeng.common.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.common.constants.Contant;
import org.codehaus.groovy.classgen.asm.StatementWriter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.vedeng.common.constant.XmlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlExercise {
	public static Logger logger = LoggerFactory.getLogger(XmlExercise.class);

	/**
	 * <P>map集合转化成xml字符串</P>
	 * @param map
	 * @param rootName
	 * @return
	 */
	public static String mapToXml(Map<String, Object> map, String rootName)
	{
		Element root = new Element(rootName);
		if (map == null)
			return xmlToString(root);
		for (String str : map.keySet()) {
			Object object = map.get(str);
			if(object instanceof java.util.List){//判断是否为List
				 List list = (List) map.get(str);
				 if(list.size() > 0){
					 for(int i=0;i<list.size();i++){
						 if(list.get(i) instanceof java.util.Map){
							 Map<String, Object> chiledMap = (Map<String, Object>)list.get(i);
							 Element rootChild = new Element(str);
							 for(Map.Entry<String, Object> entry : chiledMap.entrySet()){
									rootChild.addContent(new Element(entry.getKey()).setText(entry.getValue() == null ? ""
											: entry.getValue() + ""));
								}
							 root.addContent(rootChild);
						 }else{
							 
							 root.addContent(new Element(str).setText(list.get(i).toString()));
						 }
					 }
				 }
			}else if(object instanceof java.util.Map){
				Map<String, Object> chiledMap = (Map<String, Object>)object;
				Element rootChild = new Element(str);
				for(Map.Entry<String, Object> entry : chiledMap.entrySet()){
					rootChild.addContent(new Element(entry.getKey()).setText(entry.getValue() == null ? ""
							: entry.getValue() + ""));
				}
				root.addContent(rootChild);
			}
			else{
				root.addContent(new Element(str).setText((map.get(str) == null ? ""
						: map.get(str) + "")));
			}
		}
		return xmlToString(root);
	}
	
	/**
	 * <P>map集合转化成xml字符串</P>金蝶专用
	 * @param map
	 * @param rootName
	 * @return
	 */
	public static String mapToListXml(Map<String, Object> map, String rootName)
	{
		Element root = new Element(rootName);
		if (map == null)
			return xmlToString(root);
		for (String str : map.keySet()) {
			Object object = map.get(str);
			if(object instanceof java.util.List){//判断是否为List
				Element rootChild = new Element("list");
				 List list = (List) map.get(str);
				 if(list.size() > 0){
					 for(int i=0;i<list.size();i++){
						 if(list.get(i) instanceof java.util.Map){
							 Map<String, Object> chiledMap = (Map<String, Object>)list.get(i);
							 for(Map.Entry<String, Object> entry : chiledMap.entrySet()){
								 Element rootChild2 = new Element(entry.getKey());
								 if(entry.getValue() instanceof java.util.Map){
									 Map<String, Object> chiledMap2 = (Map<String, Object>)entry.getValue();
									 for(Map.Entry<String, Object> entry2 : chiledMap2.entrySet()){
										 Element rootChild3 = new Element(entry2.getKey());
										 rootChild3.addContent(entry2.getValue() +"");
										 rootChild2.addContent(rootChild3);
									 }
								 }
								 rootChild.addContent(rootChild2);
								}
						 }else{
							 root.addContent(new Element(str).setText(list.get(i).toString()));
						 }
					 }
				 }
				 root.addContent(rootChild);
			}else if(object instanceof java.util.Map){
				Map<String, Object> chiledMap = (Map<String, Object>)object;
				Element rootChild = new Element(str);
				for(Map.Entry<String, Object> entry : chiledMap.entrySet()){
					rootChild.addContent(new Element(entry.getKey()).setText(entry.getValue() == null ? "" : entry.getValue() + ""));
				}
				root.addContent(rootChild);
			}
			else{
				root.addContent(new Element(str).setText((map.get(str) == null ? "" : map.get(str) + "")));
			}
		}
		return xmlToString(root);
	}
	
	/**
	 * <P>xml字符串转化成map集合</P>
	 * @param xmlStr字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> xmlToMap(String xmlStr) {
		
		SAXBuilder builder = new SAXBuilder();
		Map<String, Object> map = new IdentityHashMap<String, Object>();
		try {
			xmlStr = URLDecoder.decode(xmlStr, XmlConstants.UTF);
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			List<Map<String,Object>> dataList = new ArrayList<>();
			for (Element e : list) {
				List<Element> children = e.getChildren();
				if(children.size() > 0){
					Map<String, Object> childMap = new HashMap<String, Object>();
					for(Element ce : children){
						childMap.put(ce.getName(), XmlExercise.unmarshal(ce.getText()));
					}
					dataList.add(childMap);
					map.put(e.getName(), childMap);
				}else{
					map.put(e.getName(), XmlExercise.unmarshal(e.getText()));
				}
			}
			return map;
		} catch (JDOMException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return map;
		
	}
	
	
	/**
	 * <P>xml字符串转化成map集合</P>
	 * @param xmlStr字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> xmlToMapList(String xmlStr)
	{
		
		SAXBuilder builder = new SAXBuilder();
		Map<String, Object> map = new IdentityHashMap<String, Object>();
		try {
			xmlStr = URLDecoder.decode(xmlStr, XmlConstants.UTF);
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			List<Map<String,Object>> dataList = new ArrayList<>();
			for (Element e : list) {
				List<Element> children = e.getChildren();
				if(children.size() > 0){
					Map<String, Object> childMap = new HashMap<String, Object>();
					for(Element ce : children){
						childMap.put(ce.getName(), XmlExercise.unmarshal(ce.getText()));
					}
					dataList.add(childMap);
					//map.put(e.getName(), childMap);
				}else{
					map.put(e.getName(), XmlExercise.unmarshal(e.getText()));
				}
			}
			if(dataList.size() > 0){
				map.put("data", dataList);
			}
			return map;
		} catch (JDOMException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return map;
		
	}
	
	/**
	 * <P>xml字符串转化成map集合</P>
	 * @param xmlStr字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> xmlToMapAndList(String xmlStr)
	{
		
		SAXBuilder builder = new SAXBuilder();
		Map<String, Object> map = new IdentityHashMap<String, Object>();
		try {
			xmlStr = URLDecoder.decode(xmlStr, XmlConstants.UTF);
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			List<Map<String,Object>> dataList = new ArrayList<>();
			for (Element e : list) {
				List<Element> children = e.getChildren();
				if(children.size() > 0){
					Map<String, Object> childMap = new HashMap<String, Object>();
					for(Element ce : children){
						List<Element> children2 = ce.getChildren();
						if(children2.size() > 0){
							List<Map<String,Object>> dataList2 = new ArrayList<>();
							Map<String, Object> childMap2 = new HashMap<String, Object>();
							for(Element ce2 : children2){
								childMap2.put(ce2.getName(), XmlExercise.unmarshal(ce2.getText()));
							}
							dataList.add(childMap2);
						}else{
							childMap.put(ce.getName(), XmlExercise.unmarshal(ce.getText()));
							dataList.add(childMap);
						}
					}
					//map.put(e.getName(), childMap);
				}else{
					map.put(e.getName(), XmlExercise.unmarshal(e.getText()));
				}
			}
			if(dataList.size() > 0){
				map.put("data", dataList);
			}
			return map;
		} catch (JDOMException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return map;
		
	}
	
	/**
	 * <P>list集合转化成xml字符串</P>
	 * @param list
	 * @param rootName
	 * @param parentName
	 * @return
	 */
	public static String listToXml(List<Map<String, Object>> list,
			String rootName, String parentName)
	{
		Element root = new Element(rootName);
		//boolean flag = true;
		Element parentElement = null;
		Element child = null;
		if (list == null)
			return xmlToString(root);
		for (Map<String, Object> map : list){
			//if (flag) {
				//flag = false;
				//for (String str : map.keySet()) {
					//child = new Element(str).setText(map.get(str) == null ? ""
					//		: (map.get(str) + ""));
					//root.addContent(child);
				//}
			//} else {
				parentElement = new Element(parentName);
				root.addContent(parentElement);
				for (String str : map.keySet()) {
					child = new Element(str).setText(map.get(str) == null ? ""
							: (map.get(str) + ""));
					parentElement.addContent(child);
				}
			//}
		}
		return xmlToString(root);
	}
	

	/**
	 * <P>xml字符串转化成list集合</P>
	 * @param xmlStr
	 * @return
	 */
	public static List<Map<String, Object>> xmlToList(String xmlStr) 
	{
		SAXBuilder builder = new SAXBuilder();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		boolean flag = true;
		try {
			xmlStr = URLDecoder.decode(xmlStr, XmlConstants.UTF);
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			for (Element e : list) {
				if (e.getChildren().size() == 0) {
					if (flag) {
						flag = false;
						map = new HashMap<String, Object>();
						resultList.add(map);
					}
					map.put(e.getName(), XmlExercise.unmarshal(e.getText()));
				} else {
					map = new HashMap<String, Object>();
					List<Element> childrenList = e.getChildren();
					resultList.add(map);
					for (Element element : childrenList) {
						map.put(element.getName(), XmlExercise.unmarshal(element.getText()));
					}
				}
			}
			return resultList;
		} catch (JDOMException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return resultList;
	}
	
	/**
	 * 将Element对象转化成字串
	 * @param element
	 * @return
	 */
	public static String xmlToString(Element element) 
	{
		XMLOutputter output = new XMLOutputter();
		output.setFormat(Format.getPrettyFormat().setEncoding("utf-8"));
		Document doc = new Document(element);
		String str = output.outputString(doc);
		return str;
	}
	

	/**
	 * 解析xml字符串获取节点内容
	 * @param xmlStr
	 *            xml字符串
	 * @param nodeStr
	 *            需要获取的节点
	 * @return 解析信息
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> readXml(String xmlStr, String[] nodeStrArr) 
	{
		Map<String, String> map = new HashMap<String, String>();
		SAXBuilder builder = new SAXBuilder();
		try {
			xmlStr = URLDecoder.decode(xmlStr, XmlConstants.UTF);
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> l = null;
			String str = "";
			for (String nodeStr : nodeStrArr) {
				l = root.getChildren(nodeStr);
				str = l.get(0).getText();
				if ("".equals(str) || str == null) {
					map.put(XmlConstants.MSG, XmlConstants.NODE_ERROR);
					return map;
				} else {
					map.put(XmlConstants.MSG, XmlConstants.SUCCESS);
					map.put(nodeStr, str);
				}
			}
		} catch (JDOMException e) {
			map.put(XmlConstants.MSG, XmlConstants.NODE_ERROR);
			return map;
		} catch (UnsupportedEncodingException e) {
			map.put(XmlConstants.MSG, XmlConstants.CODE_ERROR);
			return map;
		} catch (IOException e) {
			map.put(XmlConstants.MSG, XmlConstants.BUILD_ERROR);
			return map;
		} catch (Exception e) {
			map.put(XmlConstants.MSG, XmlConstants.BUILD_ERROR);
			return map;
		}
		return map;
	}



	/**
	 * 生成带内容的节点
	 * @param parentElement父节点
	 * @param map
	 *            数据集
	 * @return
	 */
	public static Element createNodes(Element parentElement,
			Map<String, Object> map)
	{
		String msg = "";
		Iterator<String> it = map.keySet().iterator();
		String tempStr = "";
		Element sonElement = null;
		while (it.hasNext()) {
			tempStr = it.next();
			msg = (map.get(tempStr)) == null ? "" : (map.get(tempStr) + "");
			sonElement = new Element(tempStr);
			parentElement.addContent(sonElement.setText(msg));
		}
		return parentElement;
	}

	/**
	 * 生成不带内容的节点
	 * @param root根节点
	 * @param strArr
	 *            节点字符
	 */
	public static void createNodes(Element root, String[] strArr) 
	{
		Element e = null;
		for (String str : strArr) {
			e = new Element(str);
			root.addContent(e);
		}

	}
	
	 /** 
     * 将对象直接转换成String类型的 XML输出 
     *  
     * @param obj 
     * @return 
     */  
    public static String convertToXml(Object obj) {  
        // 创建输出流  
        StringWriter sw = new StringWriter();  
        try {  
            // 利用jdk中自带的转换类实现  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
  
            Marshaller marshaller = context.createMarshaller();  
            // 格式化xml输出的格式  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  
                    Boolean.TRUE);  
            // 将对象转换成输出流形式的xml  
            marshaller.marshal(obj, sw);  
        } catch (JAXBException e) {  
            logger.error(Contant.ERROR_MSG, e);
        }  
        return sw.toString();  
    }  
    
    
    public static String unmarshal (String v) throws Exception
    {
        if ("<![CDATA[]]>".equals (v))
        {
            return "";
        }
        String v1 = null;
        String v2 = null;
        String subStart = "<![CDATA[";
        int a = v.indexOf (subStart);
        if (a >= 0)
        {
            v1 = v.substring (subStart.length (), v.length ());
        }
        else
        {
            return v;
        }
        String subEnd = "]]>";
        int b = v1.indexOf (subEnd);
        if (b >= 0)
        {
            v2 = v1.substring (0, b);
        }
        return v2;
    }
}
