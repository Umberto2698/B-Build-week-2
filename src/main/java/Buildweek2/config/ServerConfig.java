package Buildweek2.config;

import com.cloudinary.Cloudinary;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
public class ServerConfig {
    @Bean
    public Cloudinary cloudinaryUploader(@Value("${cloudinary.name}") String name,
                                         @Value("${cloudinary.apikey}") String apikey,
                                         @Value("${cloudinary.secret}") String secret
    ) {

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", name);
        config.put("api_key", apikey);
        config.put("api_secret", secret);

        return new Cloudinary(config);
    }

    @Bean
    public Faker faker() {
        return new Faker(Locale.ITALY);
    }
}
