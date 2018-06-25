package sample.conference;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Session implements Serializable {

	@Id
	String code;
	String title;
	String level;	
	@ElementCollection
	@JsonIgnore
	List<Long> speakerIds;
	
}