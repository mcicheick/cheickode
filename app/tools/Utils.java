package tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.ModelBase;
import play.libs.Json;

import java.util.List;

/**
 * Created by sissoko on 06/04/2016.
 */
public class Utils {

    /**
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends ModelBase> ArrayNode convert(List<T> list) {
        ArrayNode node = Json.newArray();
        for(T t : list) {
            node.add(t.toJson());
        }
        return node;
    }
}
