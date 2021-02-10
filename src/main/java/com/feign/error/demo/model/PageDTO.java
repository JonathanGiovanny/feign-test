package com.feign.error.demo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO implements Serializable {

	private static final long serialVersionUID = 8508947520725950167L;

	private Long id;
	private String name;

}
