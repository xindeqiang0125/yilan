package com.xindq.yilan.domain;

import java.io.Serializable;

public class Item implements Serializable{
    private Integer id;
    private String itemName;
    private Boolean active;
    private String accessPath;
    private String notes;
    private String type;
    private Double min;
    private Double max;
    private Double normal;
    private String unit;
    private String group;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getNormal() {
        return normal;
    }

    public void setNormal(Double normal) {
        this.normal = normal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", active=" + active +
                ", accessPath='" + accessPath + '\'' +
                ", notes='" + notes + '\'' +
                ", type='" + type + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", normal=" + normal +
                ", unit='" + unit + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
