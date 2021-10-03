package com.jlundho.kry.models.db;

import com.jlundho.kry.models.core.ServiceStatus;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="url")
    private String url;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    @Column(name = "updated_at")
    @Generated(GenerationTime.ALWAYS)
    private Instant updatedAt;

    @Column(name = "created_at")
    @Generated(GenerationTime.INSERT)
    private Instant createdAt;

    public Service() {  // Hibernate requires no-argument constructor due to initialization with reflection
    }

    public Service(Long id, String name, String url, ServiceStatus status) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.status = status;
    }

    public Service(Service service) {
        this.id = service.getId();
        this.name = service.getName();
        this.url = service.getUrl();
        this.status = service.getStatus();
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdated(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id)
            && Objects.equals(name, service.name)
            && Objects.equals(url, service.url)
            && Objects.equals(status, service.status)
            && Objects.equals(createdAt, service.createdAt)
            && Objects.equals(updatedAt, service.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
