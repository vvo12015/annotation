package net.vrakin.reflection.execute;

import lombok.extern.slf4j.Slf4j;
import net.vrakin.reflection.annotation.Save;
import net.vrakin.reflection.annotation.SaveTo;
import net.vrakin.reflection.polygon.FieldForSave;
import net.vrakin.reflection.polygon.TextContainer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SaveTo(path = ".\\src\\main\\resources\\file.ser")
public class Serializer {

    public void serialize(TextContainer textContainer){
        String path = getPathFromSaveTo();
        Class clazz = textContainer.getClass();

        ArrayList<FieldForSave> fields = new ArrayList<>();

        try(FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            for(Field field : clazz.getDeclaredFields()){
                if (field.isAnnotationPresent(Save.class)){
                    log.info("filed {} added for saving", field.getName());
                    field.setAccessible(true);
                    FieldForSave fieldForSave = new FieldForSave(field.getName(), field.get(textContainer));
                    fields.add(fieldForSave);
                }
            }
            log.info("fields {} added", fields.size());
            objectOutputStream.writeObject(fields);
            log.info("fields saved");
        }catch (Exception e){
            log.error(e.toString());
        }
    }

    public TextContainer deserialize() throws IllegalAccessException {
        TextContainer textContainer = new TextContainer();

        String path = getPathFromSaveTo();
        log.info("path for open file {}", path);
        Class clazz = textContainer.getClass();

        ArrayList<FieldForSave> fieldsFromFiles = new ArrayList<>();

        try(FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            fieldsFromFiles = (ArrayList<FieldForSave>) objectInputStream.readObject();
            log.info("fields from file {}", fieldsFromFiles.size());
        }catch (Exception e){
            log.error(e.toString());
        }
        for(FieldForSave fieldForSave : fieldsFromFiles){
            try {
                log.info("field {}", fieldForSave.getName());
                Field field = textContainer.getClass().getDeclaredField(fieldForSave.getName());
                field.setAccessible(true);
                field.set(textContainer, fieldForSave.getValue());
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

        return textContainer;
    }

    private String getPathFromSaveTo(){
        Class<? extends Serializer> clazz = this.getClass();
        SaveTo saveTo = clazz.getAnnotation(SaveTo.class);
        String path = "";
        if(saveTo != null){
            return saveTo.path();
        }
        return path;
    }
}
