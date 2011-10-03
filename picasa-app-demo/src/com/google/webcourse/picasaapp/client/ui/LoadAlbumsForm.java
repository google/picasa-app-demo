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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * The form on the top-left which let's users enter a Picasa username and load
 * the albums of that user.
 */
public class LoadAlbumsForm extends Composite {

  private static LoadAlbumsFormUiBinder uiBinder = GWT
      .create(LoadAlbumsFormUiBinder.class);

  interface LoadAlbumsFormUiBinder extends UiBinder<Widget, LoadAlbumsForm> {
  }

  /**
   * Classes implementing this interface can be called when the user wants to
   * load albums of a user.
   */
  public static interface LoadAlbumsListener {
    public void loadAlbums(String username);
  }

  @UiField
  Label userNameLabel;

  @UiField
  TextBox userNameTextBox;

  @UiField
  Button loadButton;

  LoadAlbumsListener listener;

  public LoadAlbumsForm() {
    initWidget(uiBinder.createAndBindUi(this));

    // Just for testing, so we don't have to always enter a username.
    userNameTextBox.setText("bhrose");
  }

  /**
   * Sets the listener that will be called when the user wants to load albums of
   * a user.
   */
  public void setLoadAlbumListener(LoadAlbumsListener loadAlbumListener) {
    this.listener = loadAlbumListener;
  }

  /**
   * We bind this method to the loadButton member and listen to the ClickEvent
   * fired by it.
   */
  @UiHandler("loadButton")
  void buttonClicked(ClickEvent e) {
    if (listener != null) {
      listener.loadAlbums(userNameTextBox.getText());
    }
  }
}
