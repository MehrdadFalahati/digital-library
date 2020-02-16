package asadyian.zahra.digitallibrary.domain.entities;

import asadyian.zahra.digitallibrary.controller.model.attachmenttype.AttachmentTypeResponse;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DL_ATTACHMENT_TYPE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class AttachmentTypeEntity extends AuditModel {
    @Id
    @Column(name = "ATTACHMENT_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "CODE_", nullable = false)
    private String code;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONTENT_TYPE_ID")
    private ContentTypeEntity contentType;

    public static AttachmentTypeResponse convert2response(AttachmentTypeEntity entity) {
        return AttachmentTypeResponse.builder()
                .id(entity.id)
                .code(entity.code)
                .title(entity.title)
                .contentType(new AttachmentTypeResponse.ContentType(entity.contentType.getId(), entity.contentType.getTitle()))
                .build();
    }
}
