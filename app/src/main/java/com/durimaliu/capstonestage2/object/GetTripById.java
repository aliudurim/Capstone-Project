package com.durimaliu.capstonestage2.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 1/23/16.
 */
public class GetTripById {

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
    private double locationLang;
    @SerializedName("location_lat")
    @Expose
    private double locationLat;
    @SerializedName("pickup_lang")
    @Expose
    private double pickupLang;
    @SerializedName("pickup_lat")
    @Expose
    private double pickupLat;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("joined")
    @Expose
    private boolean joined;
    @SerializedName("creator")
    @Expose
    private Creator creator = new Creator();
    @SerializedName("participants")
    @Expose
    private List<Participant> participants = new ArrayList<>();

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
    public double getLocationLang() {
        return locationLang;
    }

    /**
     * @param locationLang The location_lang
     */
    public void setLocationLang(double locationLang) {
        this.locationLang = locationLang;
    }

    /**
     * @return The locationLat
     */
    public double getLocationLat() {
        return locationLat;
    }

    /**
     * @param locationLat The location_lat
     */
    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    /**
     * @return The pickupLang
     */
    public double getPickupLang() {
        return pickupLang;
    }

    /**
     * @param pickupLang The pickup_lang
     */
    public void setPickupLang(double pickupLang) {
        this.pickupLang = pickupLang;
    }

    /**
     * @return The pickupLat
     */
    public double getPickupLat() {
        return pickupLat;
    }

    /**
     * @param pickupLat The pickup_lat
     */
    public void setPickupLat(double pickupLat) {
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

    /**
     * @return The joined
     */
    public boolean getJoined() {
        return joined;
    }

    /**
     * @param joined The joined
     */
    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    /**
     * @return The creator
     */
    public Creator getCreator() {

        return creator;
    }

    /**
     * @param creator The creator
     */
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    /**
     * @return The participants
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * @param participants The participants
     */
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

}