package JsonCreate;

import com.google.gson.JsonObject;

/**
 * Iterface containing an overridable empty method which takes a JsonObject as parameter
 */

public interface ResultList {
    void update(JsonObject json);
}
