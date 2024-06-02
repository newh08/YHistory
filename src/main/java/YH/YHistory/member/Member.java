package YH.YHistory.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Member {

    public Member(MemberDTO memberDTO) {
        this.userId = memberDTO.getUserId();
        this.password = memberDTO.getPassword();
        this.name = memberDTO.getName();
        this.email = memberDTO.getEmail();
    }

    public Member() {
    }

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;
    private String join_date;

    public void setJoinDate() {
        join_date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
