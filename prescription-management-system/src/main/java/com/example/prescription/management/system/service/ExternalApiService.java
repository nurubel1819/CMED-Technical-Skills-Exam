package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.dto.ExternalDataDto;
import com.example.prescription.management.system.model.dto.ExternalPageDto;
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

    public ExternalPageDto getPosts(int page, int size) {
        String url = String.format(
                "https://jsonplaceholder.typicode.com/posts?_page=%d&_limit=%d",
                page, size);

        ResponseEntity<List<ExternalDataDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ExternalDataDto>>() {});

        // এই লাইন এখানে ব্যবহার করা ঠিক আছে
        String header = response.getHeaders().getFirst("X-Total-Count");
        int totalItems = header != null ? Integer.parseInt(header) : 100;

        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new ExternalPageDto(response.getBody(), page, totalPages);
    }



}

