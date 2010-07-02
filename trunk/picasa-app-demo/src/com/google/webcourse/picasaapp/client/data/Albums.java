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

package com.google.webcourse.picasaapp.client.data;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A JavaScript overlay object for the Picasa albums JSON response.
 */
public class Albums extends JavaScriptObject {

  /**
   * A JavaScript overlay object for a single album.
   */
  public static class Album extends JavaScriptObject {
    protected Album() {
    }

    /**
     * Returns the title of this album.
     */
    public final native String getTitle() /*-{
      return this.title.$t;
    }-*/;

    /**
     * Returns the thumbnail URL of this album.
     */
    public final native String getThumbnailUrl() /*-{
      return this.media$group.media$thumbnail[0].url;
    }-*/;

    /**
     * Returns the link to fetch the photos of this album as JSON.
     */
    public final native String getJsonUrl() /*-{
      for(var i = 0;i < this.link.length; ++i) {
        if (this.link[i].rel == "http://schemas.google.com/g/2005#feed") {
      	  return this.link[i].href;
      	}
      }
    }-*/;
  }

  protected Albums() {
  }

  /**
   * Returns the number of albums.
   */
  public final native int getTotalResults() /*-{
    return this.feed.entry.length;
  }-*/;

  /**
   * Returns the {@link Album} at the given position.
   */
  public final native Album getEntry(int index) /*-{
    return this.feed.entry[index];
  }-*/;
}
