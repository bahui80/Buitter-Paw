<%@ include file="WEB-INF/jsp/header.jsp" %>

<div class="well">
              <form class="bs-example form-horizontal">
                <fieldset>
                  <legend>New user</legend>
                  <div class="form-group">
                    <label for="username" class="col-lg-2 control-label">Username</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" placeholder="Username">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="password" class="col-lg-2 control-label">Password</label>
                    <div class="col-lg-10">
                      <input type="password" class="form-control" placeholder="Password">
                    </div>
                  </div>
                   <div class="form-group">
                    <label for="re-password" class="col-lg-2 control-label">Re-enter password</label>
                    <div class="col-lg-10">
                      <input type="password" class="form-control" placeholder="Re-enter password">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="name" class="col-lg-2 control-label">Name</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" placeholder="Name">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="surname" class="col-lg-2 control-label">Surname</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" placeholder="Surname">
                    </div>
                  </div>
                    <div class="form-group">
                    <label for="description" class="col-lg-2 control-label">Description</label>
                    <div class="col-lg-10">
                      <textarea class="form-control" rows="3"></textarea>
                      <span class="help-block">Write a description for others users to get to know you</span>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="question" class="col-lg-2 control-label">Secret question</label>
                    <div class="col-lg-10">
                      <select class="form-control">
                        <option>What's the name of your dog?</option>
                        <option>Who was your favourite teacher?</option>
                        <option>Where do you live?</option>
                        <option>Do you hate Twitter?</option>
                        <option>What's your aunt's name?</option>
                      </select>
                      <br>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="answer" class="col-lg-2 control-label">Secret answer</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" placeholder="answer">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                      <button type="submit" class="btn btn-primary">Submit</button> 
                    </div>
                  </div>
                </fieldset>
              </form>
            </div>

           </body>
           </html>