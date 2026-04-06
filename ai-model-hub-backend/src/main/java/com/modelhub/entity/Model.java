package com.modelhub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "models")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String modelId;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(name = "context_length")
    private Integer contextLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "model_categories",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "model_capabilities",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "capability_id")
    )
    @Builder.Default
    private Set<Capability> capabilities = new HashSet<>();

    @Embedded
    @Builder.Default
    private ModelPricing pricing = new ModelPricing();

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "popularity_score")
    private Double popularityScore;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void addCapability(Capability capability) {
        this.capabilities.add(capability);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    public void removeCapability(Capability capability) {
        this.capabilities.remove(capability);
    }

}
