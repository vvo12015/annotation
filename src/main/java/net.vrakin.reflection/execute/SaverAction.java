package net.vrakin.reflection.execute;

import lombok.extern.slf4j.Slf4j;
import net.vrakin.reflection.annotation.SaveTo;
import net.vrakin.reflection.annotation.Saver;
import net.vrakin.reflection.polygon.TextContainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class SaverAction {

    public static void saveObject(TextContainer textContainer) throws InvocationTargetException, IllegalAccessException {
        Class<? extends TextContainer> clazz = textContainer.getClass();

        SaveTo saveTo = clazz.getAnnotation(SaveTo.class);

        if (saveTo != null) {
            String fileName = saveTo.path();
            log.info("Saving {}...", fileName);
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Saver.class)){
                    method.invoke(textContainer, fileName);
                }
            }
        }
    }
}
