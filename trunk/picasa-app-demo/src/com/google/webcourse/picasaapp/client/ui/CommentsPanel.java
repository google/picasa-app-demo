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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Shows comments in a vertical order.
 */
public class CommentsPanel extends Composite {

  private VerticalPanel mainPanel = new VerticalPanel();

  public CommentsPanel() {
    mainPanel.setWidth("100%");
    initWidget(mainPanel);
  }

  /**
   * Shows the given comments.
   */
  public void setComments(String[] comments) {
    mainPanel.clear();

    for (String comment : comments) {
      Label commentLabel = new Label();
      commentLabel.setText(comment);
      commentLabel.setStyleName("comment");
      mainPanel.add(commentLabel);
    }
  }
}
