package onelky.chatapp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CloudinaryConfig {
    @Value("${api.cloudinary.cloud_name}")
    private String CLOUD_NAME;

    @Value("${api.cloudinary.api_key}")
    private String API_KEY;

    @Value("${api.cloudinary.api_secret}")
    private String API_SECRET;
    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(ObjectUtils.asMap(
                "public_id", "group-chat",
                "overwrite", true,
                "cloud_name",CLOUD_NAME,
                "api_key",API_KEY,
                "api_secret",API_SECRET
        ));
    }
}