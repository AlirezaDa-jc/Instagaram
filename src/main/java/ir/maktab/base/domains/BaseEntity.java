package ir.maktab.base.domains;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity<PK extends Serializable> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private PK id;

    @Version
    private Integer version;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
