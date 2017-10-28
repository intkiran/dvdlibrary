<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>New DVD Created success page</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet">
	<%
	String title = request.getParameter("t");
	String rating = request.getParameter("r");
	String releaseDate= request.getParameter("rd");
	String notes = request.getParameter("n");
		String director = request.getParameter("d");


%>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-xs-3 col-xs-offset-2">
				<h1><%=title %></h1>
			</div>
		</div>
		<hr />
		<div class="row" >
			<div class="col-xs-5 col-xs-offset-3">
			<div class="alert alert-danger alert-dismissable" id="validationError" style="display:none;">
			  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			</div>
			<div class="alert alert-success alert-dismissable" id="successMsg" style="display:none;">
  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
</div>
			
			</div>
		</div>
		<div class="row">
			<form id="createDvdForm" method="post" class="form-horizontal">
				<div class="form-group">
					<label class="col-xs-3 control-label">Dvd Title</label>
					<div class="col-xs-5">
											<%=title %>

					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">Release year</label>
					<div class="col-xs-5">
											<%=releaseDate %>

					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">Director</label>
					<div class="col-xs-5 inputGroupContainer">
						<div class="input-group">
											<%=director %>

						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">Rating</label>
											<%=rating %>

				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label" ">Notes</label>
					<div class="col-xs-5">
											<%=notes %>
					</div>
				</div>

				<div class="form-group">
					<div class="col-xs-5 col-xs-offset-3">
						<button type="button" class="btn btn-default" id="btnCancel">Back</button>
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
	$(document).ready(function() {
		$.ajax({
			url : '${pageContext.request.contextPath}/api/dvd/readall',
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			method : 'GET',
            contentType : 'application/json; charset=utf-8',
            dataType : "json",
			success : function(data) {
				console.log("success ", data);

		        
		        //$('#dvdTable').append(trHTML);

			},
			error : function(textStatus, errorThrown) {
				console.log("fail ", textStatus );
				console.log("fail ", errorThrown );
			},
			complete : function(data) {
				console.log("complete",data);
			}
		});

		$("#btnCancel").click(function(e) {
			console.log("btnCancel ");
			//document.getElementById("createDvdForm").action="${pageContext.request.contextPath}/jsp/DVDs.jsp";
            window.location.replace("${pageContext.request.contextPath}/jsp/createDvd.jsp");

		});
		
		$("#btnCreateDvd").click(function(e) {
			var validationErrors="";
			console.log("Create DVD form click");
			var title=$('#title').val();
			if(!(title)){
				validationErrors="Please enter a title for the Dvd</br>";
			}
			var year=$('#year').val();
			  var text = /^[0-9]+$/;
	console.log("asdf ",(!text.test(year)))
			if((!(year)) ||(!text.test(year))){
				validationErrors+="Please enter valid release year";

			}else{
		        if (year.length != 4) {
					validationErrors+="Please enter a 4-digit year";
		        }
			}
			
			if(validationErrors){
				$('#validationError').show();
				$('#validationError').html(validationErrors);

			}else{
				var director=$('#director').val();
				var rating=$('#rating').val();
				var notes=$('#notes').val();
				var formData = {
			            'title': title,
						'year': year,
			            'director':director,
			            'rating':rating,
			            'notes':notes,

			        };
				$.ajax({
			        type:"POST",
					contentType: 'application/json;',
					dataType: 'json',
			        url:"${pageContext.request.contextPath}/api/dvd/create",
			        data:JSON.stringify(formData),
			        success: function(response){
						$("#successMsg").text('Dvd created successfully');
						$("#successMsg").show(); 
			            console.log(response);  
						e.preventDefault();

			        }
			      
				});
			}
			console.log("title ",title);
			console.log("year ",year);
			console.log("errr ",validationErrors);

			e.preventDefault();

			
		});
	});
</script>

