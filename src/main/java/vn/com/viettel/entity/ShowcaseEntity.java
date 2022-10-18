package vn.com.viettel.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "showcase")
public class ShowcaseEntity {

    @Id
    private Long id;

    private String title;

    private String description;

    private String img;

    private String detail;

    private Date createdDate;

    private Date updatedDate;

    private Long createdUser;

    private Long updatedUser;

    private boolean isDel;

}
