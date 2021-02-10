package com.feign.error.demo.service;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.Try;
import static io.vavr.Predicates.instanceOf;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feign.error.demo.feign.FeignTestClient;
import com.feign.error.demo.model.ApiCallError;
import com.feign.error.demo.model.ApiCallErrorClient;
import com.feign.error.demo.model.ApiCallErrorServer;
import com.feign.error.demo.model.PageDTO;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import feign.FeignException.FeignServerException;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageService {

	private final @NonNull FeignTestClient feignTestClient;
	private final @NonNull ObjectMapper objectMapper;

	public Either<ApiCallError, PageDTO> getSuccess() {
		return processRequestError(() -> feignTestClient.processSuccessRecord());
	}

	public Either<ApiCallError, PageDTO> getFailedClient() {
		return processRequestError(() -> feignTestClient.processFailedRecordClient());
	}

	public Either<ApiCallError, PageDTO> getFailedServer() {
		return processRequestError(() -> feignTestClient.processFailedRecordServer());
	}

	private Either<ApiCallError, PageDTO> processRequestError(Supplier<PageDTO> feignTestClientSupplier) {
		return Try.ofSupplier(feignTestClientSupplier)
				.toEither()
				.mapLeft(this::processError);
	}

	private ApiCallError processError(Throwable throwable) {
		return Match(throwable).of(
				Case($(instanceOf(FeignClientException.class)), this::buildCallErrorClient)
			)
		.getOrElse(ApiCallError.builder().message("There has been an error").build());
	}

	private Option<ApiCallError> buildCallErrorClient(FeignClientException clientException) {
		return Option.ofOptional(clientException.responseBody())
				.map(response -> Try(() -> objectMapper.readValue(response.array(), ApiCallErrorClient.class)))
				.flatMap(Try::toOption)
				.map(clientError -> ApiCallError.builder().message(clientError.getReason()).build());
	}

}
