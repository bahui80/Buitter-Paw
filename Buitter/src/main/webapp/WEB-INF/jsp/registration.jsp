<%@ include file="header.jsp" %>

<div class="container">
<div class="row row-offcanvas row-offcanvas-right">
<div class="col-md-8 col-md-offset-2">
    <div class="well">
              <form class="bs-example form-horizontal" method="post" action="register">
                <fieldset>
                  <legend>New user</legend>
                  <div class="form-group">
                    <label for="username" class="col-md-3 control-label">Username</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" placeholder="Username" name="username">
                      <c:if test="${not empty error_username}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_username}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="password" class="col-md-3 control-label">Password</label>
                    <div class="col-md-8">
                      <input type="password" class="form-control2" placeholder="Password" name="password">
                      <c:if test="${not empty error_password}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_password}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                   <div class="form-group">
                    <label for="re-password" class="col-md-3 control-label">Re-enter password</label>
                    <div class="col-md-8">
                      <input type="password" class="form-control2" placeholder="Re-enter password" name="password2">
                      <c:if test="${not empty error_password2}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_password2}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="name" class="col-md-3 control-label pull-left">Name</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" placeholder="Name" name="name">
                      <c:if test="${not empty error_name}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_name}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="surname" class="col-md-3 control-label">Surname</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" placeholder="Surname" name="surname">
                      <c:if test="${not empty error_surname}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_surname}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                    <div class="form-group">
                    <label for="description" class="col-md-3 control-label">Description</label>
                    <div class="col-md-8">
                      <textarea class="form-control2" rows="2" name="description"></textarea>
                      <span class="help-block">Write a description for others users to get to know you</span>
                      <c:if test="${not empty error_description}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_description}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="question" class="col-md-3 control-label">Secret question</label>
                    <div class="col-md-8">
                      <select class="form-control2" name="question">
                        <option>What's the name of your dog?</option>
                        <option>Who was your favourite teacher?</option>
                        <option>Where do you live?</option>
                        <option>Do you hate Twitter?</option>
                        <option>What's your aunt's name?</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="answer" class="col-md-3 control-label">Secret answer</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" placeholder="Type your answer" name="answer">
                      <c:if test="${not empty error_answer}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_answer}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="photo" class="col-md-3 control-label">Profile picture</label>
                    <div class="col-md-8">
                      <input type="file" data-buttonText="Select photo" accept="image/*" name="photo"/>
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
        </div>
      </div>

          
<%@ include file="footer.jsp" %>