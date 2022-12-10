package ru.vtb.service.vaccination;

import org.springframework.web.multipart.MultipartFile;

public interface ICsvDataLoader {
    void uploadDataFromFile(MultipartFile file);
}
