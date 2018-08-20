package com.example.data.utils;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @Author:HeZhengXing
 * @Descripton:
 * @Date: Created in 9:01 2018/7/27
 * @Modify By:
 */
@Service
@Lazy(false)
public class IdGen {
    private static SecureRandom random = new SecureRandom();

    public IdGen() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static long randomLong() {
        return Math.abs(random.nextLong());
    }
}

