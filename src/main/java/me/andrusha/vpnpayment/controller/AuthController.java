package me.andrusha.vpnpayment.controller;

import me.andrusha.vpnpayment.jwt_manager.JwtRequest;
import me.andrusha.vpnpayment.jwt_manager.JwtResponse;
import me.andrusha.vpnpayment.jwt_manager.RefreshJwtRequest;
import me.andrusha.vpnpayment.service.UserService;
import me.andrusha.vpnpayment.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import me.andrusha.vpnpayment.model.user.Role;
import me.andrusha.vpnpayment.model.user.User;
import me.andrusha.vpnpayment.service.RoleService;
import me.andrusha.vpnpayment.utils.Validator;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    Validator validator;

    @PostMapping("role")
    public ResponseEntity<?> login(@RequestBody Role role) {
        roleService.createRole(role);
        return ResponseEntity.ok("Okay");
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@Valid @RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@Valid @RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("registration")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validator.getErrorList(bindingResult);
        } else {
            if (!user.getPassword().equals(user.getPasswordConfirm())) {
                return ResponseEntity.ok("Пароли не совпадают");
            }
            if (!userService.saveUser(user)) {
                return ResponseEntity.ok("Пользователь с таким именем уже существует");
            }
            return ResponseEntity.ok("Зарегестрирован");
        }
    }

}
