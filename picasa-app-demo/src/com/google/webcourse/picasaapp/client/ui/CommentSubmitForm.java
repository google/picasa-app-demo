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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * A form that allows users to enter and submit a comment.
 */
public class CommentSubmitForm extends Composite {

  private static CommentSubmitFormUiBinder uiBinder = GWT
      .create(CommentSubmitFormUiBinder.class);

  interface CommentSubmitFormUiBinder extends
      UiBinder<Widget, CommentSubmitForm> {
  }

  /**
   * Classes implementing this interface cann be called when the user submits a
   * comment.
   */
  public static interface CommentSubmitHandler {
    /**
     * A comment is to be submitted.
     */
    public void submitComment(String comment);
  }

  @UiField
  Label label;

  @UiField
  TextArea commentTextArea;

  @UiField
  Button submitButton;
  private CommentSubmitHandler submitHandler;

  public CommentSubmitForm() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  /**
   * Sets the handler that will be called when the user submits a comment.
   */
  public void setCommentSubmitHandler(CommentSubmitHandler handler) {
    submitHandler = handler;
  }

  @UiHandler("submitButton")
  void submitButtonClicked(ClickEvent e) {
    if (submitHandler != null) {
      submitHandler.submitComment(commentTextArea.getText());
      commentTextArea.setText("");
    }
  }
}
