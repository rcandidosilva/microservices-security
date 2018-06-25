package sample.conference;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpeakerDTO implements Serializable {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String title;
	private String company;	
	
}
