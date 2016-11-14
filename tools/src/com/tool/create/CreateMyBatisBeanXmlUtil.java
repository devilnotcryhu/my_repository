package com.tool.create;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tool.model.OrderDemo;




/**
 * <p>版权所有:(C)2003-2010 联动优势科技有限公司</p>
 * @作者: 唐贵斌
 * @日期: 2011-8-19 上午10:48:23
 * @描述: [CreateBeanXml]根据PO自动生成ibatis数据库操作xml文件 说明：
 *      1、除了字段“serialVersionUID”外，作为主键的变量名必须写在PO类的其它变量的前面
 *      （程序使用第一个非“serialVersionUID”字段作为主键） 2、生成的表名规则：表结构前置 +
 *      以小写字母开头的PO类名，如使用了setTableNamePre
 *      ("UMPAY.t_")，PO类名为user，则生成表名为：UMPAY.t_user
 *
 *      3、生成操作类型：select_what，conditions
 *      ，key_where，insert_col，insert_val，GetByKey，
 *      Find，Find_count，Insert，Delete，Update
 *
 *      4、需要传入生成文件目录绝对路径，如“E:/umpay/Workspaces
 *      /MyEclipse9/platMonitor/src/config/ibatis/sql/mysql/busi/”
 *
 *      5、生成文件名规则：PO类+".xml"，如PO类名为User，生成User.xml文件
 */
public class CreateMyBatisBeanXmlUtil{

	public static void main(String[] args){
		CreateMyBatisBeanXmlUtil c = new CreateMyBatisBeanXmlUtil();
		c.setXmlFilePath("E:\\temp1"); //STLDATE, BANKID, MERID, SUBMERID
//		c.addPo(UserBehaviorPO.class, "PBMP.T_USE_BEHAVIOR", new String[]{"stldate","bankid","merid","submerid"});
		c.addPo(OrderDemo.class, "ecp_orders", new String[]{});
		//F:\eclipse-jee-juno-win32\
		//	c.setXmlFilePath("/tt/umpay/bdsb/bdsb-core/src/main/resources/mybatis/mapper");
//		c.setXmlFilePath("F:\\eclipse-jee-juno-win32\\");
//		c.addPo(WAdvisory, "PBMP.T_USER_LOG", new String[]{"ID"});
		c.create();
	}

//	private static final Logger log = Logger.getLogger(CreateMyBatisBeanXmlUtil.class);
	private Object obj = null; // 需要生成
	private String fileName = "";// 输出文件名,包含全路径
	private String xmlXmlFilePath = "";// 输出文件全路径,不包含文件名
	private String beanXmlFilePath = "";// BEAN文件全路径,不包含文件名
	private StringBuffer excludeStr = new StringBuffer();// 排除的字体，以";"分隔
	private List<String> fieldList = new ArrayList<String>();
	private String objName = "";
	private String objFullName = "";
	private String aliasName = "";
	private List<Object> objList = new ArrayList<Object>();
	private List<String> tableNameList = new ArrayList<String>();
	private String[] keys = null;

	public CreateMyBatisBeanXmlUtil(){
		excludeStr.append("serialVersionUID");
	}


	public void create(){
		while(objList.size() != 0){
			createOne(objList.remove(0), tableNameList.remove(0));
		}
	}

	private void createOne(Object object, String tableName){
		obj = null; // 需要生成
		fileName = "";// 输出文件名,包含全路径
		fieldList.clear();
		objName = "";
		objFullName = "";
		this.obj = object;
		this.objName = this.obj.getClass().getSimpleName();
		this.objFullName = this.obj.getClass().getName();
//		aliasName = obj.getClass().toString().substring(6);
		aliasName = this.objName;
		this.fileName = (new File(this.xmlXmlFilePath)).getAbsolutePath() + "/"
				+ this.objName + ".xml";
//		log.debug("objName: " + objName);
//		log.debug("objFullName: " + objFullName);
//		log.debug("aliasName: " + aliasName);
		checkField();
		if(this.fieldList.size() == 0){
			return;
		}
		checkKeyName();
		BufferedWriter output = null;
		try{
			output = new BufferedWriter(new FileWriter(fileName));
			StringBuffer str = new StringBuffer();
			str.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			str.append("<!DOCTYPE mapper\n");
			str.append("PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\"\n");
			str.append("\"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">\n");
			str.append("\n");
			str.append("<mapper namespace=\"" + this.objName + "\">\n");
			str.append("\n");
			str.append("	<sql id=\"tn_" +objName + "\">\n");
			str.append("		" +  (tableName) + "\n");
			str.append("	</sql>\n");
			str.append("	<sql id=\"select_what\">\n");
			str.append(makeSelectWhat("<include refid=\"tn_" +objName + "\"/>"));
			str.append("	</sql>\n");
			str.append("\n");
			str.append("	<sql id=\"conditions\">\n");
			 str.append("		<where>\n");
			str.append(makeWhereFind());
			 str.append("		</where>\n");
			str.append("	</sql>\n");
			str.append("\n");
			str.append("	<sql id=\"key_map\">\n");
			str.append("		<where>");

			int i = 0;
			for(String key : keys) {
				if(null != StringUtils.trimToNull(key)) {
					if(i++ != 0) {
						str.append( " and ");
					}
					str.append(  key + "=#{" + key + "}");
				}

			}
					str.append("</where>\n");
			str.append("	</sql>\n");
			str.append("\n");
			str.append("	<sql id=\"insert_col\">\n");
			str.append(makeInsertColumn());
			str.append("	</sql>\n");
			str.append("\n");
			str.append("	<sql id=\"insert_val\">\n");
			str.append(makeInsertValue());
			str.append("	</sql>\n");
			str.append("\n");
			str.append("	<select id=\"Get\" resultType=\"" + this.aliasName
					+ "\" parameterType=\"" + this.aliasName + "\">\n");
			str.append("		<include refid=\"select_what\" />\n");
			str.append("		<include refid=\"key_map\" />\n");
			str.append("	</select>\n");
			str.append("\n");
			str.append("	<select id=\"Find\" resultType=\"" + this.aliasName
					+ "\" parameterType=\"" + this.aliasName + "\">\n");
			str.append("		<include refid=\"select_what\" />\n");
			str.append("		<include refid=\"conditions\" />\n");
			str.append("	</select>\n");
			str.append("\n");
			str.append("	<select id=\"Find_count\" resultType=\"int\" parameterType=\""
					+ this.aliasName + "\">\n");
			str.append("		select count(0) from " + "<include refid=\"tn_" +objName + "\"/>"
					+ "\n");
			str.append("		<include refid=\"conditions\" />\n");
			str.append("	</select>\n");
			str.append("\n");
			str.append("\n");
			str.append("	<insert id=\"Insert\">\n");
			str.append("		insert into " + "<include refid=\"tn_" +objName + "\"/>" + "(" + keys[0]
					+ "\n");
			str.append("		<include refid=\"insert_col\" />\n");
			str.append("		)values(\n");
			str.append("		#{" + keys[0] + "}\n");
			str.append("		<include refid=\"insert_val\" />\n");
			str.append("		)\n");
			str.append("	</insert>\n");
			str.append("\n");
			str.append("\n");
			str.append("	<update id=\"Update\" parameterType=\""
					+ this.aliasName + "\">\n");
			str.append("		update " + "<include refid=\"tn_" +objName + "\"/>"
					+"\n");
			str.append("		<set>\n");
			str.append(makeUpdate());
			str.append("		</set>\n");
			str.append("		<include refid=\"key_map\" />\n");
			str.append("	</update>\n");
			str.append("\n");
			str.append("	<delete id=\"Delete\" parameterType=\""
					+ this.aliasName + "\">\n");
			str.append("		delete from " + "<include refid=\"tn_" +objName + "\"/>"
					+ "\n");
			str.append("		<include refid=\"conditions\"/>\n");
			str.append("	</delete>\n");
			str.append("\n");
			str.append("</mapper>\n");
			output.write(str.toString());
			output.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public void addPo(Object object, String tableName , String[] keys){
		String objName = object.getClass().getName();
		this.keys = keys;
		if(objName.equals("java.lang.Class")){
			objName = object.toString().substring(6);
		}
		Object obj = null;
		try{
			obj = (Class.forName(objName)).newInstance();
		}catch (InstantiationException e1){
			e1.printStackTrace();
		}catch (IllegalAccessException e1){
			e1.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		objList.add(obj);
		tableNameList.add(tableName);
	}

	public void addExcludeStr(String str){
		this.excludeStr.append(str + ";");
	}


	private void checkField(){
		// 获取f对象对应类中的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for(int i = 0, len = fields.length; i < len; i++){
			//final和static类型的去除
			if(Modifier.isFinal(fields[i].getModifiers())
					||Modifier.isStatic(fields[i].getModifiers())) {
				continue;
			}

			// 对于每个属性，获取属性名
			String varName = fields[i].getName();
			// System.out.println(varName);
			if(this.excludeStr.indexOf(varName) == -1){
				System.out.println(varName);
				fieldList.add(varName);
			}
		}
	}

	private void checkKeyName(){
		if(this.keys == null || keys.length == 0) {
			keys = new String[] {fieldList.get(0)};
		}
	}

	private String makeSelectWhat(String tableName){
		String str = "		select ";
		String what = "";
		for(int i = 0; i < fieldList.size(); i++){
			if(!what.equals("")){
				what += "		,";
			}
			what +=
				fieldList.get(i).trim() + "\n";
		}
		str += what + "		from " + tableName
		+ "\n";
		return str;
	}

	private String makeWhereFind(){
		String str = "";
		for(int i = 0; i < fieldList.size(); i++){
			str += "		<if test=\"" + fieldList.get(i) + "!=null and "+fieldList.get(i)+"!='' \"> and "
					+ fieldList.get(i)
					+ "=#{" + fieldList.get(i)
					+ "}</if>\n";
		}
//		str += "		1=1\n";
		return str;
	}

	private String makeInsertColumn(){
		String str = "";
		// 主键排除
		for(int i = 1; i < fieldList.size(); i++){
			str += "		<if test=\"" + fieldList.get(i) + "!=null\">,"
					+ fieldList.get(i) + "</if>\n";
		}
		return str;
	}

	private String makeInsertValue(){
		String str = "";
		// 主键排除
		for(int i = 1; i < fieldList.size(); i++){
			str += "		<if test=\"" + fieldList.get(i) + "!=null\">,#{"
					+ fieldList.get(i) + "}</if>\n";
		}
		return str;
	}

	private String makeUpdate(){
		String str = "";
		for(int i = 0; i < fieldList.size(); i++){
			str += "\t\t\t<if test=\"" + fieldList.get(i) + "!=null and "+fieldList.get(i)+"!='' \">"
					+ fieldList.get(i) + "=#{" + fieldList.get(i) + "}, </if>\n";

		}
		return str;
	}

	public void testValue(){
		// 获取f对象对应类中的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for(int i = 0, len = fields.length; i < len; i++){
			// 对于每个属性，获取属性名
			String varName = fields[i].getName();
			try{
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			}catch (IllegalArgumentException ex){
				ex.printStackTrace();
			}catch (IllegalAccessException ex){
				ex.printStackTrace();
			}
		}
	}

	public void setObj(Object obj){
		this.obj = obj;
	}

	public Object getObj(){
		return obj;
	}

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getFileName(){
		return fileName;
	}


	public void setObjName(String objName){
		this.objName = objName;
	}

	public String getObjName(){
		return objName;
	}

	public void setAliasName(String aliasName){
		this.aliasName = aliasName;
	}

	public String getAliasName(){
		return aliasName;
	}

	public void setObjFullName(String objFullName){
		this.objFullName = objFullName;
	}

	public String getObjFullName(){
		return objFullName;
	}

	public void setXmlFilePath(String xmlXmlFilePath){
		this.xmlXmlFilePath = xmlXmlFilePath;
	}

	public String getXmlFilePath(){
		return xmlXmlFilePath;
	}

	public void setBeanXmlFilePath(String beanXmlFilePath){
		this.beanXmlFilePath = beanXmlFilePath;
	}

	public String getBeanXmlFilePath(){
		return beanXmlFilePath;
	}
}
