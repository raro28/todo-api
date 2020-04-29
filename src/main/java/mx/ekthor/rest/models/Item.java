package mx.ekthor.rest.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class Item extends ItemBase {
    private static final long serialVersionUID = -3169502877369484016L;

    @Builder
    public Item(String title, String description, String id){
        super(title, description);
        this.id = id;
    }

    @Getter
    @Setter
    private String id;
}