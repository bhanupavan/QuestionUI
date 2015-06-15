package com.mobolt.selenium;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class WebElementExtractor {

  private static final String       SRC_XPATH        = "//input[not(@type='hidden' or @type='submit' or @type='button')] | //textarea | //select";

  private static final List<String> RADIO_CHECK      = Arrays.asList("radio",
                                                         "checkbox");
  static List<TempDTO>              questionAttrlist = Lists.newLinkedList();

  public static void extract(WebDriver driver, String root) {
    if (!questionAttrlist.isEmpty()) {
      questionAttrlist.clear();
    }
    WebElement rootelem = driver.findElement(By.xpath(root));
    getHtmlElements(driver, rootelem);

  }

  public static List<TempDTO> getQuestionAttrlist() {
    return questionAttrlist;
  }

  public static void setQuestionAttrlist(
      List<TempDTO> questionAttrlist) {
    WebElementExtractor.questionAttrlist = questionAttrlist;
  }

  private static void getHtmlElements(WebDriver driver,
      WebElement rootelem) {
    List<WebElement> elems = rootelem.findElements(By.xpath(SRC_XPATH));

    for (WebElement elem : elems) {
      TempDTO questionAttrs = new TempDTO();
      Map<String, String> result = getKey(elem);
      String category = result.get("category");
      String attr = result.get("attr");
      String key = result.get("attr_value");
      String value = elem.getAttribute("value");
      String key_path = getKey_path(category, attr, key);
      String label = "";
      String data_label = "";
      if (RADIO_CHECK.contains(category)) {
        String id_attr = elem.getAttribute("id");
        String name_attr = elem.getAttribute("name");
        if (id_attr != null) {
          String id_key_path = "//input[@id='" + id_attr + "']";
          data_label = getLabel(rootelem, id_key_path, "id");
        }
        if (name_attr != null) {
          String name_key_path = "//input[@name='" + name_attr + "']";
          label = getLabel(rootelem, name_key_path, "name");
        }

      } else {
        label = getLabel(rootelem, key_path, attr);
      }
      questionAttrs.setValue(value);
      questionAttrs.setKey(key_path);
      questionAttrs.setCategory(category);
      questionAttrs.setLabel(label);
      questionAttrs.setData_label(data_label);
      //questionAttrs.setOptions(stringifyOptions(key_path, elem));
      questionAttrlist.add(questionAttrs);
    }
  }

  private static Object stringifyOptions(String key_path, WebElement elem) {
    List<String> options = Lists.newLinkedList();
    Select select = new Select(elem);
    List<WebElement> options2 = select.getOptions();
    for (WebElement e : options2) {
      options.add(e.getText());
    }
    return options;
  }

  private static String getLabel(WebElement elem, String ref_key_path,
      String attr) {
    String for_label_path = "//label[@for=" + ref_key_path + "/@" + attr + "]";
    List<WebElement> for_labels = elem.findElements(By.xpath(for_label_path));
    if (!for_labels.isEmpty()) {
      return for_labels.get(0).getText();
    }

    String p_label_path = ref_key_path + "/preceding-sibling::label";
    List<WebElement> p_labels = elem.findElements(By.xpath(p_label_path));
    if (!p_labels.isEmpty()) {
      return p_labels.get(0).getText();
    }

    String f_label_path = ref_key_path + "/following-sibling::label";
    List<WebElement> f_labels = elem.findElements(By.xpath(f_label_path));
    if (!f_labels.isEmpty()) {
      return f_labels.get(0).getText();
    }

    String span_label_path = "(" + ref_key_path
        + "/preceding::*/label)[position()=last()]";
    List<WebElement> span_labels = elem.findElements(By.xpath(span_label_path));
    if (!span_labels.isEmpty()) {
      String txt = span_labels.get(0).getText();
      if (txt == null) {
        List<WebElement> span_labels_nxt = elem.findElements(By
            .xpath(span_label_path + "/*"));
        return span_labels_nxt.get(0).getText();
      }
      return txt;
    }

    String th_label_path = "(" + ref_key_path
        + "/preceding::th)[position()=last()]";
    List<WebElement> th_labels = elem.findElements(By.xpath(th_label_path));
    if (!th_labels.isEmpty()) {
      String txt = th_labels.get(0).getText();
      if (txt == null) {
        return th_labels.get(0).getAttribute("innerHTML").replaceAll("<[^>]*>",
            "");
      }
      return txt;
    }

    String td_label_path = "(" + ref_key_path
        + "/preceding::td)[position()=last()]";
    List<WebElement> td_labels = elem.findElements(By.xpath(td_label_path));
    if (!td_labels.isEmpty()) {
      String txt = td_labels.get(0).getText();
      if (txt == null) {
        return td_labels.get(0).getAttribute("innerHTML").replaceAll("<[^>]*>",
            "");
      }
      return txt;
    } else {
      List<WebElement> th_labels_nxt = elem.findElements(By.xpath(th_label_path
          + "/*"));
      if (!th_labels_nxt.isEmpty()) {
        return th_labels_nxt.get(0).getText();
      } else {
        List<WebElement> td_labels_nxt = elem.findElements(By
            .xpath(td_label_path + "/*"));
        if (!td_labels_nxt.isEmpty()) {
          return td_labels_nxt.get(0).getText();
        }
      }
    }

    return "";
  }

  private static String getKey_path(String category, String attr, String key) {
    String key_path = "";
    if (category.equals("textarea")) {
      key_path = "//textarea[@" + attr + "='" + key + "']";
    } else if (category.equals("select")) {
      key_path = "//select[@" + attr + "='" + key + "']";
    } else {
      key_path = "//input[@" + attr + "='" + key + "']";
    }
    return key_path;
  }

  private static Map<String, String> getKey(WebElement elem) {
    Map<String, String> result = Maps.newHashMap();
    String category = elem.getAttribute("type");
    if (category == null) {
      category = elem.getTagName();
    }
    String attr = "id";
    if (RADIO_CHECK.contains(category)) {
      attr = "name";
    }
    String attr_value = elem.getAttribute(attr);
    if (attr_value == null) {
      attr = "name";
      attr_value = elem.getAttribute(attr);
    }
    if (attr_value == null) {
      attr = "value";
      attr_value = elem.getAttribute(attr);
    }
    result.put("category", category);
    result.put("attr", attr);
    result.put("attr_value", attr_value);
    return result;
  }
}
