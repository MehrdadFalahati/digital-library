package asadyian.zahra.digitallibrary.domain.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DL_CONTENT_TYPE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class ContentTypeEntity extends AuditModel{
    @Id
    @Column(name = "CONTENT_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "CODE_", nullable = false)
    private String code;
    @Column(name = "TITLE", nullable = false)
    private String title;
}
