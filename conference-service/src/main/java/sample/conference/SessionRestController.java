package sample.conference;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
public class SessionRestController {
	
	@Autowired
	SessionRepository repo;
	
	@Autowired
	SpeakerClient speakerClient;

	@PreAuthorize("hasRole('USER')")
	@GetMapping
	public ResponseEntity<List<Session>> getAll() {
	    return ResponseEntity.ok(repo.findAll());
    }

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/{code}")
	public ResponseEntity<SessionDTO> get(@PathVariable String code) {
		Session session = repo.findOne(code);
		if (session != null) {
            List<SpeakerDTO> speakers = speakerClient.getSpeakers(session.getSpeakerIds());
            return ResponseEntity.ok(SessionDTO.builder()
                    .code(session.getCode())
                    .title(session.getTitle())
                    .level(session.getLevel())
                    .speakers(speakers)
                    .build());
        } else {
		    return ResponseEntity.notFound().build();
        }
	}

	@PreAuthorize("hasRole('MANAGER') && #oauth2.isUser()")
    @PostMapping
    public ResponseEntity<Session> create(@RequestBody Session session) {
        session = repo.save(session);
        if (session != null) {
            return ResponseEntity.ok(session);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PreAuthorize("hasRole('MANAGER') && #oauth2.isUser() && #oauth2.hasScope('write')")
    @PutMapping("/{code}")
    public ResponseEntity<Session> update(@PathVariable String code, @RequestBody Session session) {
        if (session != null) {
            session.setCode(code);
            session = repo.save(session);
            return ResponseEntity.ok(session);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN') && #oauth2.hasScope('delete')")
    @DeleteMapping("/{code}")
    public ResponseEntity<Session> delete(@PathVariable String code) {
        Session session = repo.findOne(code);
        if (session != null) {
            repo.delete(session);
            return ResponseEntity.ok(session);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
	
}