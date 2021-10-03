package com.jlundho.kry.models.api;

import com.jlundho.kry.models.core.ServiceStatus;

import java.time.Instant;

public class ServiceIOv1 {

    private Long id;
    private String name;
    private String url;
    private ServiceStatus status;
    private Instant updatedAt;
    private Instant createdAt;

    public ServiceIOv1() {  // Hibernate requires no-argument constructor due to initialization with reflection
    }

    public ServiceIOv1(Long id, String name, String url, ServiceStatus status, Instant updatedAt, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
