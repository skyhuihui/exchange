package com.zag.support.jpa.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;

@NoRepositoryBean
public class CommonJpaRepositoryBean<T, ID extends java.io.Serializable> extends SimpleJpaRepository<T, ID> implements CommonJpaRepository<T, ID> {

    private EntityManager entityManager;

    public CommonJpaRepositoryBean(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    public CommonJpaRepositoryBean(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void persist(T entity) {
        getEntityManager().persist(entity);
    }
}
