package no.dataingenioer.yamo.core.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import no.dataingenioer.yamo.core.annotations.Column;
import no.dataingenioer.yamo.core.annotations.Exclude;

/**
 *  Example abstract entity, so all entitys have an integer id
 */
public abstract class AbstractEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created")
    @Exclude
    private LocalDateTime created;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated")
    @Exclude
    private LocalDateTime updated;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    /**
     *
     * @return
     */
    public abstract int getId();

    /**
     *
     * @param id
     */
    public abstract void setId(int id);

}
