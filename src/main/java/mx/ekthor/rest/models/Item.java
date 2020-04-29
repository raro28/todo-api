package mx.ekthor.rest.models;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Item implements Serializable{
    private static final long serialVersionUID = -3169502877369484016L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;
}