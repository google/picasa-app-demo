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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A single thumbnail that is shown on the left-hand side.
 */
public class Thumbnail extends Composite {

  /**
   * Classes implementing this interface can be called when a thumbnail is
   * clicked.
   */
  public static interface ClickedHandler {
    public void onClick();
  }

  private Image thumbnail = new Image();
  private Label titleLabel = new Label();
  private ClickedHandler clickedHandler;

  /**
   * Instantiates a thumbnail view with the given title and image.
   */
  public Thumbnail(String title, String thumbnailUrl) {
    titleLabel.setText(title);
    titleLabel.setStyleName("thumbnailLabel");
    thumbnail.setUrl(thumbnailUrl);
    thumbnail.setStyleName("thumbnail");
    thumbnail.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (clickedHandler != null) {
          clickedHandler.onClick();
        }
      }
    });

    VerticalPanel mainPanel = new VerticalPanel();
    mainPanel.add(thumbnail);
    mainPanel.add(titleLabel);
    initWidget(mainPanel);
  }

  /**
   * Sets the handler that is called when the thumbnail is clicked.
   */
  public void setClickedHandler(ClickedHandler handler) {
    this.clickedHandler = handler;
  }
}
