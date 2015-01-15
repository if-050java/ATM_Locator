<div class="navbar navbar-fixed-bottom" style="min-height: 10px">

    <nav class="navbar navbar-fixed-bottom" style="min-height: 10px; background-color: #c0c0c0">
        <div class="col-md-6">Copyright(c) if050java, 2015</div>
        <div class="col-md-6" align="right" id="addfeedback">
            <a href="#" onclick="addFeedback()">add feedback</a>
        </div>
    </nav>

</div>

<div class="modal fade" id="feedbackModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Add feedback</h4>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" data-placement="left" for="namef">name</label>
                <div class="col-md-4">
                    <input id="namef" name="textinput" type="text" data-placement="left" value="" data-content="name isn't valid, or is too short(min 4 letters), or has unsupported character" placeholder="name" data-toggle="popover" onclick="hidePopover('name')" class="form-control input-md">
                </div>
            </div><br>

            <div class="form-group">
                <label class="col-md-4 control-label" data-placement="left" for="emailf">email</label>
                <div class="col-md-4">
                    <input id="emailf" name="textinput" type="text" data-placement="left" value="" data-content="E-mail isn't valid" data-toggle="popover" onclick="hidePopover('email')" placeholder="email" class="form-control input-md">
                </div>
            </div>

            <div class="modal-body">
                <textarea class="form-control" id="feedback" data-content="feedback must be greater than 20 characters" style="max-width: 100%"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="getFeedback()">Add feedback</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<!--Result of operation -->
<div class="container" style=" z-index: 1000;position: fixed; bottom:0%; padding-left: 0px;">
    <div class="col-md-9">
        <div class="" role="alert" id="message">
            <div type="button" class="close" onclick="hideAlert()"><span aria-hidden="true">&times;</span><span
                    class="sr-only">Close</span></div>
            <label id="resultDefinition"></label>
        </div>
    </div>
</div>

