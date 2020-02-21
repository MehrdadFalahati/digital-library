package asadyian.zahra.digitallibrary.domain.entities.feedback;

import asadyian.zahra.digitallibrary.domain.entities.AuditModel;
import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DL_COMMENT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", doNotUseGetters=true, callSuper=false)
public class CommentEntity extends AuditModel {
	public static final String REF = "CommentEntity";
	public static final String PROP_ID = "id";

	public static final String PROP_BODY = "body";

    public static final String PROP_USER = "user";
    public static final String PROP_CONTENT = "content";
    public static final String PROP_REPLY = "reply";

	// primary key
	@Id
	@Column(name = "COMMENT_ID")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	// fields
	@Basic
	@Column(name = "COMMENT_BODY", nullable = false)
	@org.hibernate.annotations.Type(type = "org.hibernate.type.MaterializedClobType")
	private String body;

	// many to one
	@ManyToOne(targetEntity = UserEntity.class, optional = true, cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_USER_ID", nullable = false)
	private UserEntity user;
	@ManyToOne(targetEntity = ContentEntity.class, optional = true, cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONTENT_ID", nullable = false)
	private ContentEntity content;
	@ManyToOne(targetEntity = CommentEntity.class, optional = true, cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "REPLY_ID", nullable = true)
	private CommentEntity reply;
}
