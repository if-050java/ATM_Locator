<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
    <div class="col-md-12" role="main" id="networksList">
        <div class="panel panel-default">
              <!-- Default panel contents -->
              <div class="panel-heading">Banks list:</div>

            <!-- Search box Start -->
            <%--<form>--%>
            <form:form method="get" role="form" cssClass="form-horizontal">
                <div class="well carousel-search hidden-sm">
                <%-- Select ATM Network dropdown menu --%>
                    <div class="btn-group"> <a class="btn btn-default dropdown-toggle btn-select" data-toggle="dropdown" href="#">Select ATM network<span class="caret"></span></a>
                        <ul class="dropdown-menu" id="networks_menu">
                            <li><a href="#" id="isnet_all"><span class="glyphicon glyphicon-star"></span>All networks</a></li>
                            <li class="divider"></li>
                            <c:forEach items="${networks}" var="net">
                                <c:if test="${net.id > 0}">
                                    <li><a href="#" id="isnet${net.id}">${net.name}</a></li>
                                </c:if>
                            </c:forEach>
                            <li class="divider"></li>
                            <li><a href="#" id="isnet-1"><span class="glyphicon glyphicon-question-sign"></span> Unassigned</a></li>
                        </ul>
                    </div>
                <%-- Select ATM Network dropdown menu --%>

                <%-- Select Bank dropdown menu --%>
                    <div class="btn-group"> <a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="#">Select a Bank <span class="caret"></span></a>
                        <ul class="dropdown-menu" id="banks_menu">
                            <c:forEach items="${banks}" var="bank">
                                <li class="bankitem isnet${bank.network.id}"><a href="#" id="${bank.id}">${bank.name}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                <%-- Select Bank dropdown menu --%>

                    <div class="btn-group">
                        <button type="submit" formaction="/adminBankEdit" id="btnBankEdit" class="btn btn-primary" disabled="true">Edit</button>
                    </div>
                    <div class="btn-group">
                        <button type="submit" formaction="/adminBankCreateNew" id="btnBankCreateNew" class="btn btn-primary">Create New</button>
                    </div>
                    <%--<input type="hidden" name="network_id" id="network_id">--%>
                    <input type="hidden" name="bank_id" id="bank_id">
                </div>
            </form:form>
            <!-- Search box End -->

          </div>

    </div>
</div>
<script type='text/javascript'>

    /* On select item in ATM Network dropdown show in Banks dropdown belonging only
    *  also set dropdown title to name of the ATM Network
    * */
    var network_id = 0;
    $("#networks_menu li a").click(function(){
        var selText = $(this).text();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
        network_id = $(this).attr("id");
        var banks_show = 0;
        var banks_hide = 0;
        var netbanks = document.getElementsByClassName("bankitem");
        for(i=0; i<netbanks.length; i++)
        {
            bankitem = netbanks[i];
            if(network_id==="isnet_all" || $(bankitem).hasClass(network_id)){
                bankitem.style.display = "block";
                banks_show = banks_show + 1;
            } else {
                bankitem.style.display = "none";
                banks_hide = banks_hide +1;
            }
        }

    });

    /*  set bank_id variable to further edit
    *   set Banks dropdown title to name of the bank
    * */
    var bank_id = 0;
    $("#banks_menu li a").click(function(){
        var selText = $(this).text();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
        bank_id = $(this).attr("id");
        document.getElementById("bank_id").value = bank_id;
        $("#btnBankEdit").removeAttr('disabled');
    });

</script>
