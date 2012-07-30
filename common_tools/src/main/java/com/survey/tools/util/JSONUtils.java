package com.survey.tools.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;

public class JSONUtils {
	
	private static ObjectMapper om = new ObjectMapper();
	private static ObjectMapper ignoreUnknownPropertiesObjectMapper = new ObjectMapper();
	static{
		ignoreUnknownPropertiesObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		
		SerializationConfig serializationConfig = ignoreUnknownPropertiesObjectMapper.copySerializationConfig();
		serializationConfig.setSerializationInclusion(Inclusion.NON_NULL);
		ignoreUnknownPropertiesObjectMapper.setSerializationConfig(serializationConfig);
	}
	
	public static <T> String toJSONwithOutNullProp(T object) {
		try {
			return ignoreUnknownPropertiesObjectMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
	}
	
	public static <T> String toJSON(T object) {
		ObjectMapper mapper = getObjectMapper(false);
		try {
			String jsonStr = mapper.writeValueAsString(object);
			return jsonStr;
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
	}
	
	
	/**
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJSON(String jsonString, Class<T> clazz) {
		
		ObjectMapper mapper = getObjectMapper();
		T object = null;
		try {
			object = mapper.readValue(jsonString, clazz);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
		return object;
	}

	
	public static <T> T fromJSON(String jsonString, Class<T> clazz,boolean isIgnoreUnknownProperties) {
		ObjectMapper mapper = getObjectMapper(isIgnoreUnknownProperties);
		T object = null;
		try {
			object = mapper.readValue(jsonString, clazz);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
		return object;
	}
	
	public static <T> T fromJSON(String jsonString, TypeReference<T> typeReference, boolean isIgnoreUnknownProperties) {
		ObjectMapper mapper = getObjectMapper(isIgnoreUnknownProperties);
		T object = null;
		try {
			object = (T) mapper.readValue(jsonString, typeReference);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
		return object;
	}
	
	public static <T> List<T> fromJSONToList(String jsonString, Class<T> clazz) {
		return fromJSONToList(jsonString,clazz,false);
	}
	
	
	
	public static <T> List<T> fromJSONToList(String jsonString, Class<T> clazz,boolean isIgnoreUnknownProperties) {
		
		ObjectMapper mapper = getObjectMapper();
		try {
			 JsonNode  rootNode = mapper.readTree(jsonString);
			 Iterator<JsonNode> it=  rootNode.getElements();
			 List<T> list =  new ArrayList<T>();
			 while(it.hasNext()){
				 T t = fromJSON(it.next().toString(),clazz);  
				 list.add(t);
			 }
			 return list;
		}catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
	}
	public static ObjectMapper getObjectMapper() {
		return  getObjectMapper(true) ;
	}
	
	public static ObjectMapper getObjectMapper(boolean isIgnoreUnknownProperties) {
		return isIgnoreUnknownProperties   ?  ignoreUnknownPropertiesObjectMapper : om;
	}

}
