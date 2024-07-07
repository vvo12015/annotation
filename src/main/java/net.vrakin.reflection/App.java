package net.vrakin.reflection;

import lombok.extern.slf4j.Slf4j;
import net.vrakin.reflection.annotation.ScanPackageName;
import net.vrakin.reflection.execute.FindAllClassFromPackage;
import net.vrakin.reflection.execute.SaverAction;
import net.vrakin.reflection.execute.Serializer;
import net.vrakin.reflection.execute.TestAnnotationAction;
import net.vrakin.reflection.polygon.TextContainer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Slf4j
@ScanPackageName("net.vrakin.reflection.polygon")
public class App
{
    public static void main( String[] args ) throws InvocationTargetException, IllegalAccessException, IOException {
        Class<App> clazz = App.class;
        ScanPackageName annotation = clazz.getAnnotation(ScanPackageName.class);
        String packageName = annotation.value();
        log.info(packageName);

        log.info("---Loaded of classes from {} package---", packageName);
        for (Class c :
        FindAllClassFromPackage.findAllClassesUsingGoogleGuice(packageName)){
            log.info(c.getName());
        }

        TestAnnotationAction action = new TestAnnotationAction();
        action.execute();

        TextContainer textContainer = new TextContainer("Successfully");
        SaverAction.saveObject(textContainer);

        TextContainer textContainerForSerializable = new TextContainer("text", "saveText", 10, "noSaved", 9);
        Serializer serializer = new Serializer();
        serializer.serialize(textContainerForSerializable);

        TextContainer textContainerFromDeSerializable = serializer.deserialize();

        log.info(textContainerFromDeSerializable.getTextSaved());
        log.info(textContainerFromDeSerializable.getText());
        log.info(Integer.toString(textContainerFromDeSerializable.getIntSaved()));
    }
}
