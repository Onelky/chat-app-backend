package onelky.chatapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	private User user;

	@BeforeEach
	public void setup() {
		user = User.builder()
				.email("test@gmail.com")
				.username("test")
				.provider(Provider.LOCAL)
				.password("testpassword")
				.build();

	}

	@Test
	public void whenSaveUser_returnsSavedUser(){

		User savedUser =  userRepository.save(user);
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void whenFindByUsername_returnsOneUser(){

		User savedUser =  userRepository.save(user);
		User user = userRepository.findByUsername(savedUser.getUsername());
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("test");
	}

}
