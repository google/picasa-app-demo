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

package com.google.webcourse.picasaapp.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.webcourse.picasaapp.client.CommentService;
import com.google.webcourse.picasaapp.server.data.Comment;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CommentServiceImpl extends RemoteServiceServlet implements
    CommentService {

  public String[] getComments(String photoUrl) throws IllegalArgumentException {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Query query = pm.newQuery(Comment.class);
    query.setFilter("photoUrl == photoUrlParam");
    query.declareParameters("String photoUrlParam");
    try {
      List<Comment> comments = (List<Comment>) query.execute(photoUrl);
      String[] result = new String[comments.size()];
      for (int i = 0; i < comments.size(); ++i) {
        result[i] = comments.get(i).getComment();
      }
      return result;
    } finally {
      query.closeAll();
    }
  }

  public void storeComment(String key, String comment) {
    Comment newComment = new Comment(key, comment);
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(newComment);
    } finally {
      pm.close();
    }
  }
}
