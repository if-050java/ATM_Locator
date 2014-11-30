/**
 * Created by Olavin on 29.11.2014.
 */

/* On select item in ATM Network dropdown show in Banks dropdown belonging only
 *  also set dropdown title to name of the ATM Network
 * */
var network_id = 0;

$(document).ready(loadBanks());

function loadBanks() {
    $.get("/banksListAjax", function(bankslist){
        var ul_banks = $("#bankslist");
        $.each(bankslist, function(i){
            var li = $('<li/>').attr('role', 'presentation').appendTo(ul_banks);
            var aaa = $('<a/>').attr('href','#').text(bankslist[i].name).appendTo(li);
        });

    });
}

$("#networks_menu li a").click(function(){
    var selText = $(this).text();
    $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
    network_id = $(this).attr("id");
    var netbanks = document.getElementsByClassName("bankitem");
    for(i=0; i<netbanks.length; i++)
    {
        bankitem = netbanks[i];
        if(network_id==="isnet_all" || $(bankitem).hasClass(network_id)){
            bankitem.style.display = "block";
        } else {
            bankitem.style.display = "none";
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
