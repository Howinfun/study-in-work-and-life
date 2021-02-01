package com.hyf.facebook.demo.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * @author sunny
 */
@Slf4j
public class JsonUtil {

    private final static String LEFT_BRACKET = "[";
    private final static String RIGHT_BRACKET = "]";

    public static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(new SimpleModule()
                    .addSerializer(LocalDate.class, new LocalDateSerializer())
                    .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer())
            )
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);


    public static <T> String toJson(T obj) {
        String serialValue = null;
        try {
            serialValue = MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("class:{} instance serial to Json has error:{}", obj.toString(), e.getMessage());
        }
        return serialValue;
    }

    /**
     * Json串反序列化,注意在对象Bean中增加@JsonIgnoreProperties(ignoreUnknown = true)(默认为false,
     * 即要求Bean与返回结果的字段严格一致,否则无法反序列化,如果外部接口没有通知情况下增加字段会比较危险.)
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T fromStr(String json, Class<T> clz) {
        if (json == null) {
            return null;
        }
        T deSerialObj = null;
        try {
            deSerialObj = MAPPER.readValue(json, clz);
        } catch (IOException e) {
            log.error("json from string",
                    "Class: {} deserialize from Json: {} failed: {}", clz, json, e.getMessage());
        }
        return deSerialObj;
    }

    public static <T> T fromStr(String json, TypeReference<?> valueTypeRef) {
        if (json == null) {
            return null;
        }
        T deSerialObj = null;
        try {
            deSerialObj = (T) MAPPER.readValue(json, valueTypeRef);
        } catch (Exception ex) {
            log.error("TypeReference: {} deserialize from Json {} failed: {}", valueTypeRef, json, ex.getMessage(), ex);
        }
        return deSerialObj;
    }

    public static <T> T fromStr(String json, JavaType javaType) {
        if (json == null) {
            return null;
        }
        T deSerialObj = null;
        try {
            deSerialObj = MAPPER.readValue(json, javaType);
        } catch (Exception ex) {
            log.error("JavaType: {} deserialize from Json {} failed: {}", javaType, json, ex.getMessage(), ex);
        }
        return deSerialObj;
    }

    public static <T> T fromStrByPath(String json, String path, Class<T> clz) {
        if (path == null || path.length() == 0) {
            return fromStr(json, clz);
        }
        T deSerialObj = null;
        try {
            JsonNode jsonNode = MAPPER.readTree(json);
            String jsonContent = getStringByPath(jsonNode, path);
            if (jsonContent == null) {
                return null;
            }
            deSerialObj = MAPPER.readValue(jsonContent, clz);
        } catch (IOException e) {
            log.error("json from string",
                    "Class: {} deserialize from Json: {} failed: {}", clz, json, e.getMessage());
        }
        return deSerialObj;
    }

    public static <T> T fromStrByPath(String json, String path, TypeReference<?> valueTypeRef) {
        if (path == null || path.length() == 0) {
            return fromStr(json, valueTypeRef);
        }
        T deSerialObj = null;
        try {
            JsonNode jsonNode = MAPPER.readTree(json);
            String jsonContent = getStringByPath(jsonNode, path);
            if (jsonContent == null) {
                return null;
            }
            deSerialObj = (T) MAPPER.readValue(jsonContent, valueTypeRef);
        } catch (IOException e) {
            log.error("json from string",
                    "TypeReference: {} deserialize from Json: {} failed: {}", valueTypeRef, json, e.getMessage());
        }
        return deSerialObj;
    }

    private static String getStringByPath(JsonNode jsonNode, String path) {
        String[] nodeNames = path.split("\\.");
        JsonNode node = jsonNode;
        for (String nodeName : nodeNames) {
            node = getNode(node, nodeName);
            if (node == null) {
                return null;
            }
        }
        return node.toString();
    }

    private static JsonNode getNode(JsonNode jsonNode, String nodeName) {
        String key;
        Integer index = null;
        if (nodeName.contains(LEFT_BRACKET)) {
            key = nodeName.substring(0, nodeName.indexOf(LEFT_BRACKET));
            index = Integer.parseInt(nodeName.substring(nodeName.indexOf(LEFT_BRACKET) + 1, nodeName.indexOf(RIGHT_BRACKET)));
        } else {
            key = nodeName;
        }
        JsonNode result = jsonNode.get(key);
        if (result != null && index != null && JsonNodeType.ARRAY.equals(result.getNodeType())) {
            return result.get(index);
        }
        return result;
    }

    public static <T> T fromInputStream(InputStream inputStream, Class<T> clazz) throws IOException {
        if (inputStream == null) {
            return null;
        }

        return MAPPER.readValue(inputStream, clazz);
    }

    public static <T> T fromInputStream(InputStream inputStream, JavaType type) throws IOException {
        if (inputStream == null) {
            return null;
        }

        return MAPPER.readValue(inputStream, type);
    }

    public static <K, V> Map<K, V> getMapFromStr(String json, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = MAPPER.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        Map<K, V> deSerialList = null;
        try {
            deSerialList = MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            log.error("Collection of class:{} deSerial from json:{} has error:{}", keyClass, json, e.getMessage());
        }
        return deSerialList;
    }

    public static String textField(JsonNode node, String field) throws Exception {
        return textField(node, field, "");
    }

    public static String textField(JsonNode node, String field, String defaultValue) throws Exception {
        JsonNode jsonNode = node.get(field);
        if (jsonNode != null) {
            if (jsonNode.isNull()) {
                return defaultValue;
            }
            return jsonNode.asText(defaultValue);
        } else {
            log.debug("textField error,field: {} , error: {}", field);
            throw new IllegalArgumentException("not exist field :" + field);
        }
    }

    public static int intField(JsonNode node, String field, int defaultValue) throws Exception {
        JsonNode jsonNode = node.get(field);
        if (jsonNode != null) {
            if (jsonNode.isNull()) {
                return defaultValue;
            }
            return jsonNode.asInt(defaultValue);
        } else {
            log.error("intField error,field: {} , error: {}", field);
            throw new IllegalArgumentException("not exist field :" + field);
        }
    }

    public static long longField(JsonNode node, String field, long defaultValue) throws Exception {
        JsonNode jsonNode = node.get(field);
        if (jsonNode != null) {
            if (jsonNode.isNull()) {
                return defaultValue;
            }
            return jsonNode.asLong(defaultValue);
        } else {
            log.error("longField error,field: {} , error: {}", field);
            throw new IllegalArgumentException("not exist field :" + field);
        }
    }

    public static boolean booleanField(JsonNode node, String field, boolean defaultValue) throws Exception {
        JsonNode jsonNode = node.get(field);
        if (jsonNode != null) {
            if (jsonNode.isNull()) {
                return defaultValue;
            }
            return jsonNode.asBoolean(defaultValue);
        } else {
            log.error("booleanField error,field: {} , error: {}", field);
            throw new IllegalArgumentException("not exist field :" + field);
        }
    }

    public static <T> T fromNode(JsonNode node, Class<T> clazz) {
        try {
            return MAPPER.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            log.error("json from jsonNode",
                    "Class: {} deserialize from Json: {} failed: {}", clazz, node, e.getMessage());
        }
        return null;
    }

    public static JsonNode addElement(JsonNode node, String filed, String value) {
        ObjectNode objectNode = (ObjectNode) node;
        objectNode.put(filed, value);
        return node;
    }

    public static String addElement(String json, String filed, String value) {
        JsonNode node = readTree(json);
        ObjectNode objectNode = (ObjectNode) node;
        objectNode.put(filed, value);
        return toJson(node);
    }

    public static JsonNode readTree(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            log.error("generate JsonNode tree from Json:{} has error:{}", json, e.getMessage());

        }
        return null;
    }


    public static String getByField(String json, String field) {
        //field必须是顶层的元素
        JsonNode node = readTree(json);
        JsonNode target = node.get(field);
        if (target == null) {
            return "";
        }
        String value = "";
        JsonNodeType type = target.getNodeType();
        switch (type) {
            case BOOLEAN:
                value = target.booleanValue() + "";
                break;
            case NUMBER:
                value = target.numberValue() + "";
                break;
            case STRING:
                value = target.textValue();
                break;
            case OBJECT:
                value = target.toString();
                break;
            default:
                //如果不是这四个,调不动的
                log.error("get node value from Json:{} has error type {}", json, type.name());
                throw new IllegalArgumentException("not exist field :" + field);
        }
        return value;
    }

}
