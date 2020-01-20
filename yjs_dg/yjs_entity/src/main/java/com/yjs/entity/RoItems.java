package com.yjs.entity;


public class RoItems {

  private long id;
  private String itemName;
  private String itemResult;
  private String auditDepartment;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }


  public String getItemResult() {
    return itemResult;
  }

  public void setItemResult(String itemResult) {
    this.itemResult = itemResult;
  }


  public String getAuditDepartment() {
    return auditDepartment;
  }

  public void setAuditDepartment(String auditDepartment) {
    this.auditDepartment = auditDepartment;
  }

}
