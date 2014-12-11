/**
 * Created by Olavin on 29.11.2014.
 */

/* On select item in ATM Network dropdown show in Banks dropdown belonging only
 *  also set dropdown title to name of the ATM Network
 * */
var network_id = 0;
var bankslist;

$(document).ready(loadBanks());

function loadBanks() {
    $.get(getHomeUrl()+"banksListAjax", function(banks){
        bankslist=banks;
        showBanks(0);
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


$("#networks_menu li a").click(function(){
    var selText = $(this).text();
    $(this).parents('.dropdown').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
    network_id = $(this).attr("id");
/*
    var netbanks = document.getElementsByClassName("bankitem");
    for(i=0; i<netbanks.length; i++)
    {
        bankitem = netbanks[i];
        if(network_id==="isnet0" || $(bankitem).hasClass(network_id)){
            bankitem.style.display = "block";
        } else {
            bankitem.style.display = "none";
        }
    }
*/
    network_number = network_id.substr(5);
    showBanks(network_number);

});

/*  set bank_id variable to further edit
 *   set Banks dropdown title to name of the bank
 * */
/*
var bank_id = 0;
$("#banks_menu li a").click(function(){
    var selText = $(this).text();
    $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
    bank_id = $(this).attr("id");
    document.getElementById("bank_id").value = bank_id;
    $("#btnBankEdit").removeAttr('disabled');
});
*/
