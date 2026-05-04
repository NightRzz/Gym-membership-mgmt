package sii.GymMembership.gym;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sii.GymMembership.common.exception.DuplicateGymNameException;
import sii.GymMembership.gym.dto.CreateGymRequest;

import java.util.List;

@Service
@Transactional
public class GymService {

	private final GymRepository gymRepository;

	public GymService(GymRepository gymRepository) {
		this.gymRepository = gymRepository;
	}

	public Gym createGym(CreateGymRequest request) {
		String name = request.name().trim();
		String address = request.address().trim();
		String phoneNumber = request.phoneNumber().trim();

		if (gymRepository.existsByNameIgnoreCase(name)) {
			throw new DuplicateGymNameException("Gym with name '" + name + "' already exists");
		}

		Gym gym = new Gym();
		gym.setName(name);
		gym.setAddress(address);
		gym.setPhoneNumber(phoneNumber);

		return gymRepository.save(gym);
	}

	@Transactional(readOnly = true)
	public List<Gym> getAllGyms() {
		return gymRepository.findAll();
	}
}