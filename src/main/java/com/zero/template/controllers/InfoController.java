package com.zero.template.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zero.template.core.OpenEndPoint;

import java.util.HashMap;
import java.util.Map;

@RestController
public class InfoController {

    @Value("${git.commit.id}")
    private String commitId;
    
    @OpenEndPoint
    @GetMapping("/info")
    public Map<String, String> getCommitId() {
        Map<String, String> result = new HashMap<>();
        result.put("Commit id", commitId);
        return result;
    }
}
