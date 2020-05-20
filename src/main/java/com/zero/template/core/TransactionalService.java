package com.zero.template.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionalService {

}
