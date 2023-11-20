package Buildweek2.authorization;

import Buildweek2.client.Client;
import Buildweek2.client.ClientRepository;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.exceptions.BadRequestException;
import Buildweek2.exceptions.UnauthorizedException;
import Buildweek2.security.JWTTools;
import Buildweek2.user.User;
import Buildweek2.user.UserRepository;
import Buildweek2.user.UserService;
import Buildweek2.user.payloads.RoleUpdateDTO;
import Buildweek2.user.payloads.UserDTO;
import Buildweek2.user.payloads.UserLoginDTO;
import Buildweek2.user.payloads.UserUpdateInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private ClientRepository clientRepo;

    public String authenticateUser(UserLoginDTO body) {
        User user = userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Email or password invalid.");
        }
    }

    public User save(UserDTO body) {
        userRepository.findByEmail(body.email()).ifPresent(a -> {
            throw new BadRequestException("The email" + a.getEmail() + " is alredy used.");
        });
        User user = User.builder().name(body.name()).email(body.email()).surname(body.surname()).password(bcrypt.encode(body.password())).role(body.role()).build();
        return userRepository.save(user);
    }

    public User update(long id, UserUpdateInfoDTO body) {
        User found = userService.getById(id);
        if (!body.email().isEmpty()) {
            found.setEmail(body.email());
        }
        if (!body.name().isEmpty()) {
            found.setName(body.name());
        }
        if (!body.surname().isEmpty()) {
            found.setSurname(body.surname());
        }
        if (!body.password().isEmpty()) {
            found.setPassword(bcrypt.encode(body.password()));
        }
        return userRepository.save(found);
    }

    public User updateRole(long id, RoleUpdateDTO body) {
        User found = userService.getById(id);
        found.setRole(body.role());
        return userRepository.save(found);
    }
    public Client save(NewClientDTO body){
        Client newClient = new Client();
        newClient.setBusinessName(body.businessName());
        newClient.setAnnualTurnHover(body.annualTurnHover());
        newClient.setContactName(body.contactName());
        newClient.setContactEmail(body.contactEmail());
        newClient.setContactSurname(body.contactSurname());
        newClient.setContactPhone(body.contactPhone());
        newClient.setEmail(body.email());
        newClient.setPec(body.pec());
        newClient.setPhone(body.phone());
        newClient.setVATNumber(body.VATNumber());
        clientRepo.save(newClient);
        return newClient;
    }
}
