package com.technovigil.patent.controller;

import com.technovigil.patent.model.FilteredPatentDto;
import com.technovigil.patent.model.Patent;
import com.technovigil.patent.model.request.PatentRequest;
import com.technovigil.patent.model.response.PatentResponse;
import com.technovigil.patent.service.PatentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author kumarhar
 */

@RestController
@RequestMapping("/technovigil/patents/v1")
@Api(tags = {"Patent Service Operations Endpoints "})
public class PatentController {

    @Autowired
    private PatentService patentService;

    @ApiOperation(value = "Fetch Filtered Patents", response = ResponseEntity.class)
    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Patent>> getFilteredPatents(@Valid @RequestBody PatentRequest requestBody) {
        try {

            System.out.println("Received api request body: " + requestBody);
            List<Patent> patents = patentService.getFilteredPatents(requestBody);

            return ResponseEntity.status(HttpStatus.OK).body(patents);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
