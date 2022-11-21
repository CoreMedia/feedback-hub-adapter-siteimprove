package com.coremedia.labs.plugins.adapters.siteimprove.itemtypes;

import com.coremedia.feedbackhub.items.FeedbackItemBuilder;
import com.coremedia.feedbackhub.items.GaugeFeedbackItem;

/**
 *
 */
public class ComparingGaugeFeedbackItemBuilder extends FeedbackItemBuilder<ComparingGaugeFeedbackItemBuilder> {
  private String color1;
  private String linkText1;
  private String url1;
  private float value1;
  private float targetValue1;
  private int decimalPlaces1;
  private long age1;

  private String color2;
  private String linkText2;
  private String url2;
  private float value2;
  private int decimalPlaces2;
  private long age2;
  private float targetValue2;


  public ComparingGaugeFeedbackItemBuilder withScore1Color(String color1) {
    this.color1 = color1;
    return this;
  }

  public ComparingGaugeFeedbackItemBuilder withScore2Color(String color2) {
    this.color2 = color2;
    return this;
  }

  public ComparingGaugeFeedbackItemBuilder withGauge1Link(String url, String linkTarget) {
    this.linkText1 = linkTarget;
    this.url1 = url;
    return this;
  }

  public ComparingGaugeFeedbackItemBuilder withGauge2Link(String url, String linkTarget) {
    this.linkText2 = linkTarget;
    this.url2 = url;
    return this;
  }

  public ComparingGaugeFeedbackItemBuilder withGaugeAge1(long age1) {
    this.age1 = age1;
    return this;
  }

  public ComparingGaugeFeedbackItemBuilder withGaugeAge2(long age2) {
    this.age2 = age2;
    return this;
  }

  public ComparingGaugeFeedbackItemBuilder withGauge1Value(double value, float targetValue, int decimalPlaces) {
    return this.withGauge1Value((float) value, targetValue, decimalPlaces);
  }

  public ComparingGaugeFeedbackItemBuilder withGauge1Value(float value, float targetValue, int decimalPlaces) {
    this.value1 = value;
    this.targetValue1 = targetValue;
    this.decimalPlaces1 = decimalPlaces;
    return this;
  }

  public ComparingGaugeFeedbackItemBuilder withGauge2Value(double value, float targetValue, int decimalPlaces) {
    return this.withGauge2Value((float) value, targetValue, decimalPlaces);
  }

  public ComparingGaugeFeedbackItemBuilder withGauge2Value(float value, float targetValue, int decimalPlaces) {
    this.value2 = value;
    this.targetValue2 = targetValue;
    this.decimalPlaces2 = decimalPlaces;
    return this;
  }

  public ComparingGaugeFeedbackItem build() {
    GaugeFeedbackItem item1 = GaugeFeedbackItem.builder()
            .withValue(value1, targetValue1, decimalPlaces1 > 0)
            .withAge(age1)
            .withColor(color1)
            .withLink(url1,linkText1).build();
    GaugeFeedbackItem item2 = GaugeFeedbackItem.builder()
            .withValue(value2, targetValue2, decimalPlaces2 > 0)
            .withAge(age2)
            .withColor(color2)
            .withLink(url2, linkText2).build();
    return new ComparingGaugeFeedbackItem(collection, title, help, item1, item2);
  }
}
