package ru.vtb.controller.api.custom;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@RequestMapping("/api/v1/aggregator")
public @interface ApiV1 {
}