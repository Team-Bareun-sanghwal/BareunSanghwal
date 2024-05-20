package life.bareun.diary.global.auth.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GsonUtil {

    private static Gson instance;

    public static String toJson(Object src) {
        if (instance == null) {
            setGsonInstance();
        }

        return instance.toJson(src);
    }

    public static byte[] toJsonBytesUtf8(Object src) {
        if (instance == null) {
            setGsonInstance();
        }

        return toJson(src).getBytes(StandardCharsets.UTF_8);
    }

    private static void setGsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        instance = gsonBuilder
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .setPrettyPrinting()
            .create();
    }

    /**
     * Gson에서 LocalDateTime 필드를 serialize하기 위한 serializer
     */
    static class LocalDateSerializer implements JsonSerializer<LocalDate> {

        private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public JsonElement serialize(
            LocalDate localDate,
            Type srcType,
            JsonSerializationContext context
        ) {
            return new JsonPrimitive(formatter.format(localDate));
        }
    }

    static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(
            JsonElement json, Type typeOfT,
            JsonDeserializationContext context
        ) throws JsonParseException {
            return LocalDate.parse(
                json.getAsString(),
                DateTimeFormatter
                    .ofPattern("yyyy-MM-dd")
                    .withLocale(Locale.KOREA)
            );
        }
    }

    /**
     * Gson에서 LocalDateTime 필드를 serialize하기 위한 serializer
     */
    static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

        private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public JsonElement serialize(
            LocalDateTime localDateTime,
            Type srcType,
            JsonSerializationContext context
        ) {
            return new JsonPrimitive(formatter.format(localDateTime));
        }
    }

    static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context
        ) throws JsonParseException {
            return LocalDateTime.parse(
                json.getAsString(),
                DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withLocale(Locale.KOREA)
            );
        }
    }

}
