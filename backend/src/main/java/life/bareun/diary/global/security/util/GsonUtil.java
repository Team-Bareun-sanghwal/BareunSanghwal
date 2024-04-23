package life.bareun.diary.global.security.util;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GsonUtil {
    private static Gson instance;

    public static String toJson(Object src) {
        if(instance == null) {
            instance = new Gson();
        }

        return instance.toJson(src);
    }

    public static byte[] toJsonBytesUtf8(Object src) {
        if(instance == null) {
            instance = new Gson();
        }

        return toJson(src).getBytes(StandardCharsets.UTF_8);
    }
}
