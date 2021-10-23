package esprit.fgsc.gateway.models;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
public class UserAccount{
    String id;
    Date joinDate;
    String email;
    String imageUrl;
    Boolean emailVerified;
}
