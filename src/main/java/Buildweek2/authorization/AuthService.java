package Buildweek2.authorization;

import Buildweek2.client.BusinessName;
import Buildweek2.client.Client;
import Buildweek2.client.ClientRepository;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.exceptions.BadRequestException;
import Buildweek2.exceptions.UnauthorizedException;
import Buildweek2.security.JWTTools;
import Buildweek2.user.User;
import Buildweek2.user.UserRepository;
import Buildweek2.user.UserRole;
import Buildweek2.user.UserService;
import Buildweek2.user.payloads.RoleUpdateDTO;
import Buildweek2.user.payloads.UserDTO;
import Buildweek2.user.payloads.UserLoginDTO;
import Buildweek2.user.payloads.UserUpdateInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

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
        System.out.println(user.getEmail());
        System.out.println(bcrypt.matches(body.password(), user.getPassword()));
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
        User user;
        if (!body.username().isEmpty()) {
            user = User.builder().name(body.name()).email(body.email()).surname(body.surname()).password(bcrypt.encode(body.password())).username(body.username()).build();
        } else {
            user = User.builder().name(body.name()).email(body.email()).surname(body.surname()).password(bcrypt.encode(body.password())).username(body.name()).build();
        }
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
        found.setRole(UserRole.valueOf(body.role()));
        return userRepository.save(found);
    }

    public Client save(NewClientDTO body) {
        Client newClient;
        if (body.insertDate() != null && body.lastContractDate() != null) {
            newClient = Client.builder().companyName(body.companyName()).companyLogo(body.companyLogo()).contactPhone(body.contactPhone()).phone(body.phone())
                    .contactName(body.contactName()).contactSurname(body.contactSurname()).contactEmail(body.contactEmail()).annualTurnHover(body.annualTurnHover())
                    .pec(body.pec()).VATNumber(body.VATNumber()).insertDate(body.insertDate()).businessName(BusinessName.valueOf(body.businessName())).lastContractDate(body.lastContractDate()).build();
        } else if (body.insertDate() == null && body.lastContractDate() != null) {
            newClient = Client.builder().companyName(body.companyName()).companyLogo(body.companyLogo()).contactPhone(body.contactPhone()).phone(body.phone())
                    .contactName(body.contactName()).contactSurname(body.contactSurname()).contactEmail(body.contactEmail()).annualTurnHover(body.annualTurnHover())
                    .pec(body.pec()).VATNumber(body.VATNumber()).insertDate(new Date()).businessName(BusinessName.valueOf(body.businessName())).lastContractDate(body.lastContractDate()).build();
        } else if (body.insertDate() != null && body.lastContractDate() == null) {
            newClient = Client.builder().companyName(body.companyName()).companyLogo(body.companyLogo()).contactPhone(body.contactPhone()).phone(body.phone())
                    .contactName(body.contactName()).contactSurname(body.contactSurname()).contactEmail(body.contactEmail()).annualTurnHover(body.annualTurnHover())
                    .pec(body.pec()).VATNumber(body.VATNumber()).insertDate(body.insertDate()).businessName(BusinessName.valueOf(body.businessName())).lastContractDate(LocalDate.now()).build();
        } else {
            newClient = Client.builder().companyName(body.companyName()).companyLogo(body.companyLogo()).contactPhone(body.contactPhone()).phone(body.phone())
                    .contactName(body.contactName()).contactSurname(body.contactSurname()).contactEmail(body.contactEmail()).annualTurnHover(body.annualTurnHover())
                    .pec(body.pec()).VATNumber(body.VATNumber()).insertDate(new Date()).businessName(BusinessName.valueOf(body.businessName())).lastContractDate(LocalDate.now()).build();
        }
        return clientRepo.save(newClient);
    }
}
