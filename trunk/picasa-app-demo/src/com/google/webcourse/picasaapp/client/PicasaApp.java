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

package com.google.webcourse.picasaapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.webcourse.picasaapp.client.data.Albums;
import com.google.webcourse.picasaapp.client.data.Albums.Album;
import com.google.webcourse.picasaapp.client.data.Photos;
import com.google.webcourse.picasaapp.client.data.Photos.Photo;
import com.google.webcourse.picasaapp.client.request.PicasaRequest;
import com.google.webcourse.picasaapp.client.request.ResponseCallback;
import com.google.webcourse.picasaapp.client.ui.CommentSubmitForm;
import com.google.webcourse.picasaapp.client.ui.CommentSubmitForm.CommentSubmitHandler;
import com.google.webcourse.picasaapp.client.ui.CommentsPanel;
import com.google.webcourse.picasaapp.client.ui.LoadAlbumsForm;
import com.google.webcourse.picasaapp.client.ui.LoadAlbumsForm.LoadAlbumsListener;
import com.google.webcourse.picasaapp.client.ui.PhotoView;
import com.google.webcourse.picasaapp.client.ui.Thumbnail;
import com.google.webcourse.picasaapp.client.ui.Thumbnail.ClickedHandler;

/**
 * The entry point class of the application.
 */
public class PicasaApp implements EntryPoint, ResizeHandler {
  /**
   * Create a remote service proxy to talk to the server-side comment service.
   */
  private final CommentServiceAsync commentService = GWT
      .create(CommentService.class);

  /**
   * The widths of the two columns on either side of the screen. This is used to
   * calculate the remaining width for the photo.
   */
  private static final int SIDE_COLUMNS_WIDTH_SUM = 400;

  /** This panel contains all the thumbnails on the left-hand side. */
  private RootPanel thumbnailList;

  /** Shows a single photo in the center for the application. */
  private PhotoView photoView;

  /** Show the comments on the right-hand side. */
  private CommentsPanel commentsPanel;

  /** The URL of the currently shown photo. */
  private String currentPhotoUrl;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    LoadAlbumsForm loadAlbumsForm = new LoadAlbumsForm();
    CommentSubmitForm commentSubmitForm = new CommentSubmitForm();
    thumbnailList = RootPanel.get("thumbnailList");
    photoView = new PhotoView();
    commentsPanel = new CommentsPanel();

    RootPanel.get("loadAlbumsForm").add(loadAlbumsForm);
    RootPanel.get("photoWrapper").add(photoView);
    RootPanel.get("comments").add(commentsPanel);
    RootPanel.get("comments").add(commentSubmitForm);

    loadAlbumsForm.setLoadAlbumListener(new LoadAlbumsListener() {
      public void loadAlbums(String username) {
        loadAlbumsOfUser(username);
      }
    });

    commentSubmitForm.setCommentSubmitHandler(new CommentSubmitHandler() {
      public void submitComment(String comment) {
        commentService.storeComment(currentPhotoUrl, comment,
            new AsyncCallback<Void>() {
              public void onFailure(Throwable caught) {
                // TODO: Show an error message
              }

              public void onSuccess(Void result) {
                loadComments(currentPhotoUrl);
              }
            });
      }
    });

    // Make sure the thumbnail list has always the correct height, as
    // height=100% doesn't work properly.
    Window.addResizeHandler(this);
    Window.addWindowScrollHandler(new ScrollHandler() {
      public void onWindowScroll(ScrollEvent event) {
        onResize(null);
      }
    });
    onResize(null);
  }

  /**
   * Loads the albums of the given user and shows them in the left-hand side.
   */
  public void loadAlbumsOfUser(String username) {
    PicasaRequest.requestAlbumsForUser(username,
        new ResponseCallback<Albums>() {
          public void callback(Albums response) {
            thumbnailList.clear();
            for (int i = 0; i < response.getTotalResults(); ++i) {
              final Album album = response.getEntry(i);
              Thumbnail thumbnail = new Thumbnail(album.getTitle(), album
                  .getThumbnailUrl());
              thumbnail.setClickedHandler(new ClickedHandler() {
                public void onClick() {
                  loadPhotos(album.getJsonUrl());
                }
              });
              thumbnailList.add(thumbnail);
            }
          }
        });
  }

  /**
   * Loads the photos of a single album.
   * 
   * @param url
   *          the URL that returns the JSON data for the album
   */
  public void loadPhotos(String url) {
    PicasaRequest.requestPhotos(url, new ResponseCallback<Photos>() {
      public void callback(Photos response) {
        thumbnailList.clear();
        for (int i = 0; i < response.getTotalResults(); ++i) {
          final Photo photo = response.getEntry(i);
          Thumbnail thumbnail = new Thumbnail(photo.getTitle(), photo
              .getThumbnailUrl());
          thumbnail.setClickedHandler(new ClickedHandler() {
            public void onClick() {
              // Thumbnail is already loaded, so let's show it first.
              photoView.setPhotoUrl(photo.getThumbnailUrl());
              photoView.setPhotoUrl(photo.getUrl());
              loadComments(photo.getUrl());
            }
          });
          thumbnailList.add(thumbnail);
        }
      }
    });
  }

  /**
   * Loads the comments for the given photo and displays them.
   */
  public void loadComments(String photoUrl) {
    currentPhotoUrl = photoUrl;
    commentService.getComments(photoUrl, new AsyncCallback<String[]>() {
      public void onSuccess(String[] result) {
        commentsPanel.setComments(result);
      }

      public void onFailure(Throwable caught) {
        // TODO: Show an error message.
      }
    });
  }

  /**
   * Causes the UI elements to be layed out.
   */
  public void onResize(ResizeEvent event) {
    int height = Window.getClientHeight() - thumbnailList.getAbsoluteTop();
    thumbnailList.setHeight((height + Window.getScrollTop()) - 5 + "px");

    int photoWidth = Window.getClientWidth() - SIDE_COLUMNS_WIDTH_SUM;
    photoView.setPhotoWidth(photoWidth);
  }
}
