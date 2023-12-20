package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.dto.*;
import booking_app_team_2.bookie.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.Optional;

@Setter
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User user = userOptional.get();

        UserDTO userDTO = new UserDTO(user.getUsername(), user.getName(), user.getSurname(),
                user.getAddressOfResidence(), user.getTelephone());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/basic-info", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserBasicInfoDTO> updateUserBasicInfo(@PathVariable Long id,
                                                                @RequestBody UserBasicInfoDTO userBasicInfoDTO) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User user = userOptional.get();

        user.setName(userBasicInfoDTO.getName());
        user.setSurname(userBasicInfoDTO.getSurname());

        userService.save(user);

        return new ResponseEntity<>(userBasicInfoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/telephone", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserTelephoneDTO> updateUserContactInfo(@PathVariable Long id,
                                                                    @RequestBody UserTelephoneDTO userTelephoneDTO) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User user = userOptional.get();

        user.setTelephone(userTelephoneDTO.getTelephone());

        userService.save(user);

        return new ResponseEntity<>(userTelephoneDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/addressOfResidence", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserAddressDTO> updateUserAddressOfResidence(@PathVariable Long id,
                                                                     @RequestBody UserAddressDTO userAddressDTO) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User user = userOptional.get();

        user.setAddressOfResidence(userAddressDTO.getAddressOfResidence());

        userService.save(user);

        return new ResponseEntity<>(userAddressDTO, HttpStatus.OK);
    }


    @PutMapping(value = "/{id}/password", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserPasswordDTO> updateUserPassword(@PathVariable Long id,
                                                             @RequestBody UserPasswordDTO userPasswordDTO) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User user = userOptional.get();

        if (!userService.isCorrectPassword(userPasswordDTO, user))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        user.setPassword(userPasswordDTO.getNewPassword());

        userService.save(user);

        return new ResponseEntity<>(userPasswordDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
