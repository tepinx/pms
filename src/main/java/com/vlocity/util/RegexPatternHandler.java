package com.vlocity.util;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class RegexPatternHandler {

    private Map<String, String> regexpattern;

    public Map<String, String> getRegexpattern() {

        return regexpattern;

    }

    public void setRegexpattern(Map<String, String> regexpattern) {

        this.regexpattern = regexpattern;

    }

}