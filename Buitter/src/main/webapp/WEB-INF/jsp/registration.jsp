<%@ include file="header.jsp" %>
<div class="col-md-8 col-md-offset-2">
    <div class="well">
              <form class="bs-example form-horizontal">
                <fieldset>
                  <legend>New user</legend>
                  <div class="form-group">
                    <label for="username" class="col-md-3 control-label">Username</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" placeholder="Username">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="password" class="col-md-3 control-label">Password</label>
                    <div class="col-md-8">
                      <input type="password" class="form-control2" placeholder="Password">
                    </div>
                  </div>
                   <div class="form-group">
                    <label for="re-password" class="col-md-3 control-label">Re-enter password</label>
                    <div class="col-md-8">
                      <input type="password" class="form-control2" placeholder="Re-enter password">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="name" class="col-md-3 control-label pull-left">Name</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" placeholder="Name">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="surname" class="col-md-3 control-label">Surname</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" placeholder="Surname">
                    </div>
                  </div>
                    <div class="form-group">
                    <label for="description" class="col-md-3 control-label">Description</label>
                    <div class="col-md-8">
                      <textarea class="form-control2" rows="3"></textarea>
                      <span class="help-block">Write a description for others users to get to know you</span>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="question" class="col-md-3 control-label">Secret question</label>
                    <div class="col-md-8">
                      <select class="form-control2">
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
                    <label for="answer" class="col-md-3 control-label">Secret answer</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" placeholder="answer">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="photo" class="col-md-3 control-label">Profile picture</label>
                    <div class="col-md-8">
                      <input type="file" class="filestyle" data-buttonText="Select photo">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-md-5 col-md-offset-4">
                      <button class="btn btn-default">Cancel</button>
                      <button type="submit" class="btn btn-primary">Submit</button> 
                    </div>
                  </div>
                </fieldset>
              </form>
            </div>
          </div>

          
<%@ include file="footer.jsp" %>