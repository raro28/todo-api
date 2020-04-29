package mx.ekthor.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ItemEntity extends ItemDetails {
    private static final long serialVersionUID = -3169502877369484016L;

    @Builder
    public ItemEntity(String title, String description, String id){
        super(title, description);
        this.id = id;
    }

    @Getter
    @Setter
    private String id;
}