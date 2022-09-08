package com.rodrigo.foodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {

    RecoveredPhoto recovered(String fileName);

    void storage(NewPhoto newPhoto);

    void remove(String fileName);

    default String createFileName(String name){
        return UUID.randomUUID() + "_" + name;
    }

    default void replace(String fileNameExist, NewPhoto newPhoto){
        this.storage(newPhoto);
        if(fileNameExist != null){
            this.remove(fileNameExist);
        }
    }

    @Builder
    @Getter
    class NewPhoto {
        private String fileName;
        private String contentType;
        private InputStream inputStream;
    }

    @Builder
    @Getter
    class RecoveredPhoto {

        private InputStream inputStream;
        private String url;

        public boolean hasUrl() {
            return url != null;
        }

        public boolean hasInputStream() {
            return inputStream != null;
        }

    }
}
