/**
 * Created by Vasyl Danylyuk on 17.11.2014.
 */
var xmlhttp;            //AJAX request

function SelectFindType() {
    if (document.getElementById("byName").checked == true) {
        document.getElementById("findName").disabled = false;
        document.getElementById("findEmail").disabled = true;
    } else {
        document.getElementById("findName").disabled = true;
        document.getElementById("findEmail").disabled = false;
    }
};

function FindUser(){
    xmlhttp=GetXmlHttpObject();

    if (xmlhttp==null){
        alert ("Your browser does not support Ajax HTTP");
        return;
    }

    var findBy;
    var findValue;

    if(document.getElementById("byName").checked == true){
        findBy = "name";
        findValue = document.getElementById("findName").value;
    } else {
        findBy = "email";
        findValue = document.getElementById("findEmail").value;
    }

    var url = "/findUser?findBy="+findBy+"&findValue="+findValue;
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if(xmlhttp.status == 200) {
                fillData();
            }
        }
    };
    xmlhttp.send(null);
};

//Creating GET HTTP request object
function GetXmlHttpObject(){
    if (window.XMLHttpRequest){
        return new XMLHttpRequest();
    }
    if (window.ActiveXObject){
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
}

function fillData(){

}


