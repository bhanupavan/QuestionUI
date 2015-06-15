package com.mobolt.selenium;

public class TempDTO {

  private String value;
  private String key;
  private String category;
  private String label;
  private String data_label;
  private Object options;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getData_label() {
    return data_label;
  }

  public void setData_label(String data_label) {
    this.data_label = data_label;
  }

  public Object getOptions() {
    return options;
  }

  public void setOptions(Object options) {
    this.options = options;
  }

  @Override
  public String toString() {
    return "TempDTO [value=" + value + ", key=" + key + ", category="
        + category + ", label=" + label + ", data_label=" + data_label
        + ", options=" + options + "]";
  }

}
