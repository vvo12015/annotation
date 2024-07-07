package net.vrakin.reflection.execute;

import net.vrakin.reflection.annotation.Test;
import net.vrakin.reflection.polygon.MethodClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestAnnotationAction {

    public void execute() throws InvocationTargetException, IllegalAccessException {
        MethodClass mc = new MethodClass();
        Class<? extends MethodClass> methodClassClass = mc.getClass();
        Method[] method = methodClassClass.getDeclaredMethods();
        for (Method m : method) {
            Test testAnnotation = m.getAnnotation(Test.class);
            if (testAnnotation != null) {
                int a = testAnnotation.a();
                int b = testAnnotation.b();
                m.invoke(mc, a, b);
            }
        }
    }
}
