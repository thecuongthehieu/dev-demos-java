package hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="myschema.family_member")
public class FamilyMember {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	@Column(name = "member_id", nullable = false, unique = true)
	private int memberId;

	@Column(name="member_name", nullable = false, length = 255)
	private String memberName;

	public int getMemberId() {
		return this.memberId;
	}

	public void setId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public String toString() {
		return "id=" + this.memberId + " " + "name=" + this.memberName;
	}
}
