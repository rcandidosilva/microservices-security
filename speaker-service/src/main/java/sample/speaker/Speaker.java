package sample.speaker;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Speaker implements Serializable {
	
	@Id @GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String title;
	private String company;	

}