package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.dto.ExternalDataDto;
import com.example.prescription.management.system.model.dto.ExternalPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate;

    public ExternalPageDto getPosts(int page, int size) {

        String url = "https://jsonplaceholder.typicode.com/posts?_page={page}&_limit={size}";

        ResponseEntity<ExternalDataDto[]> response =
                restTemplate.getForEntity(url, ExternalDataDto[].class, page, size);

        int totalCount = Integer.parseInt(
                response.getHeaders().getFirst("X-Total-Count"));
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return new ExternalPageDto(
                Arrays.asList(response.getBody()),
                page,
                totalPages);
    }




}

