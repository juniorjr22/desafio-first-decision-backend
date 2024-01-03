package com.desafio.firstdecision.controller;

import com.desafio.firstdecision.dto.UserDTORequest;
import com.desafio.firstdecision.dto.UserDTOResponse;
import com.desafio.firstdecision.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    protected UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<UserDTOResponse>> getUsers(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(this.userService.getUsers(pageable));
    }


    @PostMapping
    @Operation(responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDTOResponse.class)))
    })
    public ResponseEntity<UserDTOResponse> saveUser(@RequestBody @Valid UserDTORequest user) {
        return new ResponseEntity<>(this.userService.saveUser(user), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @Operation(responses = {
            @ApiResponse(responseCode = "204", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTORequest user) {
        this.userService.updateUser(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(path = "/{id}")
    @Operation(responses = {
            @ApiResponse(responseCode = "204", description = "Deleted user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable @NotNull Long id) {
        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
