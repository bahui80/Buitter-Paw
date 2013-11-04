<%@ include file="/WEB-INF/jsp/header.jsp" %>

<script>
    function home() {
      var str1 = "../home/home";
      window.location = str1;
    }
</script>

<div class="container">
<div class="row row-offcanvas row-offcanvas-right">
<div class="col-md-8 col-md-offset-2">
    <div class="well">
              <form:form class="bs-example form-horizontal" method="POST" commandName="userForm" action="${action}" enctype="multipart/form-data">
                <fieldset>
                  <legend>Your personal details</legend>
                  <c:if test="${action == 'editprofile'}">
                      <form:input type="hidden" class="form-control2" name="username" path="username" value="${user_username}"/>
                  </c:if>
                  <c:if test="${action == 'registration'}">
                    <c:set var="error_username"><form:errors path="username"/></c:set>
                    <div class="form-group <c:if test="${not empty error_username}">has-error</c:if>">
                      <label for="username" class="col-md-3 control-label">Username</label>
                      <div class="col-md-8">
                        <form:input type="text" class="form-control2" maxlength="32" placeholder="Username" name="username" path="username" value="${user_username}"/>
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><form:errors path="username" /></em></small></p>
                      </div>
                    </div>
                  </c:if>
                  <c:set var="error_password"><form:errors path="password"/></c:set>
                  <div class="form-group <c:if test="${not empty error_password}">has-error</c:if>">
                    <label for="password" class="col-md-3 control-label">Password</label>
                    <div class="col-md-8">
                      <form:input type="password" maxlength="32" class="form-control2" placeholder="Password" name="password" path="password" value="${user_password}"/>
                        <p class="text-danger" style="margin-bottom: 0px"><small><em><form:errors path="password" /></em></small></p>
                    </div>
                  </div>
                   <c:set var="error_password2"><form:errors path="password2"/></c:set>
                   <div class="form-group <c:if test="${not empty error_password2}">has-error</c:if>">
                    <label for="re-password" class="col-md-3 control-label">Re-type password</label>
                    <div class="col-md-8">
                      <form:input type="password" maxlength="32" class="form-control2" placeholder="Re-type password" name="password2" path="password2" value="${user_password2}"/>
                      <p class="text-danger" style="margin-bottom: 0px"><small><em><form:errors path="password2" /></em></small></p>
                    </div>
                  </div>
                  <c:set var="error_name"><form:errors path="name"/></c:set>
                  <div class="form-group <c:if test="${not empty error_name}">has-error</c:if>">
                    <label for="name" class="col-md-3 control-label pull-left">Name</label>
                    <div class="col-md-8">
                      <form:input type="text" class="form-control2" maxlength="32" placeholder="Name" name="name" path="name" value="${user_name}"/>
                      <p class="text-danger" style="margin-bottom: 0px"><small><em><form:errors path="name" /></em></small></p>
                    </div>
                  </div>
                  <c:set var="error_surname"><form:errors path="surname"/></c:set>
                  <div class="form-group <c:if test="${not empty error_surname}">has-error</c:if>">
                    <label for="surname" class="col-md-3 control-label">Surname</label>
                    <div class="col-md-8">
                      <form:input type="text" class="form-control2" maxlength="32" placeholder="Surname" name="surname" path="surname" value="${user_surname}"/>
                      <p class="text-danger" style="margin-bottom: 0px"><small><em><form:errors path="surname" /></em></small></p>
                    </div>
                  </div>
                    <c:set var="error_description"><form:errors path="description"/></c:set>
                    <div class="form-group <c:if test="${not empty error_description}">has-error</c:if>">
                    <label for="description" class="col-md-3 control-label">Description</label>
                    <div class="col-md-8">
                      <form:textarea class="form-control2" rows="4" maxlength="140" name="description" path="description"/>
                      <c:out value="${user_description}"/>
                      <span class="help-block">Write a description for others users to get to know you</span>
                      <p class="text-danger" style="margin-bottom: 0px"><small><em><form:errors path="description" /></em></small></p>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="question" class="col-md-3 control-label">Secret question</label>
                    <div class="col-md-8">
                      <form:select class="form-control2" name="question" path="question">
                          <form:option value="What is the name of your dog?">What is the name of your dog?</form:option>
                          <form:option value="Who was your favourite teacher?">Who was your favourite teacher?</form:option>
                          <form:option value="Where do you live?">Where do you live?</form:option>
                          <form:option value="Do you hate Twitter?">Do you hate Twitter?</form:option>
                          <form:option value="What is your aunts name?">What is your aunts name?</form:option>
                      </form:select>
                    </div>
                  </div>
                  <c:set var="error_answer"><form:errors path="answer"/></c:set>
                  <div class="form-group <c:if test="${not empty error_answer}">has-error</c:if>">
                    <label for="answer" class="col-md-3 control-label">Secret answer</label>
                    <div class="col-md-8">
                      <form:input type="text" maxlength="60" class="form-control2" placeholder="Type your answer" name="answer" path="answer" value="${user_answer}"/>
                      <p class="text-danger" style="margin-bottom: 0px"><small><em><form:errors path="answer" /></em></small></p>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="privacy" class="col-md-3 control-label">Privacy</label>
                    <div class="col-md-8">
                      <form:select class="form-control2" name="privacy" path="privacy">
                        <form:option value="Public">Public</form:option>
                        <form:option value="Private">Private</form:option>
                     </form:select>
                    </div>
                  </div>
                  <c:set var="error_photo"><form:errors path="photo"/></c:set>
                  <div class="form-group <c:if test="${not empty error_photo}">has-error</c:if>">
                    <label for="photo" class="col-md-3 control-label">Profile picture</label>
                    <div class="col-md-8">
                      <form:input type="file" data-buttonText="Select photo" accept="image/jpeg, image/jpg, image/png" name="photo" path="photo"/>
                      <p class="text-danger" style="margin-bottom: 0px"><small><em><form:errors path="photo" /></em></small></p>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-md-5 col-md-offset-4">
                      <button type="button" class="btn btn-default" onclick="home();">Cancel</button>
                      <button type="submit" class="btn btn-primary">Confirm</button> 
                    </div>
                  </div>
                </fieldset>
              </form:form>
            </div>
          </div>
        </div>


          
<%@ include file="/WEB-INF/jsp/footer.jsp" %>