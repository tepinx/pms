package com.vlocity.controller;

import com.vlocity.config.TokenProvider;
import com.vlocity.domain.*;
import com.vlocity.dto.LoginUserDto;
import com.vlocity.response.ApiResponse;
import com.vlocity.response.AuthToken;
import com.vlocity.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUserDto loginUser) throws AuthenticationException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userService.findByUsername(loginUser.getUsername());
        
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        
        return new ApiResponse<>(200, "success",new AuthToken(token, user));
    }

}
