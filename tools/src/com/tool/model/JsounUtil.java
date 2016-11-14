package com.tool.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;


public class JsounUtil {

	static ObjectMapper mapper = new ObjectMapper();
	 private static final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
	 private static final Map<String, JsonSchema> schemas = new HashMap<String, JsonSchema>();
	public static void json2String(String json)  throws JsonParseException, JsonMappingException, IOException{
//		JSONObject jsObj = JSONObject.parseObject(json);
		JsonNode jsonNode = mapper.readTree(json);
		Iterator<String> keys = jsonNode.fieldNames();
		
		JSONObject obj = null;
		while(keys.hasNext()){    
            String fieldName = keys.next();
            String value = jsonNode.path(fieldName).toString();
            try {
            	obj = JSONObject.parseObject(value);
            	if(obj != null){
            		json2String(json);
            	}
            	 System.out.println(fieldName + ": " + value); 
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
        }  
        //JsonNode ----> JSON  
//        System.out.println(mapper.writeValueAsString(jsonNode));
	
		
	}
	
	
	

	
	
	public static void json2String(String module, String resource, String method, String jsonStr)  throws Exception{
		JsonSchema scheam = getJsonSchema(module, resource, method);
		JsonNode jsonNode = JsonLoader.fromString(jsonStr);
		ProcessingReport report = scheam.validate(jsonNode);
		if(report.isSuccess()){
			return;
		}
		
		Iterator<ProcessingMessage> iterator = report.iterator();
		while(iterator.hasNext()){
			ProcessingMessage msg = iterator.next();
			
		}
		
	}
	
	public static void main(String[] args){
		    
//		  json2String(json);
//		  String testJosn = "username: \"zhangsan\"";
//		  JsonNode jsonNode = mapper.readTree(testJosn);
//		  System.out.println("is jsonNode "+(mapper.readTree(testJosn) instanceof JsonNode));
		
		String json = "{\"username\":\"zhangsan\",\"性别\":\"男\",\"company\":{\"companyName\":\"中华\",\"address\":\"北京\"},\"cars\":[\"奔驰\",\"宝马\"]}";
		    long begin = System.currentTimeMillis();
			StringBuffer buffer = new StringBuffer();
			buffer.append(json).append(json);
			String buffstr = buffer.toString();
			long end  = System.currentTimeMillis();
			System.out.println("---->>> buff Str ["+buffstr+"],耗时["+(end - begin)+"]ms");
			begin = System.currentTimeMillis();
			
			
			LinkedList<String> strList = new LinkedList<String>();
			strList.add(json);
			strList.add(json);
			String outStr = strList.toString();
			end  = System.currentTimeMillis();
			System.out.println("---->>>>> list Str["+outStr+"],耗时["+(end - begin)+"]");
	}
	
	

	
	
	/**
     * 根据module，resource，method获取对应的json schema
     *
     * @param module 模块名
     * @param resource 资源名
     * @param method 请求方法（get,post,put,delete）
     * @return 返回json schema，用于校验
     * @throws Exception
     */
    private static JsonSchema getJsonSchema(String module, String resource, String method) throws Exception {
        String path = "/validate/json/" + module + "/" + resource + "_" + method + ".json";
        if (schemas.containsKey(path)) {  //检测内存中存在，直接返回，不用每次加载。
            return schemas.get(path);
        } else {
            JsonNode jsonSchema = JsonLoader.fromResource(path);
            JsonSchema schema = factory.getJsonSchema(jsonSchema);
            schemas.put(path, schema);
            return schema;
        }
    }
    
    
}
