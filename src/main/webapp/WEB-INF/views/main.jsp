<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script src="https://maps.googleapis.com/maps/api/js??key=AIzaSyA4YR8loJtUaiviLc-WxnBsSH9Znt9TNEY&sensor=false&libraries=places"></script>

    <script src="<c:url value="/resources"/>/scripts/map.js"></script>
    <script src="<c:url value="/resources"/>/scripts/markers.js"></script>
    <script src="<c:url value="/resources"/>/scripts/userPositioning.js"></script>
    <script src="<c:url value="/resources"/>/jquery/jquery.geocomplete.min.js"></script>
    <script src="<c:url value="/resources"/>/jquery/jquery.autocomplete.min.js"></script>
    <script src="<c:url value="/resources"/>/jquery/jquery.cookie.js"></script>
    <script src="<c:url value="/resources"/>/jquery/jquery.bootstrap-touchspin.js"></script>
    <link href="<c:url value="/resources"/>/jquery/jquery.bootstrap-touchspin.css" rel="stylesheet">
    <script src="<c:url value="/resources"/>/scripts/richmarker-compiled.js"></script>
    <sec:authorize access="isAuthenticated()">
        <script src="<c:url value="/resources"/>/scripts/favorites.js"></script>
        <script src="<c:url value="/resources"/>/scripts/comments.js"></script>
        <link rel="stylesheet" href="<c:url value="/resources"/>/styles/animate.min.css"/>
    </sec:authorize>
    <link rel="stylesheet" href="<c:url value="/resources"/>/styles/main.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-9">
            <form onsubmit="return setLocationByAddress()">
                <div class="input-group">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="getLocation()"><span
                            class="glyphicon glyphicon-globe"></span>
                    </button>
                </span>
                    <input type="text" class="form-control" id="userAddress"
                           title="" data-content="" data-placement="bottom" data-toggle="popover"
                           data-original-title="" placeholder="Type in an address"/>
                    <span id="searchclear" class="glyphicon glyphicon-remove-circle"></span>
                <span class="input-group-btn">
                    <button class="btn btn-default" id="findLocation" type="button"
                            onclick="setLocationByAddress()"><span
                            class="glyphicon glyphicon-search"
                            title="Find"></span></button>
                </span>
                </div>
            </form>
            <div id="map_container" style="height: 500px" class="media">
                <!-- Map is here  -->
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Filter
                    </h3>
                </div>
                <div class="panel-body">
                    <form action="" method="get" onsubmit="updateFilter()" class="form">
                        <div class="form-group">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Select network"
                                       id="networksDropdownInput">

                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            data-toggle="dropdown" aria-expanded="false"><span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-right" role="menu" id="networksDropdown">
                                        <li><a href="0">All networks</a></li>
                                        <c:forEach items="${networks}" var="network">
                                            <li><a href="${network.id}">${network.name}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <input type="text" class="form-control " placeholder="Select bank"
                                       id="banksDropdownInput">

                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            data-toggle="dropdown" aria-expanded="false"><span class="caret"></span>
                                    </button>
                                    <ul id="banksDropdown" class="dropdown-menu dropdown-menu-right" role="menu">
                                        <c:forEach items="${banks}" var="bank">
                                            <li><a href="${bank.id}">${bank.name}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="showOtherBanks">
                                    Show other banks from network
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="checkbox">
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="ATMs" checked>
                                    ATMs
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="offices">
                                    Offices
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="input-group">
                                <label class="input-group-addon">Distance(m):</label>
                                <input id="distance" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <a href="#" class="btn btn-default col-md-12" onclick="updateFilter()">Update filter</a>
                        </div>
                    </form>
                </div>
            </div>
            <sec:authorize access="isAuthenticated()">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Favorites</h3>
                    </div>
                    <ul id="favorites_list" class="panel-body list-group"
                        style="padding: 0px; max-height: 163px; overflow-y: scroll">
                    </ul>
                </div>
            </sec:authorize>
        </div>
    </div>
</div>
<sec:authorize access="isAuthenticated()">
    <%--Menu on default marker--%>
    <div class="popup-menu" id="defaultMarkerMenu" style="display:none">
        <div class="popup-menu-item" onclick="addFavorite()">Add to favorites</div>
        <div class="popup-menu-item addcomment">Add comment</div>
    </div>
    <%--Menu on favorite marker--%>
    <div class="popup-menu" id="favoriteMarkerMenu" style="display:none">
        <div class="popup-menu-item" onclick="deleteFavorite()">Delete from favorites</div>
        <div class="popup-menu-item addcomment">Add comment</div>
    </div>
    <%--Add comment modal--%>
    <div class="modal fade" id="commentModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">Add comment</h4>
                </div>
                <div class="modal-body">
                    <textarea class="form-control" id="comment" style="max-width: 100%"></textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" onclick="addComment()">Add comment</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
</sec:authorize>
<%--Show comments modal--%>
<div class="modal fade" id="commentsWindow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">Comments</h4>
            </div>
            <div class="modal-body table" id="comments">
                <tr>adfgsdfgsdfgsdfg</tr>
                <tr>adfgsdfgsdfgsdfg</tr>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>
