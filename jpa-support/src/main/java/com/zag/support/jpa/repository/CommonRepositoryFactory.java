package com.zag.support.jpa.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;

public class CommonRepositoryFactory extends JpaRepositoryFactory {

    public CommonRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.repository.support.RepositoryFactorySupport#
     * getRepositoryBaseClass()
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return CommonJpaRepositoryBean.class;
    }
}
