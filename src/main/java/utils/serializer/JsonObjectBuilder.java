package utils.serializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("rawtypes")
public class JsonObjectBuilder {

    HashMap<String, String> stringTypedEntries = new HashMap<>();
    HashMap<String, Integer> intTypedEntries = new HashMap<>();
    HashMap<String, Boolean> booleanTypedEntries = new HashMap<>();
    HashMap<String, Object> nullTypedEntries = new HashMap<>();
    HashMap<String, String> nestedObjectEntries = new HashMap<>();
    HashMap<String, String> nestedListEntries = new HashMap<>();

    HashSet<String> order = new HashSet<>();

    //region AddFunctions
    public JsonObjectBuilder addString(String key, String value) {
        if (value == null)
            addNull(key);
        else
            this.stringTypedEntries.put(key, value);

        order.add(key);
        return this;
    }

    public JsonObjectBuilder addNestedObject(String key, String value) {
        if (value == null)
            addNull(key);
        else
            this.nestedObjectEntries.put(key, value);

        order.add(key);
        return this;
    }

    public JsonObjectBuilder addNestedList(String key, String value) {
        if (value == null)
            addNull(key);
        else
            this.nestedListEntries.put(key, value);

        order.add(key);
        return this;
    }

    public JsonObjectBuilder addInt(String key, int value) {

        this.intTypedEntries.put(key, value);
        order.add(key);
        return this;
    }

    public JsonObjectBuilder addBoolean(String key, boolean value) {
        this.booleanTypedEntries.put(key, value);
        order.add(key);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public JsonObjectBuilder addNull(String key) {
        this.nullTypedEntries.put(key, null);
        order.add(key);
        return this;
    }


    //endregion AddFunctions

    private String jsonStringEntry(String key) {
        String value = stringTypedEntries.get(key);
        return '"' + key + '"' + ":" + '"' + value + '"';
    }

    private String jsonIntEntry(String key) {
        int value = intTypedEntries.get(key);
        return '"' + key + '"' + ":" + value;
    }

    private String jsonBooleanEntry(String key) {
        boolean value = booleanTypedEntries.get(key);
        return '"' + key + '"' + ":" + (value ? "true" : "false");
    }

    private String jsonNullEntry(String key) {
        return '"' + key + '"' + ":" + "null";
    }

    private String jsonObjectEntry(String key) {
        String value = nestedObjectEntries.get(key);
        return '"' + key + '"' + ":" + value;
    }

    private String jsonListEntry(String key) {
        return '"' + key + '"' + ":" + nestedListEntries.get(key);
    }

    public String buildObject() {
        StringBuilder output = new StringBuilder("{");
        List<String> orderList = new ArrayList<>(order);
        for (int i = orderList.size() - 1; i >= 0; i--) {
            String key = orderList.get(i);
            if (stringTypedEntries.containsKey(key)) {
                output.append(jsonStringEntry(key));
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (intTypedEntries.containsKey(key)) {
                output.append(jsonIntEntry(key));
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (booleanTypedEntries.containsKey(key)) {
                output.append(jsonBooleanEntry(key));
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (nestedObjectEntries.containsKey(key)) {
                output.append(jsonObjectEntry(key));
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (nestedListEntries.containsKey(key)) {
                output.append(jsonListEntry(key));
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (nullTypedEntries.containsKey(key)) {
                output.append(jsonNullEntry(key));
                if (i != 0)
                    output.append(',');
            }
        }
        output.append("}");
        return output.toString();
    }
}
