package anc.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class ResizeUrlService {

    private static Integer shorterLength = 8;

    public String generateUrl() {
        return RandomStringUtils.random(shorterLength, true, true);
    }
}
