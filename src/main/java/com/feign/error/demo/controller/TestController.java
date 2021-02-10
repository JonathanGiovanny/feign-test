package com.feign.error.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feign.error.demo.model.ApiCallError;
import com.feign.error.demo.model.PageDTO;
import com.feign.error.demo.service.PageService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

	private final @NonNull PageService pageService;

	@GetMapping("success")
	public PageDTO getSuccess() {
		return pageService.getSuccess().get();
	}

	@GetMapping("failed/client")
	public ApiCallError getFailedClient() {
		return pageService.getFailedClient().getLeft();
	}

	@GetMapping("failed/server")
	public ApiCallError getFailedServer() {
		return pageService.getFailedServer().getLeft();
	}

}
