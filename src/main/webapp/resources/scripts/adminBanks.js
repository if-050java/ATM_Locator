/**
 * Created by Olavin on 29.11.2014.
 */
var SUCCESS_SAVE = "<span>ATM Network name updated.</span>";
var SUCCESS_NEW = "<span>ATM Network created.</span>";
var SUCCESS_DELETE = "<span>ATM Network deleted.</span>";
var ERROR_SAVE = "<span>Error, ATM Network name is not updated!</span>";
var ERROR_NEW = "<span>Error, ATM Network is not created!</span>";
var ERROR_DELETE = "<span>Error, ATM Network is not deleted!</span>";
var FORBID_DELETE = "<span>Error, Network is forbidden to delete!</span>";
var WARNING_MESSAGE = "<span>Warning, something goes wrong.</span>";

/* On select item in ATM Network dropdown show in Banks dropdown belonging only
 *  also set dropdown title to name of the ATM Network
 * */
var network_id = 0;
var bankslist;

$(document).ready( function () {
    loadNetworks();
    loadBanks();

    $("#btn_new_network").click(function () {
        var fd = new FormData();
        fd.append("id","0");
        fd.append("name",$("#net_name").val());
        saveNetwork(fd, SUCCESS_NEW, ERROR_NEW);
    });

    $("#btn_save_network").click(function () {
        var fd = new FormData();
        fd.append("id",network_id);
        fd.append("name",$("#net_name").val());
        saveNetwork(fd, SUCCESS_SAVE, ERROR_SAVE);
        $('.dropdown-toggle').html($("#net_name").val()+' <span class="caret"></span>');
    });

    $("#btn_del_network").click(function () {
        var fd = new FormData();
        fd.append("id", network_id);
        if (network_id == 0 || network_id == -1 || network_id == undefined) {
            showAlert("alert alert-danger", FORBID_DELETE);
        } else {
            $.ajax({
                url: getHomeUrl() + "adminNetworkDeleteAjax",
                type: "POST",
                data: fd,
                processData: false,
                contentType: false,
                success: function (response) {
                    console.log(response);
                    if (response.status == 'SUCCESS') {
                        $("#network_edit").collapse({ toggle: false });
                        loadNetworks();
                        $('.dropdown-toggle').html('Filter by ATM network <span class="caret"></span>');
                        showBanks(0);
                        showAlert("alert alert-success", SUCCESS_DELETE);
                    } else if (response.status == "ERROR") {
                        showAlert("alert alert-danger", ERROR_DELETE);
                    }
                },
                error: function () {
                    showAlert("alert alert-danger", ERROR_DELETE);
                }
            });
            //$("#networks_menu").empty();

        }
    });

});

function saveNetwork(fd, msg_succes, msg_error){
    if (fd == undefined) {
        showAlert("alert alert-danger", ERROR_SAVE);
    } else {
        $.ajax({
            url: getHomeUrl() + "adminNetworkSaveAjax",
            type: "POST",
            data: fd,
            processData: false,
            contentType: false,
            success: function (response) {
                console.log(response);
                if (response.status == 'SUCCESS') {
                    $("#network_edit").collapse({ toggle: false });
                    loadNetworks();
                    showAlert("alert alert-success", msg_succes);

                } else if (response.status == "ERROR") {
                    showAlert("alert alert-danger", msg_error);
                }
            },
            error: function () {
                showAlert("alert alert-danger", msg_error);
            }
        });
    }
}

function loadBanks() {
    $.get(getHomeUrl()+"banksListAjax", function(banks){
        bankslist=banks;
        showBanks(0);
    });
}

function loadNetworks() {
    $.get(getHomeUrl()+"networksListAjax", function(networks){
        var ul_networks = $("#networks_menu");

        ul_networks.empty();
        var li = $('<li/>').appendTo(ul_networks);
        var aaa = $('<a/>').attr('href',"#")
            .attr("id","isnet0")
            .text('All networks ')
            .appendTo(li);
        $('<span class="glyphicon glyphicon-star"></span>').appendTo(aaa);

        var li = $('<li class="divider"></li>').appendTo(ul_networks);

        for(i = 0; i < networks.length; i++){
            if(networks[i].id > 0) {
                var li = $('<li/>').appendTo(ul_networks);
                var aaa = $('<a/>').attr('href',"#")
                    .attr("id","isnet"+networks[i].id)
                    .text(networks[i].name)
                    .appendTo(li);
            }
        }
        var li = $('<li class="divider"></li>').appendTo(ul_networks);

        var li = $('<li/>').appendTo(ul_networks);
        var aaa = $('<a/>').attr('href',"#")
            .attr("id","isnet-1")
            .text('Unassigned ')
            .appendTo(li);
        $('<span class="glyphicon glyphicon-question-sign"></span>').appendTo(aaa);

        $("#networks_menu li a").click(function(){
            var selText = $(this).text();
            $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
            network_id = $(this).attr("id").substr(5);
            $("#net_name").val(selText);
            showBanks(network_id);
        });

    });
}

function showBanks(network) {
    var bankslist_net;
    if (network == 0) {
        bankslist_net = bankslist;
    } else {
        bankslist_net = jQuery.grep(bankslist, function (nbank) {
            return nbank.network.id == network;
        });
    }

    var column=1;
    var row = 1;
    var ul_banks = $("#bankslist"+column);
    var max_rows = Math.ceil(bankslist_net.length/3);
    $("#bankslist1").empty();
    $("#bankslist2").empty();
    $("#bankslist3").empty();
    for(i = 0; i < bankslist_net.length; i++){
        var li = $('<li/>').attr('role', 'presentation').appendTo(ul_banks);

        var aaa = $('<a/>').attr('href',getHomeUrl()+"adminBankEdit?bank_id="+bankslist_net[i].id)
                           .attr("id",bankslist_net[i].id)
                           .text(bankslist_net[i].name)
                           .appendTo(li);
        row++;
        if(row>max_rows){
            row=1; column++;
            ul_banks = $("#bankslist"+column);
        }
    }

}


