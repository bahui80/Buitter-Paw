<%@ include file="/WEB-INF/jsp/header.jsp" %>
<script>
    function home() {
      var str1 = "/Buitter/web/home/home";
      window.location = str1;
    }
</script>

<div class="container">
<div class="row row-offcanvas row-offcanvas-right">
<div class="col-md-8 col-md-offset-2">
    <div class="well">
              <form class="bs-example form-horizontal" method="post" action="<c:out value="${action}"/>" enctype="multipart/form-data">
                <fieldset>
                  <legend>Your personal details</legend>
                  <c:if test="${action == 'editprofile'}">
                      <input type="hidden" class="form-control2" name="username" value="<c:out value="${user_username}"/>"/>
                  </c:if>
                  <c:if test="${action == 'registration'}">
                    <div class="form-group <c:if test="${not empty error_username}">has-error</c:if>">
                      <label for="username" class="col-md-3 control-label">Username</label>
                      <div class="col-md-8">
                        <input type="text" class="form-control2" maxlength="32" placeholder="Username" name="username" value="<c:out value="${user_username}"/>"/>
                        <c:if test="${not empty error_username}">
                          <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_username}"/></em></small></p>
                        </c:if>
                      </div>
                    </div>
                  </c:if>
                  <div class="form-group <c:if test="${not empty error_password}">has-error</c:if>">
                    <label for="password" class="col-md-3 control-label">Password</label>
                    <div class="col-md-8">
                      <input type="password" maxlength="32" class="form-control2" placeholder="Password" name="password" value="<c:out value="${user_password}"/>"/>
                      <c:if test="${not empty error_password}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_password}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                   <div class="form-group <c:if test="${not empty error_password2}">has-error</c:if>">
                    <label for="re-password" class="col-md-3 control-label">Re-type password</label>
                    <div class="col-md-8">
                      <input type="password" maxlength="32" class="form-control2" placeholder="Re-type password" name="password2" value="<c:out value="${user_password2}"/>"/>
                      <c:if test="${not empty error_password2}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_password2}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group <c:if test="${not empty error_name}">has-error</c:if>">
                    <label for="name" class="col-md-3 control-label pull-left">Name</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" maxlength="32" placeholder="Name" name="name" value="<c:out value="${user_name}"/>"/>
                      <c:if test="${not empty error_name}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_name}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group <c:if test="${not empty error_surname}">has-error</c:if>">
                    <label for="surname" class="col-md-3 control-label">Surname</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control2" maxlength="32" placeholder="Surname" name="surname" value="<c:out value="${user_surname}"/>"/>
                      <c:if test="${not empty error_surname}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_surname}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                    <div class="form-group <c:if test="${not empty error_description}">has-error</c:if>">
                    <label for="description" class="col-md-3 control-label">Description</label>
                    <div class="col-md-8">
                      <textarea class="form-control2" rows="4" maxlength="140" name="description"><c:out value="${user_description}"/></textarea>
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
                        <option <c:if test="${user_question == 'What is the name of your dog?'}">selected="selected"</c:if> >What is the name of your dog?</option>
                        <option <c:if test="${user_question == 'Who was your favourite teacher?'}">selected="selected"</c:if> >Who was your favourite teacher?</option>
                        <option <c:if test="${user_question == 'Where do you live?'}">selected="selected"</c:if> >Where do you live?</option>
                        <option <c:if test="${user_question == 'Do you hate Twitter?'}">selected="selected"</c:if> >Do you hate Twitter?</option>
                        <option <c:if test="${user_question == 'What is your aunts name?'}">selected="selected"</c:if> >What is your aunts name?</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group <c:if test="${not empty error_answer}">has-error</c:if>">
                    <label for="answer" class="col-md-3 control-label">Secret answer</label>
                    <div class="col-md-8">
                      <input type="text" maxlength="60" class="form-control2" placeholder="Type your answer" name="answer" value="<c:out value="${user_answer}"/>"/>
                      <c:if test="${not empty error_answer}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_answer}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group <c:if test="${not empty error_photo}">has-error</c:if>">
                    <label for="photo" class="col-md-3 control-label">Profile picture</label>
                    <div class="col-md-8">
                      <input type="file" data-buttonText="Select photo" accept="image/jpeg, image/jpg, image/png" name="photo"/>
                       <c:if test="${not empty error_photo}">
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_photo}"/></em></small></p>
                      </c:if>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-md-5 col-md-offset-4">
                      <button type="button" class="btn btn-default" onclick="home();">Cancel</button>
                      <button type="submit" class="btn btn-primary">Confirm</button> 
                    </div>
                  </div>
                </fieldset>
              </form>
            </div>
          </div>
        </div>
      </div>

          
<%@ include file="/WEB-INF/jsp/footer.jsp" %>