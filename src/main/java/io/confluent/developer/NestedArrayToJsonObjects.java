package io.confluent.developer;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NestedArrayToJsonObjects {

    private static final org.slf4j.Logger logger =
            LoggerFactory.getLogger(NestedArrayToJsonObjects.class);

    public static JsonNode[] toJsonObjects(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode[] jsonObjects = new JsonNode[jsonNode.get("array").size()];

        for (int i = 0; i < jsonNode.get("array").size(); i++) {
            try {
                jsonObjects[i] = mapper.readTree(jsonNode.get("array").get(i).toString());
                logger.info(jsonObjects[i].toString());
            } catch (JsonProcessingException e) {
                System.err.println(e);
            }
        }
        return jsonObjects;
    }

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(
                    "{ \"array\": [ { \"name\": \"John Doe\", \"age\": 30 }, { \"name\": \"Jane Doe\", \"age\": 25 } ] }");
            logger.info(jsonNode.toString());
            Map<String, Object> result =
                    mapper.convertValue(jsonNode, new TypeReference<HashMap<String, Object>>() {});
            logger.info(result.get("array").toString());
            JsonNode[] jsonObjects = toJsonObjects(jsonNode);



            for (JsonNode x : toJsonObjects(jsonNode)) {
                logger.info(x.toString());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
