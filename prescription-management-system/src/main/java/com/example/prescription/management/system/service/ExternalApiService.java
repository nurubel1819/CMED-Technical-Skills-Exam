package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.dto.ExternalDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalApiService {
    private final RestTemplate restTemplate;

    public List<ExternalDataDto> getAllPosts() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        ResponseEntity<List<ExternalDataDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ExternalDataDto>>() {}
        );

        return response.getBody();
    }
}
