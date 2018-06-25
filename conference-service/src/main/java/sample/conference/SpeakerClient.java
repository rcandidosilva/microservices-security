package sample.conference;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("speaker-service")
public interface SpeakerClient {

	@PostMapping("/speakers/by/ids")
	List<SpeakerDTO> getSpeakers(@RequestBody List<Long> speakerIds);
	
}