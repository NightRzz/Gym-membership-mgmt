package sii.GymMembership.gym;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sii.GymMembership.gym.dto.CreateGymRequest;
import sii.GymMembership.gym.dto.GymResponse;

import java.util.List;

@RestController
@RequestMapping("/api/gyms")
public class GymController {

	private final GymService gymService;

	public GymController(GymService gymService) {
		this.gymService = gymService;
	}

	@PostMapping
	public ResponseEntity<GymResponse> createGym(@Valid @RequestBody CreateGymRequest request) {
		Gym gym = gymService.createGym(request);
		GymResponse response = new GymResponse(gym.getId(), gym.getName(), gym.getAddress(), gym.getPhoneNumber());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<GymResponse>> getAllGyms() {
		List<Gym> gyms = gymService.getAllGyms();
		List<GymResponse> responses = gyms.stream()
			.map(gym -> new GymResponse(gym.getId(), gym.getName(), gym.getAddress(), gym.getPhoneNumber()))
			.toList();
		return ResponseEntity.ok(responses);
	}
}