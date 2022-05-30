package com.rissins.records;

import org.springframework.web.multipart.MultipartFile;

import static com.rissins.records.common.CommonFixtures.IMAGE;

public class S3Fixtures {

    public MultipartFile getImage() {
        return IMAGE;
    }
}
