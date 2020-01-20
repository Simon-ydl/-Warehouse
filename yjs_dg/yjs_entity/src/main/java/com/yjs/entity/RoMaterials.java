package com.yjs.entity;


public class RoMaterials {

  private long id;
  private String materialName;
  private String materialForm;
  private String materialRequire;
  private String materialDownload;
  private String isSubmit;
  private long itemId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getMaterialName() {
    return materialName;
  }

  public void setMaterialName(String materialName) {
    this.materialName = materialName;
  }


  public String getMaterialForm() {
    return materialForm;
  }

  public void setMaterialForm(String materialForm) {
    this.materialForm = materialForm;
  }


  public String getMaterialRequire() {
    return materialRequire;
  }

  public void setMaterialRequire(String materialRequire) {
    this.materialRequire = materialRequire;
  }


  public String getMaterialDownload() {
    return materialDownload;
  }

  public void setMaterialDownload(String materialDownload) {
    this.materialDownload = materialDownload;
  }


  public String getIsSubmit() {
    return isSubmit;
  }

  public void setIsSubmit(String isSubmit) {
    this.isSubmit = isSubmit;
  }


  public long getItemId() {
    return itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
  }

}
