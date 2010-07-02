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

package com.google.webcourse.picasaapp.client.request;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.webcourse.picasaapp.client.data.Albums;
import com.google.webcourse.picasaapp.client.data.Photos;

/**
 * This class makes Picasa Gdata requests.
 */
public class PicasaRequest {
  /**
   * Every requests gets a new callback, so that we can make many requests in
   * parallel.
   */
  private static final String CALLBACK_NAME = "picb{N}";

  /** The basic Picasa album Gdata URL. */
  private static final String ALBUMS_REQUEST_URL = "http://picasaweb.google.com/data/feed/api/"
      + "user/{USER}?alt=json&callback={CALLBACK}";

  private static int counter = 0;

  /**
   * Requests all albums for the given user.
   */
  public static void requestAlbumsForUser(String username,
      ResponseCallback<Albums> callback) {
    String callbackName = getNextCallbackName();
    String url = ALBUMS_REQUEST_URL.replace("{USER}", username);
    url = url.replace("{CALLBACK}", callbackName);
    request(url, callbackName, callback);
  }

  /**
   * Requests all photos for one album.
   * 
   * @param jsonUrl
   *          the URL which returns the JSON photos response
   */
  public static void requestPhotos(String jsonUrl,
      ResponseCallback<Photos> callback) {
    String callbackName = getNextCallbackName();
    String url = jsonUrl += "&callback=" + callbackName;
    request(url, callbackName, callback);
  }

  private static String getNextCallbackName() {
    return CALLBACK_NAME.replace("{N}", String.valueOf(counter++));
  }

  private static void request(String url, String callbackName,
      ResponseCallback<?> callback) {
    registerCallback(callbackName, callback);
    ScriptElement element = Document.get().createScriptElement();
    element.setSrc(url);
    Document.get().getBody().appendChild(element);
  }

  private static native void registerCallback(String name,
      ResponseCallback<?> callback) /*-{
    $wnd[name] = function(response) {
      callback.@com.google.webcourse.picasaapp.client.request.ResponseCallback::callback(Ljava/lang/Object;)(response);
    }
  }-*/;
}
