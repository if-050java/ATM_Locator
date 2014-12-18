<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css">
        TABLE {
            border:1px solid black;
            background-color:#e9e9e9;
            table-layout: auto;
            padding: 3px;
            width: 98%;
        }
        TD, TH {
            padding: 3px;
            border: 1px solid black;
            text-align: center;
        }
        TH {
            background: #b0e0e6;
        }
    </style>
	 <script>
		$(document).ready(function() {
			$("#jobsTable .deleteLink").on("click",function() {
				var tr = $(this).parent().parent();
				//change the background color to red before removing
				var jobName = tr.children("td:nth-child(1)").html();
                $.ajax({
                    url:"./admin/"+jobName,
                    type : "DELETE",
                    context: document.body,
                    dataType: "json",
                    statusCode: {
                        200: function(){
                            tr.css("background-color","#FF3700");
                            tr.fadeOut(500, function(){
                                tr.remove();
                            });
                            return false;
                        },
                        500: function(){alert("Error");}
                    }
                })

			});
			
			$("#jobsTable .enableLink").on("click",function() {
				var tr = $(this).parent().parent(); //tr
				var tdJobStatus = tr.children("td:nth-child(7)");

                tdJobStatus.html("ACTIVE");
			});
			
			$("#jobsTable .disableLink").on("click",function() {
				var tr = $(this).parent().parent(); //tr
				var tdJobStatus = tr.children("td:nth-child(7)");
				tdJobStatus.html("PAUSED");
			});
			
			
		});
	</script> 
</head>
<body>
<div style="height:40px; width:100%;">
    <c:if test="${not empty error}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only">Close</span></button>

            <div style="text-align: center">
                    ${error}
            </div>
        </div>
    </c:if>
</div>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Jobs</h3>
    </div>
    <div class="panel-body">
        <table id="jobsTable">
            <tr>
            <th>JobName</th>
            <th>JobGroup</th>
            <th>TriggerName</th>
            <th>TriggerGroup</th>
            <th>CronTab</th>
            <th>JobClass</th>
            <th>JobStatus</th>
            <th></th>
            </tr>
            <c:forEach var="item" items="${jobs}">
                <tr>
                    <td><c:out value="${item.getJobName()}"/></td>
                    <td><c:out value="${item.getJobGroup()}"/></td>
                    <td><c:out value="${item.getTriggerName()}"/></td>
                    <td><c:out value="${item.getTriggerGroup()}"/></td>
                    <td><c:out value="${item.getJobClassName()}"/></td>
                    <td><c:out value="${item.getCronSched()}"/></td>
                    <td><c:out value="${item.getJobStatus()}"/></td>
					<td>
						<a class="runLink" href="#" >run</a>
						<a class="deleteLink" href="#" >delete</a>
						<a class="enableLink" href="#" >enable</a>
						<a class="disableLink" href="#" >disable</a>
						<a class="editLink" href="#" >edit</a>
						<a class="viewLink" href="#" >view</a>
					</td>
                </tr>
            </c:forEach>
        </table>
        <a class="runLink" href="<c:url value="/admin/addnew"/>" >Add new job</a>
    </div>
</div>
</body>
</html>
