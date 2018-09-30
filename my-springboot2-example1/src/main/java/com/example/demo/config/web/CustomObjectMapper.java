package com.example.demo.config.web;

import java.io.IOException;
import java.text.SimpleDateFormat;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SuppressWarnings("serial")
@Component
@Qualifier(value = "customObjectMapper")
public class CustomObjectMapper extends ObjectMapper {
	
	//private static Logger logger = LoggerFactory.getLogger(CustomObjectMapper.class);

	public CustomObjectMapper() {
		super();
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//允许不带引号
		configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//针对timestamp类型的格式化
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
	}
	
	@Override
	public String writeValueAsString(Object value) throws JsonProcessingException {
		return super.writeValueAsString(value);
	}
	
	@Override
	public void writeValue(JsonGenerator jgen, Object value) throws IOException, JsonGenerationException, JsonMappingException {
		super.writeValue(jgen, value);
	}
}