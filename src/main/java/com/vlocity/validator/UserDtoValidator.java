package com.vlocity.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vlocity.dto.UserDto;
import com.vlocity.repository.UserRepository;
import com.vlocity.util.RegexPatternHandler;

@Component
public class UserDtoValidator extends AbstractValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegexPatternHandler regexPatternHandler;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        
        UserDto userDto = (UserDto) target;

    }

}
