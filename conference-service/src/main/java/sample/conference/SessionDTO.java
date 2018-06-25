package sample.conference;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDTO implements Serializable {

	String code;
	String title;
	String level;
	List<SpeakerDTO> speakers;
	
}