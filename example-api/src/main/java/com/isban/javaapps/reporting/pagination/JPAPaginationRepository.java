package com.isban.javaapps.reporting.pagination;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JPAPaginationRepository<T, I extends Serializable> extends JpaRepository<T, I>,
        JpaSpecificationExecutor<T> {

}
