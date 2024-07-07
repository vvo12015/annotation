package net.vrakin.reflection.polygon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.vrakin.reflection.annotation.Save;
import net.vrakin.reflection.annotation.SaveTo;
import net.vrakin.reflection.annotation.Saver;
import net.vrakin.reflection.annotation.SerializeTo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@SaveTo(path = ".\\src\\main\\resources\\file.txt")
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TextContainer {

    private String text;

    @Save
    private String textSaved;

    @Save
    private int intSaved;

    private String textNotSaved;

    private int intNotSaved;

    public TextContainer(String text) {
        this.text = text;
    }

    @Saver
    public void save(String fileName) {
        try (OutputStreamWriter osw = new OutputStreamWriter(Files.newOutputStream(Paths.get(fileName)));
        BufferedWriter bw = new BufferedWriter(osw)) {
            bw.write(text);
            log.info("saved to {} is successfully", fileName);
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "TextContainer{" +
                "text='" + text + '\'' +
                '}';
    }
}
