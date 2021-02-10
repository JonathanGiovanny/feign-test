package com.feign.error.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.feign.error.demo.model.PageDTO;

@FeignClient(value = "externalapi", url = "https://run.mocky.io/v3")
public interface FeignTestClient {

	// 400
	@RequestMapping(method = RequestMethod.GET, value = "4ddf14a8-f83a-4482-b05c-eb110445efe3")
	PageDTO processFailedRecordClient();

	// 500
	@RequestMapping(method = RequestMethod.GET, value = "8334be1a-de62-4ee1-9872-53afcb4e2b14")
	PageDTO processFailedRecordServer();

	// 200
	@RequestMapping(method = RequestMethod.GET, value = "9e773072-f904-4b83-839c-fc7a1ca13114")
	PageDTO processSuccessRecord();

}
