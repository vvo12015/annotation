package net.vrakin.reflection.polygon;

import lombok.extern.slf4j.Slf4j;
import net.vrakin.reflection.annotation.Test;

@Slf4j
public class MethodClass {

    @Test(a = 2, b = 5)
    public void printSum(int a, int b) {
        int sum = a + b;
        log.info(Integer.toString(sum));
    }

    public static int sum(int a, int b, int c) {
        return a + b + c;
    }

    public String getText(){
        return "Hello World";
    }
}
