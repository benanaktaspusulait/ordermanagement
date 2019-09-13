package com.cgi.ordermanagement.resource.v1;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.model.dto.security.UserDTO;
import com.cgi.ordermanagement.model.security.User;
import com.cgi.ordermanagement.payload.request.SignUpRequestDTO;
import com.cgi.ordermanagement.payload.response.OrderApiResponse;
import com.cgi.ordermanagement.payload.response.UserIdentityAvailability;
import com.cgi.ordermanagement.payload.response.UserSummary;
import com.cgi.ordermanagement.repository.security.UserRepository;
import com.cgi.ordermanagement.security.CurrentUser;
import com.cgi.ordermanagement.security.UserPrincipal;
import com.cgi.ordermanagement.service.SecurityUtils;
import com.cgi.ordermanagement.service.UserService;
import com.cgi.ordermanagement.util.Rights;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Api(value = "User Controller Service", tags = "users", produces = "application/json", consumes = "application/json", protocols = "https")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserResource() {
    }
    @ApiOperation(value = "Register User ", produces = "application/json", response = OrderApiResponse.class)
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "", response = OrderApiResponse.class),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Access denied")

    })

    @PostMapping("/signup")
    public ResponseEntity<?> registerCompanyUser(@Valid @RequestBody SignUpRequestDTO signUpRequest) throws AppException, Exception {
        UserDTO userDTO = userService.registerUser(signUpRequest);
        OrderApiResponse apiResponse = new OrderApiResponse(true, "User registered successfully");
        apiResponse.setId(userDTO.getId());
        return ResponseEntity.ok().body(apiResponse);
    }




    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Get user summary", produces = "application/json", response = UserSummary.class)
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "", response = UserSummary.class),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Access denied")

    })
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) throws AppException {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail());
        return userSummary;
    }


    @ApiOperation(value = "Is email exist", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = UserIdentityAvailability.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Access denied")

    })
    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@ApiParam(name = "email", required = true, example = "admin@test.com")
                                                           @RequestParam(value = "email") String email) throws AppException {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }


    @ApiOperation(value = "Get current user principal", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = UserPrincipal.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Access denied")

    })

    @RolesAllowed(value = {Rights.USER, Rights.ADMIN})
    @GetMapping(value = "/account")
    public ResponseEntity<?> getAccount() throws AppException {
        User user = userRepository.findOneByEmailIgnoreCase(SecurityUtils.getCurrentLogin());
        return new ResponseEntity<>(UserPrincipal.create(user), HttpStatus.OK);
    }


}
