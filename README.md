# vertx-project
Basically, the index page is a simple CRUD UI to manage a not-yet-read articles list. It was made in a generic way, so you can transpose it to your own stuff. The reading list is displayed in the main table. You can create a new reading list, edit one, or delete one. 
These actions are relying on a REST API (that we are going to implement) through AJAX calls.
 
 Vert.x Web makes the implementation of a REST API really easy, as it basically routes your URL to the right handler. 
 The API will be structured as follows:
 
     GET /api/articles => get all articles (getAll).
     GET /api/articles/:id => get the article with the corresponding id (getOne).
     POST /api/articles => add a new article (addOne).
     PUT /api/articles/:id => update an article (updateOne).
     DELETE /api/articles/id => delete an article (deleteOne).
