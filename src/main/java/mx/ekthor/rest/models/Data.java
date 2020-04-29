package mx.ekthor.rest.models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Data<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 8673918334980526048L;

    @Getter
    @Builder.Default
    private List<T> data = new ArrayList<T>();
}