package asadyian.zahra.digitallibrary.domain.entities;

import asadyian.zahra.digitallibrary.controller.model.content.ContentResponse;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DL_CONTENT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class ContentEntity extends AuditModel{
    @Id
    @Column(name = "CONTENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "Name_", nullable = false)
    private String name;
    @Column(name = "BUY_DATE")
    private Date buyDate;
    @Column(name = "DESCRIPTION", length = 4001)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONTENT_DATA_ID")
    private FileEntity contentData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_LIBRARY_ID")
    private LibraryEntity library;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONTENT_TYPE_ID")
    private ContentTypeEntity contentType;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONTENT_ID")
    @Builder.Default
    private Set<ContentAttachmentEntity> attachments = new HashSet<>();

    public static ContentResponse convert2response(ContentEntity result) {
        return ContentResponse.builder()
                .id(result.id)
                .name(result.name)
                .buyDate(result.buyDate)
                .description(result.description)
                .build();
    }

    public void addAttachment(ContentAttachmentEntity attachment) {
        if (attachments == null) {
            attachments = new HashSet<>();
        }
        this.attachments.add(attachment);
    }

    public void removeAttachment(ContentAttachmentEntity attachment){
        this.attachments.remove(attachment);
    }
}
