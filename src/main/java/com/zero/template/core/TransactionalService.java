package com.zero.template.core;

import java.lang.annotation.Inherited;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
@Inherited
// TODO: create a common annotation for service class
public @interface TransactionalService {

}
