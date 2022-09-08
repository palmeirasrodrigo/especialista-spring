package com.rodrigo.foodapi.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("food.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private StorageType type = StorageType.LOCAL;

    public enum StorageType {
        LOCAL, S3
    }

    @Getter
    @Setter
    public class Local {
        private Path photoDirectory;
    }

    @Getter
    @Setter
    public class S3 {

        private String idAccessKey;
        private String idSecretAccessKey;
        private String bucket;
        private Regions region;
        private String photoDirectory;
    }
}
