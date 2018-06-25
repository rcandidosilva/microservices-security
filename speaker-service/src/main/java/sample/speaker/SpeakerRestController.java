package sample.speaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speakers")
public class SpeakerRestController {
	
	@Autowired
	SpeakerRepository repo;

	@PreAuthorize("hasRole('USER')")
	@GetMapping
    public List<Speaker> getAll() {
        return repo.findAll();
    }

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Speaker> get(@PathVariable Long id) {
		Speaker speaker = repo.findOne(id);
		if (speaker != null) {
			return ResponseEntity.ok(speaker);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PreAuthorize("hasRole('MANAGER') && #oauth2.isUser()")
	@PostMapping
	public ResponseEntity<Speaker> create(@RequestBody Speaker speaker) {
        speaker = repo.save(speaker);
		if (speaker != null) {
			return ResponseEntity.ok(speaker);
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@PreAuthorize("hasRole('MANAGER') && #oauth2.isUser() && #oauth2.hasScope('write')")
	@PutMapping("/{id}")
	public ResponseEntity<Speaker> update(@PathVariable Long id, @RequestBody Speaker speaker) {
		if (speaker != null) {
            speaker.setId(id);
            speaker = repo.save(speaker);
			return ResponseEntity.ok(speaker);
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@PreAuthorize("hasRole('ADMIN') && #oauth2.hasScope('custom')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Speaker> delete(@PathVariable Long id) {
		Speaker speaker = repo.findOne(id);
		if (speaker != null) {
			repo.delete(speaker);
			return ResponseEntity.ok(speaker);
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/by/ids")
	public List<Speaker> getSpeakers(@RequestBody List<Long> speakerIds) {
		return repo.findAll(speakerIds);
	}

}