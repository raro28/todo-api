package mx.ekthor.rest.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ItemBase implements Serializable{

    private static final long serialVersionUID = -7090373998008749282L;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;
}
