/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.webcourse.picasaapp.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Shows a single photo with a label.
 */
public class PhotoView extends Composite {
  private Image photo = new Image();
  private Label titleLabel = new Label();
  private VerticalPanel mainPanel;

  public PhotoView() {
    photo.setStyleName("photo");

    mainPanel = new VerticalPanel();
    mainPanel.add(photo);
    mainPanel.add(titleLabel);
    initWidget(mainPanel);
  }

  /**
   * Sets the URL of the photo to be shown.
   */
  public void setPhotoUrl(String url) {
    photo.setUrl(url);
  }

  /**
   * Sets the width of the photo in pixels.
   */
  public void setPhotoWidth(int pixels) {
    photo.setWidth(pixels + "px");
  }
}