package com.example.Connect.Controller;

import com.example.Connect.Exceptions.BadRequestException;
import com.example.Connect.Model.User;
import com.example.Connect.Service.UserService;
import com.example.Connect.Exceptions.ResourceNotFoundException;
import com.example.Connect.util.responses.Response;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "Connect To Angular", description = "a simple app to interface angular")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "User Found"),
            @ApiResponse(code = 401, message = "UnAuthorised Access"),
            @ApiResponse(code = 403, message = "Access Forbidden"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ApiOperation(value = "View a list of available users", response = List.class)
    @GetMapping("/users")
    public Response<List<User>> getUsers() {
        Response<List<User>> response = new Response();
        try{
            response.setStatus(HttpStatus.FOUND.value());
            response.setMessage(HttpStatus.FOUND.toString());
            response.setData(userService.getUsers());
        }
        catch (Exception ex){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Something is Not Right", ex
            );
        }
        return response;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added"),
            @ApiResponse(code = 401, message = "You are not authorised to add Users"),
            @ApiResponse(code = 404, message = "Route not Found")
    })
    @ApiOperation(value = "Add User")
    @PostMapping("/users")
    public Response<User> create(@Valid @RequestBody User user) {
        Response<User> response = new Response();
        try {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage(HttpStatus.CREATED.toString());
            response.setData(userService.addUser(user));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.SEE_OTHER, "Resource Already Exist", ex);
        }

        return response;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "User Found"),
            @ApiResponse(code = 401, message = "UnAuthorised Access"),
            @ApiResponse(code = 403, message = "Access Forbidden"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ApiOperation(value = "Get a user by Id")
    @GetMapping("/users/{id}")
    public Response< User > getEmployeeById(
            @ApiParam(value = "The id from which the user will be retrieved", required = true) @PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Response<User> response = new Response();
        response.setStatus(HttpStatus.FOUND.value());
        response.setMessage(HttpStatus.FOUND.toString());
        response.setData(userService.findUser(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id)));
        return response;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "User Found"),
            @ApiResponse(code = 401, message = "UnAuthorised Access"),
            @ApiResponse(code = 403, message = "Access Forbidden"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ApiOperation(value = "Update User Details by Id")
    @PutMapping("/users/{id}")
    public Response< User > updateUser(
            @ApiParam(value = "User Id to update user object", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "Update employee object", required = true) @Valid @RequestBody User userDetails) throws ResourceNotFoundException
    {   Response<User> response = new Response();
        User user = userService.findUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this Id :: " + id));

        user.setId(userDetails.getId());
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        final User updatedUser = userService.addUser(user);
        response.setStatus(HttpStatus.ACCEPTED.value());
        response.setMessage(HttpStatus.ACCEPTED.toString());
        response.setData(user);
        return response;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update Successful"),
            @ApiResponse(code = 401, message = "UnAuthorised Access"),
            @ApiResponse(code = 403, message = "Access Forbidden"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ApiOperation(value = "Delete an employee")
    @DeleteMapping("/users/{id}")
    public Response<User> deleteEmployee(
            @ApiParam(value = "Employee Id from which employee object will delete from database table", required = true) @PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Response<User> response = new Response();
        User employee = userService.findUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        userService.deleteUser(id);
        Map < String, Boolean > delresponse = new HashMap< >();
        delresponse.put("deleted", Boolean.TRUE);
        response.setMessage(delresponse.toString());
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

}
