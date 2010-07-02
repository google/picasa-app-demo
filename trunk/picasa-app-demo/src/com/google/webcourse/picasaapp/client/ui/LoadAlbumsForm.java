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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The form on the top-left which let's users enter a Picasa username and load
 * the albums of that user.
 */
public class LoadAlbumsForm extends Composite {

  /**
   * Classes implementing this interface can be called when the user wants to
   * load albums of a user.
   */
  public static interface LoadAlbumsListener {
    public void loadAlbums(String username);
  }

  Label userNameLabel = new Label("Enter Picasa username:");
  TextBox userNameTextBox = new TextBox();
  Button loadButton = new Button("Load albums");
  LoadAlbumsListener listener;

  public LoadAlbumsForm() {

    loadButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (listener != null) {
          listener.loadAlbums(userNameTextBox.getText());
        }
      }
    });

    // Just for testing, so we don't have to always enter a username.
    userNameTextBox.setText("bhrose");
    userNameTextBox.setStyleName("picasaUserName");
    loadButton.setStyleName("loadAlbumsButton");

    VerticalPanel mainPanel = new VerticalPanel();
    mainPanel.add(userNameLabel);
    mainPanel.add(userNameTextBox);
    mainPanel.add(loadButton);
    initWidget(mainPanel);
  }

  /**
   * Sets the listener that will be called when the user wants to load albums of
   * a user.
   */
  public void setLoadAlbumListener(LoadAlbumsListener loadAlbumListener) {
    this.listener = loadAlbumListener;
  }
}
