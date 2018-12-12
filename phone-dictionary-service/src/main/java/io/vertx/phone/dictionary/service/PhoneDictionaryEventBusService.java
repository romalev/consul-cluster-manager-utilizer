package io.vertx.phone.dictionary.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Phone dictionary event bus based service implementation.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public class PhoneDictionaryEventBusService implements PhoneDictionaryService {

    private final static Logger log = LoggerFactory.getLogger(PhoneDictionaryEventBusService.class);

    private final static String USER_KEY = "user";
    private final static String PHONE_NUMBER_KEY = "phoneNumber";

    private final Map<String, String> inMemoryPhoneDictionary = new ConcurrentHashMap<>();

    @Override
    public void savePhoneRecord(JsonObject phoneRecord, Handler<AsyncResult<Void>> resultHandler) {
        String user = phoneRecord.getString(USER_KEY);
        if (Objects.isNull(user) || user.isEmpty()) {
            log.warn("Invalid json - no used defined.");
            resultHandler.handle(Future.failedFuture("No user defined"));
        }
        String userPhoneNumber = phoneRecord.getString(PHONE_NUMBER_KEY);
        if (Objects.isNull(userPhoneNumber) || userPhoneNumber.isEmpty()) {
            log.warn("Invalid json - no phone number defined.");
            resultHandler.handle(Future.failedFuture("No phone number defined"));
        }
        if (inMemoryPhoneDictionary.containsKey(user)) {
            log.warn("User: {} already exists in dictionary.", user);
            resultHandler.handle(Future.failedFuture("User: " + user + " already exists in dictionary."));
        }
        inMemoryPhoneDictionary.put(user, userPhoneNumber);
    }

    @Override
    public void getPhoneRecord(String user, Handler<AsyncResult<JsonObject>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(
                new JsonObject()
                        .put(USER_KEY, user)
                        .put(PHONE_NUMBER_KEY, inMemoryPhoneDictionary.get(user)))
        );
    }

    @Override
    public void getAllPhoneRecords(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
        final List<JsonObject> allRecords = new ArrayList<>();
        inMemoryPhoneDictionary.forEach((k, v) ->
                allRecords.add(new JsonObject()
                        .put(USER_KEY, k)
                        .put(PHONE_NUMBER_KEY, v)));
        log.debug("All phone records are fetched: {}", allRecords);
        resultHandler.handle(Future.succeededFuture(allRecords));
    }
}
