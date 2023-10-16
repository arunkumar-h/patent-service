package com.technovigil.patent.controller;

import com.technovigil.patent.model.Patent;
import com.technovigil.patent.model.request.PatentRequest;
import com.technovigil.patent.service.PatentService;
import com.technovigil.patent.util.CsvUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<byte[]> getFilteredPatents(@Valid @RequestBody PatentRequest requestBody) {
        try {

            System.out.println("Received api request body: " + requestBody);
            List<Patent> patents = patentService.getFilteredPatents(requestBody);

            byte[] csvBytes = CsvUtil.convertPatentsToCsv(patents);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "data.csv");
            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
