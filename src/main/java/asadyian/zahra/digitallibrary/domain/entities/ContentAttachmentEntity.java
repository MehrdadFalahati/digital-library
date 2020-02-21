package asadyian.zahra.digitallibrary.domain.entities;

import asadyian.zahra.digitallibrary.controller.model.contentAttachment.ContentAttachmentResponse;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DL_CONTENT_ATTACHMENT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class ContentAttachmentEntity extends AuditModel {
    @Id
    @Column(name = "CONTENT_ATTACHMENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NAME_")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "FK_ATTACHMENT_DATA_ID")
    private FileEntity attachmentData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONTENT_ID")
    private ContentEntity content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ATTACHMENT_TYPE_ID")
    private AttachmentTypeEntity attachmentType;

    public static ContentAttachmentResponse convert2response(ContentAttachmentEntity entity) {
        return ContentAttachmentResponse.builder()
                .id(entity.id)
                .name(entity.name)
                .attachmentType(new ContentAttachmentResponse.AttachmentType(entity.getAttachmentType().getId(), entity.getAttachmentType().getTitle()))
                .content( new ContentAttachmentResponse.Content(entity.getContent().getId(), entity.getContent().getName()))
                .fileId( entity.getAttachmentData().getId())
                .build();
    }
}
