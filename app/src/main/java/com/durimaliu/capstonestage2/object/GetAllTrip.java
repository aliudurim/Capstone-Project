package com.durimaliu.capstonestage2.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by macbook on 1/19/16.
 */
public class GetAllTrip {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("location_lang")
    @Expose
    private Double locationLang;
    @SerializedName("location_lat")
    @Expose
    private Double locationLat;
    @SerializedName("pickup_lang")
    @Expose
    private Double pickupLang;
    @SerializedName("pickup_lat")
    @Expose
    private Double pickupLat;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The locationLang
     */
    public Double getLocationLang() {
        return locationLang;
    }

    /**
     * @param locationLang The location_lang
     */
    public void setLocationLang(Double locationLang) {
        this.locationLang = locationLang;
    }

    /**
     * @return The locationLat
     */
    public Double getLocationLat() {
        return locationLat;
    }

    /**
     * @param locationLat The location_lat
     */
    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    /**
     * @return The pickupLang
     */
    public Double getPickupLang() {
        return pickupLang;
    }

    /**
     * @param pickupLang The pickup_lang
     */
    public void setPickupLang(Double pickupLang) {
        this.pickupLang = pickupLang;
    }

    /**
     * @return The pickupLat
     */
    public Double getPickupLat() {
        return pickupLat;
    }

    /**
     * @param pickupLat The pickup_lat
     */
    public void setPickupLat(Double pickupLat) {
        this.pickupLat = pickupLat;
    }

    /**
     * @return The createdBy
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The created_by
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
