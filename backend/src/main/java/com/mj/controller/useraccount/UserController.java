package com.mj.controller.useraccount;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.mj.api.dto.in.UserAccountDto;
import com.mj.domain.UserAccount;
import com.mj.exception.UserAccountNotFoundException;
import com.mj.service.useraccount.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserAccountService service;


    @PostMapping(value = "/")
    public ResponseEntity<String> createUserAccount(@Valid @RequestBody UserAccountDto dto) {
        try {
            service.createUserAccount(dto);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/")
    public ResponseEntity<UserAccount> getUserByPesel(@RequestParam @Size(max = 11, min = 11, message = "Pesel has invalid size.") String pesel) {
        try {
            UserAccount userAccount = service.getUserByPesel(pesel);
            return new ResponseEntity<>(userAccount, HttpStatus.OK);
        } catch (UserAccountNotFoundException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
