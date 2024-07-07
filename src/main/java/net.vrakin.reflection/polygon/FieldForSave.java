package net.vrakin.reflection.polygon;

import lombok.*;

import java.io.Serializable;
import java.lang.reflect.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class FieldForSave implements Serializable {
    private String name;
    private Object value;
}
