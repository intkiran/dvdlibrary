<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>DVD Library</title>
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>DVD Library</h1>
    <hr/>
    <div class="row">
        <div class="col-sm-3">
            <form id="createDvdForm">
                <button type="button" class="btn" id="createDvd">Create DVD</button>
            </form>

        </div>
        <div class="col-sm-1">
            <button type="button" class="btn" id="searchBtn">Search</button>
        </div>
        <div class="col-sm-3">
            <select class="form-control" name="category">
                <option value="">Choose Category</option>
                <option value="TITLE">Title</option>
                <option value="YEAR">Release Date</option>
                <option value="DIRECTOR">Director</option>
                <option value="RATING">Rating</option>

            </select>
        </div>
        <div class="col-sm-5">
            <input class="form-control" placeholder="Search term"
                   name="srch-term" id="srch-term" type="text">
        </div>

    </div>
    <br/> <br/>
    <div class="row">
        <div class="">
            <div class="alert alert-danger alert-dismissable" id="validationError"
                 style="display:none;">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            </div>

        </div>
    </div>

    <table class="table" id="dvdTable">
        <thead>
        <tr>
            <th>Dvd Id</th>
            <th>Title</th>
            <th>Release Date</th>
            <th>Director</th>
            <th>Rating</th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
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
                       var trHTML = '';

                       $.each(data, function (i, item) {

                           trHTML +=
                               '<tr class="active"><td>' + item.dvdId + '</td><td><a href="${pageContext.request.contextPath}/jsp/editDvd.jsp?dvdId='+item.dvdId +'">'+ item.title
                               + '</a></td><td>' + item.releaseDate + '</td><td>' + item.director
                               + '</td><td>' + item.rating
                               + '</td><td><a href="#" class="btn btn-info" role="button" onclick="editDvd('
                               + item.dvdId + ')">Edit</a>'
                               + '<a href="#" class="btn btn-info" role="button"  onclick="deleteDvd('
                               + item.dvdId + ')" >Delete</a></td></tr>';
                       });

                       $('#dvdTable').append(trHTML);

                   },
                   error: function (textStatus, errorThrown) {
                       console.log("fail ", textStatus);
                       console.log("fail ", errorThrown);
                   },
                   complete: function (data) {
                       console.log("complete", data);

                   }
               });
        $("#searchBtn").click(function (e) {
            console.log("searchBtn ");
            //var category=$("#category").val();
            var searchTerm = $("#srch-term").val();
            var category = $('select[name=category]').val();
            console.log("searchTerm ",searchTerm);
            console.log("category ",category);
            if(!((searchTerm)&&(category)))
            {
                $('#validationError').show();
                $('#validationError').html("Both Search Category and Search term are required");
            }            var formData = {
                "keyword": searchTerm,
                "category": category
            }
            $.ajax({
                       url: '${pageContext.request.contextPath}/api/dvd/search',
                       headers: {
                           'Accept': 'application/json',
                           'Content-Type': 'application/json'
                       },
                       method: 'POST',
                       data: JSON.stringify(formData),
                       contentType: 'application/json; charset=utf-8',
                       dataType: "json",
                       success: function (data) {
                           console.log("success ", data);
                           $("#dvdTable").find("tr:gt(0)").remove();

                           if(data.length!=0) {
                               var trHTML = '';
                               $.each(data, function (i, item) {

                                   trHTML +=
                                       '<tr class="active"><td>' + item.dvdId + '</td><td><a href="${pageContext.request.contextPath}/jsp/editDvd.jsp?dvdId='+item.dvdId +'">'
                                       + item.title
                                       + '</a></td><td>' + item.releaseDate + '</td><td>'
                                       + item.director
                                       + '</td><td>' + item.rating
                                       + '</td><td><a href="#" class="btn btn-info" role="button" onclick="editDvd('
                                       + item.dvdId + ')">Edit</a>'
                                       + '<a href="#" class="btn btn-info" role="button"  onclick="deleteDvd('
                                       + item.dvdId + ')" >Delete</a></td></tr>';
                               });

                               $('#dvdTable').append(trHTML);
                           }else{

                               $('#validationError').show();
                               $('#validationError').html("No Search result found for Search Term "+searchTerm +" and Category: "+category);

                           }

                       },
                       error: function (textStatus, errorThrown) {
                           console.log("fail ", textStatus);
                           console.log("fail ", errorThrown);
                       },
                       complete: function (data) {
                           console.log("complete", data);

                       }
                   });

        });
        $("#dvdDelete").click(function (e) {
            console.log("dvdDelete ");
            var $modalDiv = $(e.delegateTarget);
            var id = $(this).data('dvdId');
            var dd = $("#dvdDeleteButton").data("dvdId");
            var currentoperatorid = $(e).attr("data-dvdId"); //This is the id of the currently attending operator
            var t2 = $(this).data('dvdId');

            console.log("dd::", t2);

            console.log("ID " + id);
            $modalDiv.addClass('loading');
            /* $.post('/api/dvd/' + id).then(function() {
             $modalDiv.modal('hide').removeClass('loading');
             }); */

        });

        $("#createDvd").click(function (e) {
            console.log("Create DVD");
            document.getElementById("createDvdForm").action =
                "${pageContext.request.contextPath}/jsp/createDvd.jsp";
            document.getElementById("createDvdForm").submit();

        });
        $("#dvdDeleteBtn").click(function (e) {
            var id = $("#dvdId").val();

            console.log("dvdDeleteBtn on Modal", id);
            var formData = {
                'dvdId': parseInt(id)
            };
            $.ajax({
                       type: 'POST',
                       contentType: 'application/json; charset=utf-8',
                       dataType: "json",
                       data: JSON.stringify(formData),
                       url: '${pageContext.request.contextPath}/api/dvd/delete'
                   }).done(function (data) {
                // If successful
                console.log("RESULT", data);
                $("confirm-delete").hide();
                location.reload();
            }).fail(function (jqXHR, textStatus, errorThrown) {
                // If fail
                console.log(textStatus + ': ' + errorThrown);
            });

        });
    });
    function editDvd(id) {
        console.log("editDvd ", id);
        window.location.replace("${pageContext.request.contextPath}/jsp/editDvd.jsp?dvdId=" + id);

    }
    function deleteDvd(id) {
        console.log("dvdDelete ", id);
        $("#dvdId").val(id);
        $("#confirm-delete").modal();

    }
</script>
<div id="deleteDialog" class="modal hide fade">
    <div class="modal-body">
        Are you sure you want to delete this Dvd from your collection?
        <input type="hidden" name="dvdId" id="dvdId" value=""/>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn btn-primary" id="delete">Delete
        </button>
        <button type="button" data-dismiss="modal" class="btn">Cancel</button>
    </div>
</div>
<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                Are you sure you want to delete this Dvd from your collection?
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a class="btn btn-danger btn-ok" id="dvdDeleteBtn">Delete</a>
            </div>
        </div>
    </div>
</div>
