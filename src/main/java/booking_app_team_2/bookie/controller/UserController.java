package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.domain.UserRole;
import booking_app_team_2.bookie.dto.*;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.service.ReservationService;
import booking_app_team_2.bookie.service.UserService;
import jakarta.validation.Valid;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Setter
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {
    private UserService userService;
    private ReservationService reservationService;

    @Autowired
    public UserController(UserService userService, ReservationService reservationService) {

        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "User not found.");

        User user = userOptional.get();

        UserDTO userDTO = new UserDTO(user.getUsername(), user.getName(), user.getSurname(),
                user.getAddressOfResidence(), user.getTelephone());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/basic-info", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserBasicInfoDTO> updateUserBasicInfo(@PathVariable Long id,
                                                                @Valid @RequestBody UserBasicInfoDTO userBasicInfoDTO) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "User not found.");

        User user = userOptional.get();

        user.setName(userBasicInfoDTO.getName());
        user.setSurname(userBasicInfoDTO.getSurname());

        userService.save(user);

        return new ResponseEntity<>(userBasicInfoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/telephone", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserTelephoneDTO> updateUserContactInfo(
            @PathVariable Long id,
            @Valid @RequestBody UserTelephoneDTO userTelephoneDTO
    ) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "User not found.");

        User user = userOptional.get();

        user.setTelephone(userTelephoneDTO.getTelephone());

        userService.save(user);

        return new ResponseEntity<>(userTelephoneDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/address-of-residence", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserAddressDTO> updateUserAddressOfResidence(
            @PathVariable Long id,
            @Valid @RequestBody UserAddressDTO userAddressDTO
    ) {
        Optional<User> userOptional = userService.findOne(id);
        if (userOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "User not found.");

        User user = userOptional.get();

        user.setAddressOfResidence(userAddressDTO.getAddressOfResidence());

        userService.save(user);

        return new ResponseEntity<>(userAddressDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/password", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<UserPasswordDTO> updateUserPassword(@PathVariable Long id,
                                                              @Valid @RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(id, userPasswordDTO);

        return new ResponseEntity<>(userPasswordDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/block")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        User user = userService.findOne(id).orElseThrow(() -> new RuntimeException("User not found."));
        user.setBlocked(true);
        if(user.getRole().equals(UserRole.Guest)){
            List<Reservation> reservations = reservationService.findAll();
            for(Reservation reservation : reservations){
                if(reservation.getReservee().getId().equals(user.getId()) && reservation.getStatus().equals(ReservationStatus.Waiting)){
                    reservation.setStatus(ReservationStatus.Cancelled);
                    reservationService.save(reservation);
                }
            }
        }
        userService.save(user);

        return new ResponseEntity<>("User blocked.", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/unblock")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<String> unblockUser(@PathVariable Long id) {
        User user = userService.findOne(id).orElseThrow(() -> new RuntimeException("User not found."));
        user.setBlocked(false);
        userService.save(user);

        return new ResponseEntity<>("User unblocked.", HttpStatus.OK);
    }
}
