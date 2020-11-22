package utils.serializer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class JsonListBuilder {
    int index = 0;

    //increment index
    void increment() {
        order.add(index);
        index++;
    }

    //content containers
    HashMap<Integer, String> stringTypedEntries = new HashMap<>();
    HashMap<Integer, Integer> intTypedEntries = new HashMap<>();
    HashMap<Integer, Boolean> booleanTypedEntries = new HashMap<>();
    HashMap<Integer, String> nestedObjectEntries = new HashMap<>();
    HashMap<Integer, String> nestedListEntries = new HashMap<>();

    HashSet<Integer> order = new HashSet<>();

    //region AddFunctions
    public JsonListBuilder addString(String value) {

        if (value == null)
            return this;
        this.stringTypedEntries.put(index, value);
        increment();
        return this;
    }

    public JsonListBuilder addNestedObject(String objectAsJson) {
        if (objectAsJson == null)
            return this;
        this.nestedObjectEntries.put(index, objectAsJson);
        increment();
        return this;
    }

    public JsonListBuilder addNestedList(String listAsJson) {
        if (listAsJson == null)
            return this;
        this.nestedListEntries.put(index, listAsJson);
        increment();
        return this;
    }

    public JsonListBuilder addInt(int value) {
        this.intTypedEntries.put(index, value);
        increment();
        return this;
    }
    public JsonListBuilder addBoolean(boolean value) {
        this.booleanTypedEntries.put(index, value);
        increment();
        return this;
    }


    //endregion AddFunctions


    public String buildListJson() {
        StringBuilder output = new StringBuilder("[");
        List<Integer> orderList = new ArrayList<Integer>(order);
        for (int i = orderList.size() - 1; i >= 0; i--) {
            Integer key = orderList.get(i);
            if (stringTypedEntries.containsKey(key)) {
                output.append('"').append(stringTypedEntries.get(key)).append('"');
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (intTypedEntries.containsKey(key)) {
                output.append(intTypedEntries.get(key));
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (booleanTypedEntries.containsKey(key)) {
                output.append(booleanTypedEntries.get(key));
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (nestedObjectEntries.containsKey(key)) {
                output.append(nestedObjectEntries.get(key));
                if (i != 0)
                    output.append(',');
                continue;
            }
            if (nestedListEntries.containsKey(key)) {
                output.append(nestedListEntries.get(key));
                if (i != 0)
                    output.append(',');
            }
        }
        output.append("]");
        return output.toString();
    }
}

