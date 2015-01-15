/**
 * Created by Olavin on 14.01.2015.
 */

$(document).ready(function () {
    $("#noticesgrid").dataTable({
        serverSide: true,
        pageLength: 20,
        ajax: {
            url: "/getNotices",
            data: function ( data ) {
                planify(data);
            },
            type: "POST"
        },
        columnDefs: [
            { searchable: false, targets: [ 0 ] }
        ],
        order: [ 0, 'desc' ],
        orderMulti: false,
        processing: true
    });
});

function planify(data) {
    for (var i = 0; i < data.columns.length; i++) {
        column = data.columns[i];
        column.searchRegex = column.search.regex;
        column.searchValue = column.search.value;
        delete(column.search);
    }
}
