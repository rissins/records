package com.rissins.records.common;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class CommonFixtures {

    public static final Long ID = 0L;
    public static final String TITLE = "테스트제목";
    public static final String CONTENT = "테스트내용";
    public static final String USER_ID = "testId";
    public static final String INCORRECT_USER_ID = "incorrectTestId";
    public static final String USER_PASSWORD = "testPassword";
    public static final String INCORRECT_USER_PASSWORD = "incorrectTestPassword";
    public static final String TEXT_COLOR = "white";
    public static final String BACKGROUND_COLOR = "#02343f";
    public static final Boolean ALL_DAY = true;

    public static final String UPDATE_TITLE = "업데이트테스트제목";
    public static final String UPDATE_CONTENT = "업데이트테스트내용";
    public static final String FAIL_USER_PASSWORD = "failPassword";
    public static final MultipartFile IMAGE = new MockMultipartFile("image", "image".getBytes());

    public static MultipartFile getIMAGE() throws IOException {
        byte[] data = new byte[]{1, 2, 3, 4};
        InputStream stream = new ByteArrayInputStream(data);
        return new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", stream);
    }
}
