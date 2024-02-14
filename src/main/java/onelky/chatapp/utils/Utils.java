package onelky.chatapp.utils;

import org.springframework.util.StringUtils;

public class Utils {

    public static String getToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }
}
