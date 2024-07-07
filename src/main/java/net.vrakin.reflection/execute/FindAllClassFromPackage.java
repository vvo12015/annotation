package net.vrakin.reflection.execute;

import com.google.common.reflect.ClassPath;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class FindAllClassFromPackage {
    public static Set<Class> findAllClasses(String packageName) {
        try(InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return reader.lines()
                    .filter(l->l.endsWith(".class"))
                    .map(
                    l->{
                        try{
                            return Class.forName(packageName + "." + l);
                        }catch(ClassNotFoundException e){
                            log.error(e.toString());
                            throw new RuntimeException(e);
                        }
                    }
            ).collect(Collectors.toSet());
        }catch(IOException e){
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    public static Set<Class> findAllClassesUsingGoogleGuice(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName()
                        .equalsIgnoreCase(packageName))
                .map(ClassPath.ClassInfo::load)
                .collect(Collectors.toSet());
    }

}
