package com.cgi.ordermanagement.resource.v1;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.model.dto.ScreenMessageDTO;
import com.cgi.ordermanagement.model.enums.Language;
import com.cgi.ordermanagement.service.system.SystemErrorService;
import com.cgi.ordermanagement.service.ScreenMessageService;
import com.cgi.ordermanagement.util.Rights;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@Slf4j
@RestController
@RequestMapping("/api/v1/screenMessages")
@Api(tags = "screenMessages")
public class ScreenMessageResource {

    @Autowired
    private ScreenMessageService screenMessageService;

    @Autowired
    private SystemErrorService systemErrorService;

    @RolesAllowed(value = {Rights.USER, Rights.ADMIN})
    @GetMapping("/language/{language}")
    @ApiOperation(value = "Get Screen message by language", produces = "application/json", response = ScreenMessageDTO.class)
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "", response = ScreenMessageDTO.class),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Access denied")

    })
    public ResponseEntity<?> findByLanguage(@ApiParam(name = "language", required = true, example = "EN") @PathVariable Language language) throws AppException {

        return new ResponseEntity<>(screenMessageService.findByLanguage(language), HttpStatus.OK);

    }

    @RolesAllowed(value = {Rights.USER, Rights.ADMIN})
    @GetMapping("/language/{language}/code/{code}")
    @ApiOperation(value = "Get Screen message by language and code", produces = "application/json", response = ScreenMessageDTO.class)
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "", response = ScreenMessageDTO.class),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Access denied")

    })
    public ResponseEntity<?> findByLanguage(@ApiParam(name = "language", required = true, example = "EN") @PathVariable Language language,
                                            @ApiParam(name = "code", required = true, example = "120") @PathVariable String code) throws Exception {

        return new ResponseEntity<>(screenMessageService.findByLanguageAndCode(language, code), HttpStatus.OK);

    }

}
