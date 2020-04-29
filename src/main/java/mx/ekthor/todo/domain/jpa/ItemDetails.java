package mx.ekthor.todo.domain.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class ItemDetails implements Serializable{

    private static final long serialVersionUID = 2594497187894741738L;

    @Column
    private String title;

    @Column
    private String content;
}
