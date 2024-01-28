package com.itv.mobilestore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix="file")
public class FileStorageProperties {
    public String uploadDir;
    
}

