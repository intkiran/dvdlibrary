<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New DVD</title>
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-3 col-xs-offset-2">
            <h1>Create DVD</h1>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-xs-5 col-xs-offset-3">
            <div class="alert alert-danger alert-dismissable" id="validationError"
                 style="display:none;">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            </div>
            <div class="alert alert-success alert-dismissable" id="successMsg"
                 style="display:none;">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            </div>

        </div>
    </div>
    <div class="row">
        <form id="createDvdForm" method="post" class="form-horizontal">
            <div class="form-group">
                <label class="col-xs-3 control-label">Dvd Title</label>
                <div class="col-xs-5">
                    <input type="text" class="form-control" name="title" id="title"
                           placeholder="Enter Title"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">Release year</label>
                <div class="col-xs-5">
                    <input type="text" class="form-control" name="year" id="year"
                           placeholder="Enter Release year"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">Director</label>
                <div class="col-xs-5 inputGroupContainer">
                    <div class="input-group">
                        <input type="text" class="form-control" name="director" id="director"
                               placeholder="Enter Director"/>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">Rating</label>
                <div class="col-xs-5 selectContainer">
                    <select class="form-control" name="rating" id="rating">
                        <option value="">Choose Rating</option>
                        <option value="G">G - General Audiences</option>
                        <option value="pg">PG - Parental Guidance Suggested
                        </option>
                        <option value="pg-13">PG-13 - Parents Strongly Cautioned
                        </option>
                        <option value="R">R - Restricted</option>
                        <option value="NC-17">NC-17 - Adults Only
                        </option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">Notes</label>
                <div class="col-xs-5">
						<textarea name="notes" id="notes" class="form-control" rows="5"
                                  placeholder="Enter Note"></textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-xs-5 col-xs-offset-3">
                    <input type="hidden" name="titleHidden" value="">
                    <input type="hidden" name="ratingHidden" value="">
                    <input type="hidden" name="directorHidden" value="">
                    <input type="hidden" name="notesHidden" value="">
                    <button type="button" class="btn btn-default" id="btnCancel">Cancel</button>
                    <button type="submit" class="btn btn-default" id="btnCreateDvd">Create
                        Dvd
                    </button>
                </div>
            </div>
        </form>
    </div>
    <!-- Main Page Content Start -->

    <!-- Main Page Content Stop -->
</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>
<script>
    $(document).ready(function () {
        $.ajax({
                   url: '${pageContext.request.contextPath}/api/dvd/readall',
                   headers: {
                       'Accept': 'application/json',
                       'Content-Type': 'application/json'
                   },
                   method: 'GET',
                   contentType: 'application/json; charset=utf-8',
                   dataType: "json",
                   success: function (data) {
                       console.log("success ", data);
                   },
                   error: function (textStatus, errorThrown) {
                       console.log("fail ", textStatus);
                       console.log("fail ", errorThrown);
                   },
                   complete: function (data) {
                       console.log("complete", data);
                   }
               });

        $("#btnCancel").click(function (e) {
            console.log("btnCancel ");
            window.location.replace("${pageContext.request.contextPath}/jsp/DVDs.jsp");

        });

        $("#btnCreateDvd").click(function (e) {
            var validationErrors = "";
            console.log("Create DVD form click");
            var title = $('#title').val();
            if (!(title)) {
                validationErrors = "Please enter a title for the Dvd</br>";
            }
            var year = $('#year').val();
            var text = /^[0-9]+$/;
            console.log("asdf ", (!text.test(year)))
            if ((!(year)) || (!text.test(year))) {
                validationErrors += "Please enter valid release year";

            } else {
                if (year.length != 4) {
                    validationErrors += "Please enter a 4-digit year";
                }
            }

            if (validationErrors) {
                $('#validationError').show();
                $('#validationError').html(validationErrors);

            } else {
                var director = $('#director').val();
                var rating = $('#rating').val();
                var notes = $('#notes').val();
                var formData = {
                    'title': title,
                    'year': year,
                    'director': director,
                    'rating': rating,
                    'notes': notes,

                };
                $.ajax({
                           type: "POST",
                           contentType: 'application/json;',
                           dataType: 'json',
                           url: "${pageContext.request.contextPath}/api/dvd/create",
                           data: JSON.stringify(formData),
                           success: function (response) {
                               //$("#successMsg").text('Dvd created successfully');
                               //$("#successMsg").show();
                               var title = response.title;
                               var rating = response.rating;
                               var releaseDate = response.releaseDate;
                               var notes = response.notes;
                               var director = response.director;

                               $("#titleHidden").val(response.title)
                               $("#ratingHidden").val(response.rating)
                               $("#yearHidden").val(response.releaseDate)
                               $("#notesHidden").val(response.notes)
                               console.log(response);
                               //	e.preventDefault();
                               window.location.replace(
                                   "${pageContext.request.contextPath}/jsp/success.jsp?t=" + title
                                   + "&r=" + rating + "&rd=" + releaseDate + "&n=" + notes + "&d="
                                   + director);

                           }

                       });
            }
            console.log("title ", title);
            console.log("year ", year);
            console.log("errr ", validationErrors);

            e.preventDefault();

        });
    });
</script>

